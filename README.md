##Running  

This project was built with maven, go to the root project folder and type in a terminal:  
-> mvn clean install  
At this point, your project must compile and all the tests should run  
Go to root project folder/target than type in a terminal: 
-> java -jar revolut-account-0.0.1-SNAPSHOT-jar-with-dependencies.jar
  
** Please, you must have JAVA and MAVEN variables exported to your OS Path  

API echo endpoint (GET): http://localhost:4567/echo  
API endpoint (POST): http://localhost:4567/accounts/transfer/internal  
  
  
##Validation
  
  Please take a look at IntrernalTransferITest.java
  
  
##Technology stack  
	
	- java 8 (not still confident to java 11 compatibilities)
	- maven
	- spark java (http server - http://sparkjava.com)
	- guice (ioc - https://github.com/google/guice)
	- gson (json converter - https://github.com/google/gson)
	- slf4j (logging abstraction - https://www.slf4j.org)
	- junit (jupiter version)
	- unirest (http request client - http://unirest.io/java.html)
  	
   
##Project structure  

Basically our API test is inside SparkAccountController.java  
All the rest components are called from there  
For now, I focused on internal transfer transactions only (Revolut account to other Revolut account)    
I've also thought in a possible microservice expanse, so lets works with String ID to aggregations.
  
It was split into 2 main packages:  
  
	- core
	  - Must not provide our API
	  - This one was designed to be our core domain model
	  - Should be independent of third libraries
	  - Was designed to support other transactions operations, but for now we will work with Internal Transfer only (Revolut account to Revolut account)
	  - "commons" package should abstract cross domain components
  
	- infrastructure
	  - Must provide our API
	  - This one was designed to implement our core domain model infrastructure needs
	    - Validations
	    - Repository
	    - HttpServer
	    - Logger
	    - Json converter
  
Talking about tests...  
  
	- tests
	  - integration tests
	    - the ones which ends up with **ITest.java
	  - unit tests
	    - the ones which ends up with **UTest.java
	    - mocked with interface implementation (i did not use Mockito library as i am used to)
	  
