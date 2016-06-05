Event Sourced Banking
==============

Banking use case to  try out [Event Sourcing](https://www.youtube.com/watch?v=JHGkaShoyNs).

Deposits and withdrawals are written as events to a [MongoDB](https://www.mongodb.com/) database running in a [Docker](https://www.docker.com/) container.

Example event:
--------------
Account **Y** has £**X** deposited into it at date/time **Z**).

These 'events' are then read from the database, ordered by time, to *project* the current state of the bank account.
