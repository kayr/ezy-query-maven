# The ezy-query Maven Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.kayr/ezy-query-maven-plugin/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/io.github.kayr/ezy-query-maven-plugin)

Maven plugin for https://github.com/kayr/ezy-query

## Installation

Add the following to your `pom.xml` (For now this is not yet available in maven central)

```xml
<plugins>
    ....
    <plugin>
        <groupId>io.github.kayr</groupId>
        <artifactId>ezy-query-maven-plugin</artifactId>
        <version>0.0.2</version>
        <executions>
            <execution>
                <goals>
                    <goal>ezyInitFolders</goal>
                    <goal>ezyBuild</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    ....
</plugins>

```

