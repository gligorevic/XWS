FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/ChatService-1.0.0.jar ChatService-1.0.0.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]
