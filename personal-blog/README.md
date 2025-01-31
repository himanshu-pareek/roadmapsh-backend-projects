# Personal Blog

[Project URL](https://roadmap.sh/projects/personal-blog)
[Project Submission](https://roadmap.sh/projects/personal-blog/solutions?u=6479b105c4ec366ad5b756da)

## Getting the source code

1. Clone the GitHub repository:
   ```shell
   git clone https://github.com/himanshu-pareek/roadmapsh-backend-projects.git
    ```
   
2. Go to `personal-blog` directory:
    ```shell
    cd roadmap-backend-projects
    cd personal-blog
    ```

## Running the application

### Environment variables

The below environment variables are used by the application. You can either put these variables using `.env` file or you can put them in system environment variables.

1. `SPRING_PROFILES_ACTIVE` - comma separated list of active profiles (`local` for development, `init-db` if you want to generate dummy data and store it in the database on startup of the application)
2. `POSTGRES_URL` - The url of the postgres database (for local / development)
3. `POSTGRES_DATABASE` - The name of the database (for local / development)
4. `POSTGRES_USER` - The username of the database user (for local / development)
5. `POSTGRES_PASSWORD` - The password of the database user (for local / development)
6. `SPRING_DATASOURCE_URL` - The url of the postgres database (for production)
7. `SPRING_DATASOURCE_USERNAME` - The username of the database user (for production)
8. `SPRING_DATASOURCE_PASSWORD` - The password of the database user (for production)
8. `ADMIN_USERNAME` - Username of admin for application
10. `ADMIN_PASSWORD` - Password for admin of the applicaton

### Start postgres database

You can run postgres database however you want. The easiest and recommended approach is to use docker container for that. I have included a `docker-compose.yaml` file which can be used with `docker` cli to spin a postgres database container up. You need to specify the properties required in `docker-compose.yaml` file in a `.env` file or as environment variables (that is up to you). After that, run the following command to start the database up:

```shell
docker compose up -d
```

The same environment variable are used by `application-local.properties` file to connect to the database. So, even if you are not using containerization for postgres database, it is highly recommended to put the database connection properties as environment variables (or in `.env` file at the root of the project).

### Local Development

1. Generate resources - First of all you need to generate resources required for the application using maven. This step is required for first time only and also after running `mvn clean` command:

```shell
./mvnw generate-resources
```

2. Running application - Use any IDE to run the main application, or use the below command:

```shell
./mvnw spring-boot:run
```

3. Generating templates in watch mode - Run the following command to keep watching for changes in static files and regenerate the templates:

```shell
./npm run build && ./npm run watch
```

### Running in production

1. Package the application using the following command:

```shell
./mvnw package
```

The above command generates a `jar` file in `target` directory.

2. Run the generated `jar` file in production using the following command. Make sure you have all of the specified environment variables are set correctly. Also make sure you have postgres database running.

```shell
java -jar file.jar
```
