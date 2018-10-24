# Banking&ETLSchedulerSim
A bank transactions &amp; ETL scheduling simulator made during an internship in a Tunisian banking system. It's basically an attempt to simulate near real-time datawarehousing and to optimize the TX lifecycle within the banking system.

Technologies used in this project are: Spring Boot, Spring MVC, Spring Cloud (+ Zipkin), Spring Security (+ LDAP), Thymeleaf, Vaadin, PostgreSQL &amp; OpenLDAP (as databases for storage and authentication) &amp; Spring Batch.

Hopefully, someone would find this project useful someday.
PS* possibility of opensourcing this project whenever the first version of it comes to light.

#### Getting it up & running
To get this app to run on your side, you've got to:
* Have some Spring framework/components knowledge. Well, the more you have, the more easier it will become to handle & maintain this project.
1. Clone this repo
2. Import the project to your IDE
3. Create your own configuration config-server-repo
4. Link it to the config-server microservice
5. Create/Reconfigure your logs-repo directory
6. (Optional) Start zipkin-server
7. (Optional) Install your log etl tool
8. Run the whole Spring Dashboard

###### Clone this repo:
To clone/get the project, you've got two ways:  
- Either go in the folder you want to create the project in, open a terminal window and git clone the project: git clone <a>https://github.com/RKayX2/ETLSchedulerSim.git</a>  
- Or you can download the zip file from this github repo, and extract it wherever you want to create your project folder
###### Import the project to your IDE:
I made this project using Intellij IDEA (of course you can use both versions, Community or Ultimate).  
So you choose to import the project without importing the underlying folders, then start by adding microservices files; you add the microservice folder as a module to your project through the project structure settings. Mark the java folder under the main/src folder as your Sources folder, and the resources folder as your Resources folder.  
You then have got to repeat the process for each microservice/edge-microservice. Of course, when adding microservices as modules, you choose to import the maven project so it loads all of its dependencies and register them.  
###### Create your own configuration repository:
To ensure better overall control for each aspect of this project (especially its deployment and its maintainability), I tended to externalize the configuration through a configuration server (using a Spring Cloud Config project: config-server edge microservice) that would load all the microservices configuration files from a github repository (yes, the config-server-repo is also a github repository #repoception).  
So to make your own, you can choose to either host a config server on your own and link it to the Spring Cloud Config project (config-server) through its configuration file, or to create another git repository for your config files and only change the link in the config-server module properties file.
###### Create your logs-repo directory:
In this project, I tried to centralize the logging into a directory, that after, in deployment step, would be held into a server that splunk is listening to, pass it into ETL process, to some real time analysis on the debugging/maintaining side, and for real time bugs reporting without the need of the user to interfere.  
###### Run the whole Spring Dashboard
Finally, you can either choose to run the Spring Dashboard (including the whole set of microservices & edge microservices under) letting it figure out the order it needs to implement. (for IOC nerds, and trust me it knows what to run first and what next)  
Or if ever you're so skeptical about it, you can run the whole thing in this order:
1. ConfigServerApplication
2. EurekaServerApplication
3. ZuulServerApplication
4. MicrocustomerApplication
5. MicroaccountApplication
6. MicrotransactionApplication
7. MicroetlApplication
8. AgentuiApplication
9. SpringadminApplication

Hopefully, someone will find this project useful, don't hesitate to contact me for some support whenever you need to .
