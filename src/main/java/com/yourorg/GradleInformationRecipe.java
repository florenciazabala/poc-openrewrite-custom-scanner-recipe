package com.yourorg;

import org.openrewrite.*;
import org.openrewrite.gradle.marker.GradleProject;

import java.util.Optional;



public class GradleInformationRecipe extends Recipe {

    @Override
    public String getDisplayName() {
        return "Gradle Dependencies declaration to Maven";
    }

    @Override
    public String getDescription() {
        return "Converts a project from Gradle to Maven.";
    }

    /*@Override
    public Accumulator getInitialValue(ExecutionContext ctx) {
        return new Accumulator();
    }*/

    /*@Override
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
                            System.out.println(String.format("%s:%s:%s:%s\n", dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), dependency.getScope()));
                        });
                    });

                    gp.getConfigurations().forEach(configuration -> {
                        System.out.println(configuration.getName());
                        if ("dependencyManagement".equals(configuration.getName())) {
                            configuration.getRequested().forEach(dependency -> {
                                System.out.println(String.format("%s:%s:%s:%s\n", dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), dependency.getScope()));
                            });
                        }
                    });

                }

                return tree;
            }
        };
    }*/

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new TreeVisitor<Tree, ExecutionContext>() {
            @Override
            public Tree visit(Tree tree, ExecutionContext ctx) {
                Optional<GradleProject> maybeGp = tree.getMarkers()
                        .findFirst(GradleProject.class);
                if (maybeGp.isPresent()) {
                    GradleProject gp = maybeGp.get();

                    gp.getConfigurations().forEach(configuration -> {
                        configuration.getRequested().forEach(dependency -> {
                            System.out.println(String.format("%s:%s:%s:%s\n", dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), dependency.getScope()));
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
