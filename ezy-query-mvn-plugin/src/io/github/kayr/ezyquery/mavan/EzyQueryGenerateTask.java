package io.github.kayr.ezyquery.mavan;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.nio.file.Path;

@Mojo(name = "ezyBuild", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class EzyQueryGenerateTask extends AbstractMojo {
    @Override
    public void execute() {
        Clj.call("ezy_query_mvn_plugin.core", "mvn-gen!", getPluginContext().get("project"));
    }
}
