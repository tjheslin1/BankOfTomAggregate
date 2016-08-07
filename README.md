Bank of Tom
==============

Creating a dummy banking system using a microservice architecture with [Event Sourcing](https://www.youtube.com/watch?v=JHGkaShoyNs).

Bank of Tom Aggregate (this app)
--------------

This is the *Aggregate* in [this diagram](http://tomhesl.in/wp-content/uploads/2016/07/Lifepreserver.png)

Deposits and withdrawals are written as events to a [MongoDB](https://www.mongodb.com/) database running in a [Docker](https://www.docker.com/) container.

Bank of Tom Gateway
--------------

This is the *Gateway* in [this diagram](http://tomhesl.in/wp-content/uploads/2016/07/Lifepreserver.png)

Exposes the microservices (the *Aggregate* and *View*), exposing avaiable features by health-checking the microservices and translating requests to commands to the *Aggregate* and queries to the *View*
. (See [CQRS](http://martinfowler.com/bliki/CQRS.html)).

Useful links
--------------

[Event Sourcing with MongoDB](https://www.mongodb.com/blog/post/event-sourcing-with-mongodb) *see video*.