# reactive-spring-data-cassandra
This sample project is based on Spring and shows how to implement a simple REST service relying on Cassandra database and reactive programming pattern.

Spring components used:
- spring-boot
- spring-data-cassandra
- spring-webflux

2 JUnit test classes provided to validate code:
- UserServiceTest
- UserControllerTest based on Spring WebTestClient

Basically, it creates an entity User and persists it into a Cassandra repository. 

JUnit classes rely on an embedded Cassandra, check project page for more information:
https://github.com/nosan/embedded-cassandra

It provides a useful way to have a cassandra instance running for tests without installing anything and comes with a spring integration that makes it very easy to use. 
