Embedded RSocket Broker
=======================

Embed RSocket Broker in Spring Boot unit test by testcontainers-spring-boot.

# Quickstart

* Refer https://github.com/Playtika/testcontainers-spring-boot for testing setup
* Add embedded-rsocket-broker dependency in the pom.xml

```xml
<dependency>
   <groupId>com.alibaba.rsocket</groupId>
   <artifactId>embedded-rsocket-broker</artifactId>
   <version>1.0.0</version>
   <scope>test</scope>
 </dependency>
```

* Modify `src/test/resources/application.properties` add following configuration

```properties
rsocket.brokers=${embedded.rsocket.uri}
```

# References

* testcontainers-spring-boot: https://github.com/Playtika/testcontainers-spring-boot
* Testcontainers: https://www.testcontainers.org/
