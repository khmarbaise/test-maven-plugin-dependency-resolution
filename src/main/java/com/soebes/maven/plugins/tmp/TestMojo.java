package com.soebes.maven.plugins.tmp;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.IncludesArtifactFilter;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilderException;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.traversal.CollectingDependencyNodeVisitor;

/**
 * Will check dependencies of your project and fail the build if they are not up-to-date.
 * 
 * @author Karl-Heinz Marbaise <a href="mailto:khmarbaise@apache.org">khmarbaise@apache.org</a>
 */
@Mojo( name = "test", requiresDependencyResolution = ResolutionScope.COMPILE, defaultPhase = LifecyclePhase.PACKAGE, requiresProject = true, threadSafe = true )
public class TestMojo
    extends AbstractTestMojo
{

    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        if ( isSkip() )
        {
            getLog().info( " Skipping execution based on user request." );
            return;
        }

        List<Dependency> dependencies = getMavenProject().getDependencies();
        for ( Dependency dependency : dependencies )
        {
            getLog().info( "Dependency: " + getId( dependency ) );
        }

        Set<Artifact> artifacts = getMavenProject().getArtifacts();
        for ( Artifact artifact : artifacts )
        {
            getLog().info( "Artifact: " + artifact.getId() );
        }

        resolveViaDependencyGraphBuilder();
    }

    private String getId( Dependency dep )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( dep.getGroupId() );
        sb.append( ':' );
        sb.append( dep.getArtifactId() );
        sb.append( ':' );
        sb.append( dep.getVersion() );
        return sb.toString();
    }

    public void resolveViaDependencyGraphBuilder()
        throws MojoExecutionException
    {

        ArtifactFilter artifactFilter =
            new IncludesArtifactFilter( Arrays.asList( "org.apache.maven.shared:maven-invoker" ) );
        ProjectBuildingRequest buildingRequest =
            new DefaultProjectBuildingRequest( getMavenSession().getProjectBuildingRequest() );
        buildingRequest.setProject( getMavenProject() );
        try
        {
            DependencyNode rootNode =
                getDependencyGraphBuilder().buildDependencyGraph( buildingRequest, artifactFilter );
            CollectingDependencyNodeVisitor visitor = new CollectingDependencyNodeVisitor();
            rootNode.accept( visitor );
            for ( DependencyNode node : visitor.getNodes() )
            {
                getLog().info( "GraphBuilder: " + node.toNodeString() );
            }
        }
        catch ( DependencyGraphBuilderException e )
        {
            throw new MojoExecutionException( "Couldn't build dependency graph", e );
        }
    }
}
