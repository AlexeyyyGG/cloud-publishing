FROM tomcat:11.0-jre17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY web-app/build/libs/web-app.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]