.PHONY: clean javac pom gen-descriptor descriptor install pluginTest

# Makefile mainly created to work around generating the plugin.xml file

clean:
	lein clean
	rm -f pom.xml
	rm -f resources/META-INF/maven/plugin.xml
	find resources/META-INF -depth -type d -exec rmdir {} + 2>/dev/null || true

javac:
	lein javac

pom:
	lein pom

install:
	lein install

descriptor:
	mvn org.apache.maven.plugins:maven-plugin-plugin:descriptor
	mkdir -p resources/META-INF/maven
	cp target/plugin-enhanced.xml resources/META-INF/maven/plugin.xml

clean-install: clean javac pom descriptor install
ci: clean-install
di: descriptor install

