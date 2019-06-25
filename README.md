# GeoFencing Test

## Table of Contents

- [About](#about)
- [Getting Started](#getting_started)
- [Usage](#usage)

## About <a name = "about"></a>

A typical web application, having a front end and an api service.
The frontend for creating a polygon, and calls the respective api service to persist the polygon in the database.
The api service which has below endpoints :

1. takes GeoJSON structure of a polygon geometry as input, and saves the polygon in database
2. takes lat,long as input and returns whether this point is with the polygon created from frontend.

## Getting Started <a name = "getting_started"></a>

These instructions will get you a copy of the project up and running on your local machine for demo purpose. See [deployment](#deployment) for notes on how to deploy the project on a live system.

### Prerequisites

```
1. Java 8
2. Docker / Docker-compose (Optional: if you have a standalone Oracle DB installed)
3. Git
4. A java IDE i.e. Spring Tools Suite / Eclipse / IntelliJ
```

### Installing

#### A step by step instruction on how to get a runtime env setup for demo.

##### - Ensure prequsites are all installed

```bash
 java -version
 git --version

 # docker required only if NOT you have installed Oracle DB
 docker --version
 docker-compose --version

```

##### - Clone the github repo : <https://github.com/hishamsharif/geo-test.git>

##### - Setup DB and execute sql scripts to run create tables,procedures, and other objects required by the application

- If you have Oracle installed in your (host) machine, then run the scripts inside "/db/sql"
- Else
  - [Download the Oracle 12c here](https://www.oracle.com/technetwork/database/enterprise-edition/downloads/oracle12c-linux-12201-3608234.html)
    - ( Note: 11g XE and 18c XE versions did not work properly with hibernate spatial, and in the case of 11g, oracle locator did not have much features such as within spatial query functions)
  - Copy the downloaded "linuxx64_12201_database.zip" inside
    "/db/dcoker-oracle-xe-12c/dockerfiles/12.2.0.1"
  - In the terminal, run the "./db/setup.sh" to install oracle 12c & create required DB user/objects

##### - Setup the front-end web app and back-end api source in your favourite IDE \_STS IDE is recommended for spring boot apps

- import the below projects into IDE
  - front-end app : "./location-marker-web"
  - back-end api : "./location-service-api"
- verify/modify the DB connection settings in the application.property
- start the back-end api (Run As -> Spring Boot App), and ensure its started successfully _(Started BackendAPI in x.xx seconds...)_
- start the front-end web app (Run As -> Spring Boot App), and ensure its started successfully _(Started FrontendWebApp in x.xx seconds...)_
- [Open the web app in a browser](http://localhost:8080/)
  - Draw a polygon on the displayed Map
  - Click "Submit Form" button on thr right side, and polygon coordinates are saved in the db.

## Usage <a name = "usage"></a>

Demo on how to use the system.
