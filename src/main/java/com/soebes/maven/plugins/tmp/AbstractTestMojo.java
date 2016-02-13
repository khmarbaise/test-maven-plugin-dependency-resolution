package com.soebes.maven.plugins.tmp;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;

public abstract class AbstractTestMojo
    extends AbstractMojo
{

    /**
     * You can skip the execution of test-maven-plugin in cases
     * where you might encounter a failure.
     */
    @Parameter( defaultValue = "false", property = "tmp.skip" )
    private boolean skip;

    /**
     * The project currently being build.
     */
    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject mavenProject;

    /**
     * The current Maven session.
     */
    @Parameter( defaultValue = "${session}", readonly = true )
    private MavenSession mavenSession;

    
    @Component
    private DependencyGraphBuilder dependencyGraphBuilder;
    
    public boolean isSkip()
    {
        return skip;
    }

    public void setSkip( boolean skip )
    {
        this.skip = skip;
    }

    public MavenProject getMavenProject()
    {
        return mavenProject;
    }

    public MavenSession getMavenSession()
    {
        return mavenSession;
    }

    public DependencyGraphBuilder getDependencyGraphBuilder()
    {
        return dependencyGraphBuilder;
    }

}
