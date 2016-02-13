package com.soebes.maven.plugins.tmp;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Named
public class LifeCycleParticipant
    extends AbstractMavenLifecycleParticipant
{
    private final Logger LOGGER = LoggerFactory.getLogger( getClass() );

    private boolean afterProjectReadCalled;
    
    private String conf;

    private RepositorySystem system;

    private AbstractMojo mojo;
    
    @Inject
    public LifeCycleParticipant( RepositorySystem system )
    {
        LOGGER.info( "LifeCycleParticipant::LifeCycleParticipant(ctro) {}", this );
        this.system = system;
        this.conf = "Unkonwn";
        this.mojo = null;
        this.afterProjectReadCalled = false;
    }

    @Override
    public void afterProjectsRead( MavenSession session )
    {
        LOGGER.info( "LifeCycleParticipant::afterProjectsRead() {}", this );
        this.afterProjectReadCalled = true;
    }

    @Override
    public void afterSessionStart( MavenSession session )
    {
        //This is only be called if this an extension only...
        LOGGER.info( "LifeCycleParticipant::afterSessionStart() {}", this );
    }

//    @Override
//    public void afterSessionEnd( MavenSession session )
//        throws MavenExecutionException
//    {
//        LOGGER.info( "LifeCycleParticipant::afterSessionEnd(start) {}", this );
//        LOGGER.info( "LifeCycleParticipant::afterSessionEnd(start) mojo:={}", mojo);
//        LOGGER.info( "LifeCycleParticipant::afterSessionEnd(start) aferProjectReadCalled:={}", afterProjectReadCalled);
//        
//        MavenProject project = session.getProjects().get( 0 );
//
//        if ( !project.hasLifecyclePhase( LifecyclePhase.DEPLOY.id() ) )
//        {
//            return;
//        }
//
//        for ( MavenProject p : session.getProjectDependencyGraph().getSortedProjects() )
//        {
//            LOGGER.info( "-> Project:" + p.getId() );
//        }
//        LOGGER.info( "LifeCycleParticipant::afterSessionEnd(end) {}", this );
//    }

    public String getConf()
    {
        return conf;
    }

    public void setConf( String conf )
    {
        this.conf = conf;
    }

    public AbstractMojo getMojo()
    {
        return mojo;
    }

    public void setMojo( AbstractMojo mojo )
    {
        this.mojo = mojo;
    }

    public RepositorySystem getSystem()
    {
        return system;
    }

    public void setSystem( RepositorySystem system )
    {
        this.system = system;
    }

    public boolean isAfterProjectReadCalled()
    {
        return afterProjectReadCalled;
    }

}
