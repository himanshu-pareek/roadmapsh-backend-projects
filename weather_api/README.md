# Personal Blog

[Project URL](https://roadmap.sh/projects/weather-api-wrapper-service)
[Submission URL](https://roadmap.sh/projects/weather-api-wrapper-service/solutions?u=6479b105c4ec366ad5b756da)

## Getting the source code

1. Clone the GitHub repository:
   ```shell
   git clone https://github.com/himanshu-pareek/roadmapsh-backend-projects.git
    ```
   
2. Go to `weather_api` directory:
    ```shell
    cd roadmap-backend-projects
    cd weather_api
    ```

## Running the application

### Environment variables

The below environment variables are used by the application. You can either put these variables using `.env` file or you can put them in system environment variables.

1. `VISUAL_CROSSING_API_KEY`=Your API Key 
2. `REDIS_HOST`=localhost 
3. `REDIS_PORT`=6379 
4. `WEATHER_API_CACHE_DURATION_SECONDS`=Number of seconds to cache the weather data

### Start Redis Container

You can run redis server however you want. The easiest and recommended approach is to use docker container for that. I have included a `docker-compose.yaml` file which can be used with `docker` cli to spin a redis container up. Run the following command to start the redis up:

```shell
docker compose up -d
```

After that you can run the application as a normal Spring Boot Application.
