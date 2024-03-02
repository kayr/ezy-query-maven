package io.github.kayr.ezyquery.mavan;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

public class EzyQueryTask {

    @Mojo(name = "ezyBuild", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
    public static class Generate extends AbstractMojo {
        @Override
        public void execute() {
            Clj.call("ezy_query_mvn_plugin.core", "mvn-gen!", getPluginContext().get("project"));
        }
    }

    @Mojo(name = "ezyInitFolders", defaultPhase = LifecyclePhase.INITIALIZE)
    public static class InitFolders extends AbstractMojo {
        @Override
        public void execute() {
            Clj.call("ezy_query_mvn_plugin.core", "mvn-init!", getPluginContext().get("project"));
        }
    }
}
