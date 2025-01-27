package com.yourorg;

import org.openrewrite.ExecutionContext;
import org.openrewrite.ScanningRecipe;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.gradle.marker.GradleProject;

import java.util.Optional;


public class GradleInformationRecipe extends ScanningRecipe<GradleInformationRecipe.Accumulator> {

    @Override
    public String getDisplayName() {
        return "Gradle Dependencies declaration to Maven";
    }

    @Override
    public String getDescription() {
        return "Converts a project from Gradle to Maven.";
    }

    @Override
    public Accumulator getInitialValue(ExecutionContext ctx) {
        return new Accumulator();
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getScanner(Accumulator acc) {
        return new TreeVisitor<Tree, ExecutionContext>() {
            @Override
            public Tree visit(Tree tree, ExecutionContext ctx) {
                Optional<GradleProject> maybeGp = tree.getMarkers()
                        .findFirst(GradleProject.class);
                if (maybeGp.isPresent()) {
                    GradleProject gp = maybeGp.get();

                    acc.group = gp.getGroup();

                    gp.getConfigurations().forEach(configuration -> {
                        configuration.getRequested().forEach(dependency -> {
                            System.out.println(String.format("%s:%s:%s\n", dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion()));
                        });
                    });

                }

                return tree;
            }
        };
    }

    static class Accumulator {
        String group;
    }
}
