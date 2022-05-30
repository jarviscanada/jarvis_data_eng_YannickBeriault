# Introduction
- This is a small project meant to deploy the trading app developed in the context of the so-called Springboot project
  inside a Kubernetes infrastructure where various instances of our application can be served by a load-balancer and to
  which can be added new instances when usage demands it, effectively auto-scaling our application while maintaining a
  sole Psql database instance with which the application instances can interact.
- Two environments were created on Azure to answer our need of having a development version and a production version of
  our software. Our develop branch is deployed to the first one and our main branch is deployed to the second one.
- Our pipelines are triggered manually and both work in the same fashion. They are linked to our github repository,
  were they find the Jenkinsfile they need to execute:
  - They will login into our Azure account, using credentials that were saved and encrypted in our Jenkins instance;
  - They will switch to the springboot directory, cloned from our repository;
  - There, an image will be built using the latest version of code that was pushed to the corresponding branch;
  - The pipeline will then associate the kubectl command to the corresponding k8s infrastructure in the cloud;
  - This infrastructure will be instructed to deploy the new image.

# Improvements
- The pipeline does not include deployment to a test environment and, consequently, testing of our application. A
  complete solution would necessitate to include testing of the application;
- We would like to see our pipeline react automatically to a push to the corresponding branch of the repository instead
  of us having to trigger it manually.