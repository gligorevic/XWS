FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/PKIService-1.0.0.jar PKIService-1.0.0.jar
RUN chmod +x /entrypoint.sh
VOLUME /root
CMD ["/entrypoint.sh"]
