sudo rm -rf /usr/share/tomcat8/webapps/ROOT
sudo rm -rf /usr/share/tomcat8/webapps/ROOT.war
git pull
mvn package
sudo mv target/uss-1.0.war /usr/share/tomcat8/webapps/ROOT.war
sudo tomcat8 stop
sudo tomcat8 start