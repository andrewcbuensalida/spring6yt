# spring6yt
https://www.youtube.com/playlist?list=PLsyeobzWxl7qbKoSgR5ub6jolI8-ocxCF
https://lucid.app/lucidchart/5bb60b52-5ab7-4901-a2d8-84ab6df0d0e8/edit?viewport_loc=-10%2C-10%2C1707%2C871%2C0_0&invitationId=inv_5ad682a1-3010-4a58-9d15-1c4e18e92864
https://lucid.app/lucidchart/d51a203b-4a07-488f-8d46-e60d4d7cf760/edit?viewport_loc=-10%2C-10%2C1707%2C871%2C0_0&invitationId=inv_b9ed87de-5626-48ad-80ee-889b5e36d052
## The front-end for this is ecom-frontend-5.
## Part 28 is the crud api.
unit tests https://www.youtube.com/playlist?list=PL82C6-O4XrHcg8sNwpoDDhcxUCbFy855E
When testing, model and repo files aren't included in the coverage. Maybe because they are not annotated. They are included in the tests though.

## swagger
just add in pom.xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
and go to http://localhost:8080/swagger-ui/index.html

## Part 38 is jwt with postgres.
https://lucid.app/lucidchart/72aadf0a-acc8-4e2b-a380-f2f70fcd6123/edit?invitationId=inv_8d5a82c4-45e4-4ad4-8361-beb6202251a0
First you need to setup the database and table in pgadmin. OR maybe have a schema.sql and data.sql in the resources folder to setup and preload the database. Then you have to do a POST request to http://localhost:8080/register in Postman to register a user. The body is something like:
```
{
    "username": "user",
    "password": "password"
}
```
This will encode the password with BCrypt, then save to postgres.
Then in browser, go to http://localhost:8080. It will ask for username and password. 
TODO figure out how to logout after logging in. I tried clearing the cache and hard reload, and deleting the cookie in dev tools and there's nothing in local and session storage, but it still remembers the user.

## Part 39 is the oauth with GitHub and google.
Have to setup GitHub and google oauth secrets first.

## TODOS
TODO deploy to fargate https://www.youtube.com/watch?v=lnnNshOlXjo

##
When you add a dependency to pom.xml, it automatically downloads. You can see the downloaded dependencies in vs code, explorer, on the bottom there's a maven section, expand the correct Part, Dependencies.

## 
In order for Explorer > Maven > Part 28 > lifecycles > test to work, command prompt in IDE has to have the correct jdk version. To check the version, 
`mvn --version`
And also check the local mvn with
`"c:\swe\code\spring6yt\Part28-Project using Spring Search Feature\mvnw.cmd" --version`
Sometimes you have to close all the vs code windows if you recently changed your environment variables JAVA_HOME path.
If you want to temporarily set the environment variable in that specific terminal, 
`set JAVA_HOME=C:\Program Files\Java\jdk-21`
To check the environment variable,
`echo %JAVA_HOME%`
or
`mvn --version`
The error when the jdk version in the app is 17 but the maven compiler version is 21 is:
`[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.8.1:compile (default-compile) on project api: Fatal error compiling: java.lang.NoSuchFieldError: Class com.sun.tools.javac.tree.JCTree$JCImport does not have member field 'com.sun.tools.javac.tree.JCTree qualid' -> [Help 1]`
The error when the jdk version in the app is 21 but the maven compiler version is 17 is something like:
`version 21 not compatible`


## To deploy to aws elastic beanstalk
https://www.geeksforgeeks.org/deploy-a-spring-boot-application-with-aws/

- Should have / route for EB health checks OR modify the health check route in EB
- VS code > Explorer > Maven > Part28-telusko-ecomm > Lifecycle > package
This creates the jar file in target folder.
- Open AWS Console and Create and Elastic BeanStalk Application
upload the jar.
Public ip address activated and select subnets, just to make it easy. Probably need to tighten this in production.
Environment variables, add
SERVER_PORT 5000
