 when i use maven scope import import the dependency get the cycle
 
 [ERROR]   The project com.example:websocket-dependencies:0.0.1-SNAPSHOT (D:\code_local\fmt\websocket\websocket-dependencies\pom.xml) has 1 error
 [ERROR]     The dependencies of type=pom and with scope=import form a cycle: com.example:websocket-dependencies:0.0.1-SNAPSHOT -> com.example:websocket-dependencies:0.0.1-SNAPSHOT
the pom.xml is that:
    
    <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.example</groupId>
        <artifactId>websocket</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <modules>
            <module>web</module>
            <module>websocket-dependencies</module>
            <module>entity</module>
        </modules>
        <packaging>pom</packaging>
        <name>websocket</name>
        <description>Demo project for Spring Boot</description>
    
    
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
            <java.version>1.8</java.version>
            <maven-compiler-plugin.version>3.6.1</maven-compiler-plugin.version>
            <spring.boot.verison>1.5.9.RELEASE</spring.boot.verison>
        </properties>
    
    
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-dependencies</artifactId>
                    <version>${spring.boot.verison}</version>
                    <scope>import</scope>
                    <type>pom</type>
                </dependency>
                <dependency>
                    <groupId>com.example</groupId>
                    <artifactId>websocket-dependencies</artifactId>
                    <version>${project.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
    
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <configuration>
                        <fork>true</fork>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    
    
        <profiles>
            <profile>
                <id>dev</id>
                <properties>
                    <env>dev</env>
                </properties>
            </profile>
            <profile>
                <id>test</id>
                <properties>
                    <env>test</env>
                </properties>
            </profile>
            <profile>
                <id>prod</id>
                <properties>
                    <env>prod</env>
                </properties>
            </profile>
        </profiles>
    </project>


    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
        <parent>
            <artifactId>websocket</artifactId>
            <groupId>com.example</groupId>
            <version>0.0.1-SNAPSHOT</version>
        </parent>
        <artifactId>websocket-dependencies</artifactId>
        <packaging>pom</packaging>
        <name>websocket-dependencies</name>
        <description>websocket-dependencies</description>
        <properties>
        </properties>
        <dependencyManagement>
            <dependencies>
            </dependencies>
        </dependencyManagement>
    
        <profiles>
        </profiles>
    </project>

when i run `mvn clean -x` it get the error detail:

    [INFO] Error stacktraces are turned on.
    [WARNING]
    [WARNING] Some problems were encountered while building the effective settings
    [WARNING] Unrecognised tag: 'mirror' (position: START_TAG seen ...he preferred\n   | server for that repository.\n   |-->\n    <mirror>... @146:13)  @ D:\soft\apache-maven-3.3.9\conf\settings.xml, line 146, column 13
    [WARNING]
    [INFO] Scanning for projects...
    [ERROR] [ERROR] Some problems were encountered while processing the POMs:
    [WARNING] 'build.plugins.plugin.version' for org.springframework.boot:spring-boot-maven-plugin is missing. @ com.example:websocket:0.0.1-SNAPSHOT, D:\code_local\fmt\websocket\pom.xml, line 52, column 21
    [ERROR] Non-resolvable import POM: Could not find artifact com.example:websocket-dependencies:pom:0.0.1-SNAPSHOT @ com.example:websocket:0.0.1-SNAPSHOT, D:\code_local\fmt\websocket\pom.xml, line 38, column 25
     @
    [ERROR] The build could not read 1 project -> [Help 1]
    org.apache.maven.project.ProjectBuildingException: Some problems were encountered while processing the POMs:
    [WARNING] 'build.plugins.plugin.version' for org.springframework.boot:spring-boot-maven-plugin is missing. @ com.example:websocket:0.0.1-SNAPSHOT, D:\code_local\fmt\websocket\pom.xml, line 52, column 21
    [ERROR] Non-resolvable import POM: Could not find artifact com.example:websocket-dependencies:pom:0.0.1-SNAPSHOT @ com.example:websocket:0.0.1-SNAPSHOT, D:\code_local\fmt\websocket\pom.xml, line 38, column 25

        at org.apache.maven.project.DefaultProjectBuilder.build(DefaultProjectBuilder.java:422)
        at org.apache.maven.graph.DefaultGraphBuilder.collectProjects(DefaultGraphBuilder.java:419)
        at org.apache.maven.graph.DefaultGraphBuilder.getProjectsForMavenReactor(DefaultGraphBuilder.java:410)
        at org.apache.maven.graph.DefaultGraphBuilder.build(DefaultGraphBuilder.java:83)
        at org.apache.maven.DefaultMaven.buildGraph(DefaultMaven.java:491)
        at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:219)
        at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:193)
        at org.apache.maven.DefaultMaven.execute(DefaultMaven.java:106)
        at org.apache.maven.cli.MavenCli.execute(MavenCli.java:863)
        at org.apache.maven.cli.MavenCli.doMain(MavenCli.java:288)
        at org.apache.maven.cli.MavenCli.main(MavenCli.java:199)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:497)
        at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced(Launcher.java:289)
        at org.codehaus.plexus.classworlds.launcher.Launcher.launch(Launcher.java:229)
        at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode(Launcher.java:415)
        at org.codehaus.plexus.classworlds.launcher.Launcher.main(Launcher.java:356)
    [ERROR]
    [ERROR]   The project com.example:web:0.0.1-SNAPSHOT (D:\code_local\fmt\websocket\web\pom.xml) has 1 error
    [ERROR]     Non-resolvable import POM: Could not find artifact com.example:websocket-dependencies:pom:0.0.1-SNAPSHOT @ com.example:websocket:0.0.1-SNAPSHOT, D:\code_local\fmt\websocket\pom.xml, line 38, column 25 -> [Help 2]
    org.apache.maven.model.resolution.UnresolvableModelException: Could not find artifact com.example:websocket-dependencies:pom:0.0.1-SNAPSHOT
            at org.apache.maven.project.ProjectModelResolver.resolveModel(ProjectModelResolver.java:197)
            at org.apache.maven.model.building.DefaultModelBuilder.importDependencyManagement(DefaultModelBuilder.java:1192)
            at org.apache.maven.model.building.DefaultModelBuilder.build(DefaultModelBuilder.java:455)
          
        
