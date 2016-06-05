Event Sourced Banking
==============

Banking use case to  try out [Event Sourcing](https://www.youtube.com/watch?v=JHGkaShoyNs).

Deposits and withdrawls are written to a [MongoDB](https://www.mongodb.com/) Database running [Docker](https://www.docker.com/) as events (e.g. account y has £x deposited into it at date/time Z).

These 'events' are then read from to [project](https://www.youtube.com/watch?v=JHGkaShoyNs) the current state of the bank account.
