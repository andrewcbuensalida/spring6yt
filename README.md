# spring6yt
## The front-end for this is ecom-frontend-5.
## Part 28 is the crud api.

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