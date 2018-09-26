FROM java:8

# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/wedulpos-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} wedulpos.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/wedulpos.jar"]