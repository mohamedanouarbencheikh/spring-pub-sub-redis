# Project Title

Spring data Redis (pub/sub)

## Description

This are two projects identify how to use redisDB (pub/sub) with spring boot.

## Getting Started

### Dependencies

The projects are created using spring https://start.spring.io/ with spring data redis dependency.

### Installing

* Redis server version 5 or higher  in linux environement.

### Executing program

* In this example we show how to use redis as pub / sub.
* Firslty we should start the sub project before the pub project.
* Contrary to Redis stream mode, Redis in pub / sub mode doesn't stock data exanged in queue and the subscriber doesn't sent an acknowledgment message to the publisher, that's why it is necessary to have subscriber started before publisher, if not, the data will be lost.   


