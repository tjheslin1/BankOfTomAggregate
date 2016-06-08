Event Sourced Banking
==============

Banking use case to  try out [Event Sourcing](https://www.youtube.com/watch?v=JHGkaShoyNs).

Deposits and withdrawals are written as events to a [MongoDB](https://www.mongodb.com/) database running in a [Docker](https://www.docker.com/) container.

These *events* are then read from the database, ordered by time, to *project* the current state of the bank account.

Useful links
--------------

[Event Sourcing with MongoDB](https://www.mongodb.com/blog/post/event-sourcing-with-mongodb) *see video*.