FROM java:8

RUN ["mkdir", "esb"]
ADD BankOfTomAggregate-1.0-SNAPSHOT.jar /esb

CMD ["java", "-jar", "/esb/BankOfTomAggregate-1.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "BankOfTomAggregate-1.0-SNAPSHOT.jar", "--acceptLicense", "/dev/root/"]