# Blogging Platform API

[Project URL](https://roadmap.sh/projects/blogging-platform-api)
[Submission URL](https://roadmap.sh/projects/blogging-platform-api/solutions?u=6479b105c4ec366ad5b756da)

## Getting the source code

1. Clone the GitHub repository:
   ```shell
   git clone https://github.com/himanshu-pareek/roadmapsh-backend-projects.git
    ```
   
2. Go to `blogging-platform-api` directory:
    ```shell
    cd roadmap-backend-projects
    cd blogging-platform-api
    ```

## Running the application

### Environment variables

The below environment variables are used by the application. You can either put these variables using `.env` file (only if you run using IDE) or you can put them in system environment variables.

1. `POSTGRES_URL`=Postgres Database url
2. `POSTGRES_USER`=Postgres Database username
3. `POSTGRES_PASSWORD`=Postgres Database password

If you want to use the `docker-compose.yaml` file to run the application, you can put these variables in a `.env` file in the root directory of the project. In this case you need one more environment variable `POSTGRES_DATABASE` which is the name of the database.

### Start Postgres Container (Optional)

You can run postgres server however you want. The easiest and recommended approach is to use docker container for that. I have included a `docker-compose.yaml` file which can be used with `docker` cli to spin a postgres container up. Run the following command to start the postgres up:

```shell
docker compose up -d
```

After that you can run the application as a normal Spring Boot Application.

### Using IDE

For IntelliJ IDEA, the run configuration is already set up. You can directly run the application by selecting **BloggingPlatformApiApplication** run configuration clicking on the run button. The `.env` file is selected as the environment file in this run configuration.

For any other IDE, you can set the environment variables in the run configuration and run as a normal Spring Boot Application.

### Using CLI (gradle)

```shell
# First create required environment variables using export command
./graldew bootRun
```
