Author: Timothy Mwangi
Date: 12th June 2014
Project: Simple Loarn Armotization helper
==========================================

Kindly follow these instructions to be able to run the project


PREREQUISITES 
=============
Have the following installed in your runtime;

- JDK  (Developed & tested on version 1.6.0_23)
- JBOSS (Developed and tested on version 5.1.0.GA)
- Mysql Server (Tested on version  5.5.8)
- Operating system should be any, but this was developed and tested on a  64bit windows 8 machine.
- Ant (My version is 1.8.2) - this is for build management of this project
- Browser - tested on Gogogle Chrome, Mozilla Firefox and Internet Explorer version 8
- create a database called "test" in your mysql server.


Third party java libraries. (though I've packed all libs herein to save you time)
=================================================================================

Jar files should be located in WebContent/WEB-INF/lib
antlr-2.7.6.jar
commons-beanutils.jar
commons-lang-2.3.jar
commons-logging-1.1.1.jar
commons-logging.jar
displaytag-1.2.jar
dom4j-1.5.jar
ejb-3.0.jar
ejb3-persistence.jar
freemarker-2.3.16.jar
IN hibernate folder, copy all *hibernate 3.5.0 final" libs found in the "lib" sub directory of the package**	
hibernate-jpa-2.0-api-1.0.0.Final.jar
hibernate-tools-3.2.4.GA.jar
hibernate3.jar
javax.persistence-2.0.0.jar
json.jar
jstl.jar
log4j-1.2.16.jar
mail.jar
mysql-connector-java-5.1.14-bin.jar
persistence-api.jar
slf4j-api-1.6.0.jar
slf4j-log4j12-1.6.0.jar
standard.jar
stripes.jar



FILES TO EDIT BEFOE BUILDING AND RUNNING APPLICATION
===================================================
You'll need to edit the following files as per your environment;

1. loan_calc_mysql-ds.xml
2. build.properties
3. jndi.properties

Below are instructions on how to do that;


1.loan_calc_mysql-ds.xml
  ========================

Open loan_calc_mysql-ds.xml located on the root directory.
lines 16 to 19 are of interest here and they look like this;
Line 16.    <connection-url>jdbc:mysql://localhost:3306/test</connection-url>
Line 17.    <driver-class>com.mysql.jdbc.Driver</driver-class>
Line 18.    <user-name>root</user-name>
Line 19.    <password></password> 

Change the connection url (line 16) to point to your mysql server host and correct port.
Chage the username and password to reflect those of your mysql server on lines 18 and 19 
respectively.

2. build.properties
   ================

Open build.properties located in the root directory.
First three lines look like this;

Line 1. #Edit as per your environment
Line 2. jboss5=H\:/INSTALLED_SOFTWARE/jboss-5.1.0.GA
Line 3. jboss.home=${jboss5}

Edit the second line to point to your jboss's root directory.
Edit the value of "jboss5" to reflect to your jboss 5.1.0 GA



jndi.properties
===============

Open jndi.properties located in the root directory. It looks like this;

Line 1. java.naming.factory.initial=org.jnp.interfaces.NamingContextFactory
Line 2. java.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces
Line 3. java.naming.provider.url=localhost

Edit line 3 if necessary. Should point to the hostname where the jboss runs
in my case it runs in localhost. No need to change file if same case applies
to you.





HOW TO BUILD THE PROJECT
========================

open your command prompt and navigate to this directory, in my case it is

F:\Projects\Inmobia\loan_calculator>

Type

run the following ant tasks; (be 100% that there is a database called "test" that exists

ant exportDDL
ant initialize
ant jar
ant war


After the above 4 ant tasks have run successfully, run your jboss and open your browser

Open your browser
and type in the url

http://localhost:8080/loan_calculator/
The application should be that of a monthly payment calculator for loans from 
different banks with different interest rates.

Have fun!


