# spring6yt
## The front-end for this is ecom-frontend-5.
## Part 28 is the crud api.
unit tests https://www.youtube.com/playlist?list=PL82C6-O4XrHcg8sNwpoDDhcxUCbFy855E
When testing, model and repo files aren't included in the coverage. Maybe because they are not annotated. They are included in the tests though.

## Part 38 is jwt with postgres.
First you need to setup the database and table in pgadmin. Then you have to do a POST request to http://localhost:8080/register in Postman to register a user. The body is something like:
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
TODO deploy to aws lambda using sam cli. https://www.youtube.com/watch?v=A1rYiHTy9Lg&t=153s
TODO deploy to fargate https://www.youtube.com/watch?v=lnnNshOlXjo
TODO use RestTemplate, OpenFeign, and WebClient to make requests to other services. 

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
