### logback 配置

---

#### 输出到文件滚动策略

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="60 seconds" debug="true">
    <!--输出到文件-->
    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>${LOG_FILE}.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <maxFileSize>10MB</maxFileSize>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} %contextName [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
</configuration>
```

#### appender

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="60 seconds" debug="true">
    <!--将控制controller包下的所有类的日志的打印，但是并没用设置打印级别，所以继承他的上级的日志级别 info -->
    <!--没有设置addtivity，默认为true，将此logger的打印信息向上级传递；-->
    <!--没有设置appender，此logger本身不打印任何信息。-->
    <logger name="com.example.websocket.controller"/>
    
    <!--<logger>用来设置某一个包或者具体的某一个类的日志打印级别、以及指定<appender>-->
    <!--additivity: 是否向上级 logger 传递打印信息. 默认为 true-->
    <logger name="com.example" level="info" additivity="false">
        <appender-ref ref="console"/>
    </logger>
</configuration>
```

#### 多环境配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="60 seconds" debug="true">
    <springProfile name="dev,test">
            <logger name="org.apache.ibatis" level="debug" />
            <logger name="java.sql" level="debug" />
    </springProfile>
    <!-- 生产环境. -->
    <springProfile name="prod">
        <logger name="org.apache.ibatis" level="error" />
        <logger name="java.sql" level="error" />
    </springProfile>
</configuration>
```