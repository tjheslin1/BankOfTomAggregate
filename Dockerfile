FROM java:8

RUN ["mkdir", "esb"]
ADD EventSourcedBanking-1.0-SNAPSHOT.jar /esb

CMD ["java", "-jar", "/esb/EventSourcedBanking-1.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "EventSourcedBanking-1.0-SNAPSHOT.jar", "--acceptLicense", "/dev/root/"]