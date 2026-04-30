FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY . .
RUN mvn -pl ruoyi-admin -am -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app

ENV TZ=Asia/Shanghai \
    SPRING_PROFILES_ACTIVE=prod \
    RUOYI_PROFILE=/app/data/ruoyi

RUN mkdir -p /app/data/ruoyi

COPY --from=build /workspace/ruoyi-admin/target/ruoyi-admin.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
