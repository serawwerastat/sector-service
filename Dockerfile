ARG NODEJS_VERSION=14.18.1
ARG JAVA_VERSION=11
ARG MAVEN_VERSION=3.6.0

#Stage 1: Build Angular UI
FROM node:${NODEJS_VERSION}-alpine as build-ui
WORKDIR /root/sector-service-ui
COPY sector-selector-ui/package.json sector-selector-ui/package-lock.json ./
RUN npm install
COPY sector-selector-ui/. .
RUN npm run build

#Stage 2: Build back-end (compiled UI will be packaged into JAR)
FROM maven:${MAVEN_VERSION}-jdk-${JAVA_VERSION}-slim AS build
WORKDIR /root/sector-service

COPY sector-selector-app/pom.xml sector-selector-app/pom.xml
RUN mvn -f sector-selector-app/pom.xml dependency:go-offline

#COPY .git/ ./.git/
COPY sector-selector-app/ sector-selector-app/
COPY --from=build-ui /root/sector-service-ui/dist/ ui/
RUN mvn -f sector-selector-app/pom.xml -DUI_DIST_PATH=../ui -DskipTests package

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
