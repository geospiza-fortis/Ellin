FROM openjdk:8

RUN apt-get update && \
    apt-get install -y maven default-mysql-client npm

RUN npm install -g n && n stable

WORKDIR /app
# Cache plugins by calling help once before use
# https://stackoverflow.com/a/39336178
COPY pom.xml pom.xml
RUN mvn exec:help spotless:apply
RUN mvn dependency:go-offline package
ADD . /app
RUN mvn package
