FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/CarInfoService-1.0.0.jar CarInfoService-1.0.0.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]