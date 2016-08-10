FROM java:8

RUN ["mkdir", "BankOfTom"]
ADD target/BankOfTomAggregate-1.0-SNAPSHOT.jar /BankOfTom

EXPOSE 8085

CMD ["java", "-jar", "/BankOfTom/BankOfTomAggregate-1.0-SNAPSHOT.jar"]