FROM gradle:8-jdk17-jammy AS build
COPY *.gradle .
COPY src src
RUN gradle build -x test
#COPY build/libs/to-mr-*SNAPSHOT.jar ./to-mr.jar
#RUN java -Djarmode=layertools -jar to-mr.jar extract


FROM openjdk:17-alpine
EXPOSE 8080
COPY --from=build /home/gradle/build/libs/to-mr-*SNAPSHOT.jar ./to-mr.jar
ENTRYPOINT ["java", "-jar", "to-mr.jar"]
#COPY --from=build /home/gradle/dependencies/ ./
#COPY --from=build /home/gradle/snapshot-dependencies/ ./
#COPY --from=build /home/gradle/spring-boot-loader/ ./
#COPY --from=build /home/gradle/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
