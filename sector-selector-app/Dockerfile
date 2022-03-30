ARG JAVA_VERSION=11
ARG MAVEN_VERSION=3.6.0

FROM maven:${MAVEN_VERSION}-jdk-${JAVA_VERSION}-slim AS build
WORKDIR /root/sector-service

COPY pom.xml sector-selector-app/pom.xml
RUN mvn -f sector-selector-app/pom.xml dependency:go-offline

#COPY .git/ ./.git/
COPY . sector-selector-app/
RUN mvn -f sector-selector-app/pom.xml -DskipTests package

RUN mkdir artifact \
    && cp sector-selector-app/target/*.jar artifact \
    && cd artifact \
    && mv *.jar sector-service.jar

#Stage 3: Build final app container
FROM openjdk:${JAVA_VERSION}

RUN groupadd --gid 1000 sector-service \
   && useradd --uid 1000 -s /bin/false -g sector-service sector-service

WORKDIR /home/sector-service
COPY --from=build --chown=sector-service /root/sector-service/artifact/ ./

USER sector-service
ENTRYPOINT ["java","-jar","sector-service.jar"]

