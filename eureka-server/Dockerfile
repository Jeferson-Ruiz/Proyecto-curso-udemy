FROM amazoncorretto:17-alpine-jdk
WORKDIR /appdo
EXPOSE 8761
ADD ./target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar

ENTRYPOINT [ "java", "-jar", "eureka-server.jar" ]
