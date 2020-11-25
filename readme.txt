正式发布时用以下方法运行，但要安装maven，在cmd中到项目所在目录执行以下命令：
mvn exec:java -Dexec.mainClass="com.genertech.phm.core.Runner"


以下要pom.xml里配置exec-maven-plugin
也可直接以下方式运行：
mvn test -Pphm-data


以下要pom.xml里配置maven-shade-plugin
也可先用maven install一下，运行生成的jar包，
如有jvm客户端可双击直接运行
如果只有jdk可使用以下命令运行：
java -Dfile.encoding=UTF-8 -jar PHM-DATA-0.0.1-SNAPSHOT.jar


以上在以jar方式运行的时候，现在已不能用了，因为项目已改为web项目了
使用maven的 tomcat7:deploy可以发布
还有tomcat:undeploy   tomcat:redeploy
