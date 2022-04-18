# Introduction
This app is meant to manage the most common Twitter operations, being to create and post a tweet, to retrieve and show the most important informations about a tweet and to delete a tweet. It uses a java HTTP client to communicate with the Twitter Rest API, authenticating with the OAuth protocol 1.0. It manages the information extracted from tweet using the javax json library. It manages depencies using Maven and the Spring framework, while being wrapper in a Docker container for convenience of use.

# Quick Start
- The app should be packaged using `mvn clean package`. The tests will be run autimatically.
  - A docker image then needs to be built: `docker build -t ${docker_user}/twitterapp .`
  - Four keys and tokens needs to be obtained from the Twitter Developper portal to access the REST API through the app, as oneself. They need to be entered as environment variables in a file named `.env` and created at the root of the project, with the names TWITTER_API_KEY, TWITTER_API_SECRET, TWITTER_ACCESS_TOKEN and TWITTER_ACCESS_SECRET. Make sure to exclude this .env file from the staging area.
- The command then used to launch the app will be `docker run --env-file .env --rm ${docker_user}/twitterapp command arg1 arg2(optional)`

# Design
## UML diagram
## explain each component(app/main, controller, service, DAO) (30-50 words each)
## Models
Talk about tweet model
## Spring
- How you managed the dependencies using Spring?

# Test
How did you test you app using Junit and mockito?

## Deployment
How did you dockerize your app.

# Improvements
- Imporvement 1
- Imporvement 2
- Imporvement 3
