FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/PriceListService-1.0.0.jar PriceListService-1.0.0.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]
