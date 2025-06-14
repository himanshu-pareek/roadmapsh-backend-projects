# TODO List API

## About

This is a solution of the project [Todo List API](https://roadmap.sh/projects/todo-list-api). This project implements a simple CRUD service for todo items and implements security using token authentication.

## Running the application

Running the app requires `3` steps:
1. Setting up the environment
2. Starting up the Postgres Database
3. Starting up the Spring Application

### Prerequisite

1. Java Version `21` is installed
2. `docker` and `docker compose` are installed

### Setting up the environment

You are expected to have the following environment variables:
```shell
POSTGRES_DATABASE=<Name of the datbase>
POSTGRES_USER=<Database user name>
POSTGRES_PASSWORD=<Database password>
POSTGRES_URL=<Database url to connect to>
```

These environment variables are required to spin up the postgres server as well as to start the spring application.

You can put these environment variables in a file called `.env` if you don't want to mess up the environment variables of your system.

### Starting up the Postgres Database

Run the following command to spin up a Postgres database:

```shell
docker compose up -d
```

### Starting up the Spring Application

You can run the spring application in whatever way you want, just be sure to pass the environment variables to the application while running it.

If you are using IntelliJ IDEA to run the app, then the run configuration are already loaded in the IDE. The run configuration reads the environment variables from the `.env` file.

