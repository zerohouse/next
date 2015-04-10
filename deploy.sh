sudo rm -rf /usr/share/tomcat8/webapps/ROOT
sudo rm -rf /usr/share/tomcat8/webapps/ROOT.war
git pull
rm -rf wewebapp/WEB-INF/lib.setting
cp ../lib.setting webapp/WEB-INF/
mvn package
sudo mv target/uss-1.0.war /usr/share/tomcat8/webapps/ROOT.war
sudo tomcat8 stop
sudo tomcat8 start