# spring6yt
Part 28 is the crud api.

Part 38 is jwt with postgres.
First have to do a POST request to http://localhost:8080/register in Postman to register a user. The body is something like:
```
{
    "username": "user",
    "password": "password"
}
```
This will encode the password with BCrypt, then save to postgres.
Then in browser, go to http://localhost:8080. It will ask for username and password. 

Part 39 is the oauth with GitHub and google.
Have to setup GitHub and google oauth secrets first.