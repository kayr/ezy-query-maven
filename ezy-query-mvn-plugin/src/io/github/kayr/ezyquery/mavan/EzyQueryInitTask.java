package io.github.kayr.ezyquery.mavan;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "ezyInitFolders", defaultPhase = LifecyclePhase.INITIALIZE)
public class EzyQueryInitTask extends AbstractMojo {


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Clj.call("ezy_query_mvn_plugin.core", "mvn-init!", getPluginContext().get("project"));
    }
}
