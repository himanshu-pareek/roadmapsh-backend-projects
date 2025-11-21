# Image Processing Service

Project listing - [Image Processing Service](https://roadmap.sh/projects/image-processing-service)

This project is an image processing service that allows users to upload, transform, and download images.

## Architecture

The project is a Spring Boot application built with Java `21`. It follows a multi-project structure, with each subproject having a specific responsibility:

- **api**: The main web application that exposes the REST API for image operations.
- **core**: Contains the core business logic for image processing.
- **mongodb**: Handles all interactions with the MongoDB database.
- **amqp/producer**: Responsible for sending messages to RabbitMQ.
- **amqp/consumer**: Responsible for consuming messages from RabbitMQ.
- **filesystem**: Manages interactions with the local filesystem.
- **aws**: Integrates with AWS services.
- **redis**: Manages interactions with the Redis cache.
- **opencv**: Works with images using opencv library.
- **processor**: Processes the images

## External Components

The following external components are required to run the project:

- **RabbitMQ**: Used for asynchronous message queuing.
- **Redis**: Used for caching.
- **MongoDB**: Used for storing the image data.
- **AWS S3**: Used to store files (images). Any other storage service that is compatible with AWS S3 apis can be used. Or, you can just use local filesystem.

## Setup and Run

1.  **Start External Services**: Use the `compose.yaml` file to start the required external services:
    ```shell
    docker-compose up -d
    ```
    
    You can modify the `compose.yaml` file according to your needs. For example, you can remove the `rabbitmq` service for the file if the rabbitmq is hosted somewhere  else.

2.  **Create `.env` files**: The `api`  and `processor` subprojects require a `.env` file each with the necessary environment variables. Create a `.env` file in the `api` and `processor` directories each with the following content:
    ```shell
    MONGODB_CONNECTION_URI=<connection_string for mongodb>
    
    # Replace with Redis host and port
    REDIS_HOST=localhost
    REDIS_PORT=6379
    
    # Replace with RabbitMQ Configuration
    RABBITMQ_HOST=localhost
    RABBITMQ_PORT=5672
    RABBITMQ_USERNAME=<username goes here>
    RABBITMQ_PASSWORD=<password goes here>
    
    # Remove aws-s3 if you want to use local filesystem to store the files (images)
    # Remove local if you don't want the configurations listed in application-local.properties file
    SPRING_PROFILES_ACTIVE=init,aws-s3,local
    
    # AWS S3 configuration if `aws-s3` profile is active
    AWS_ACCESS_KEY=<access key id>
    AWS_SECRET_KEY=<secret access key>
    AWS_S3_ENDPOINT=<aws s3 endpoint>
    AWS_S3_BUCKET=<aws s3 bucket to store images>
    ```
3.  **Run the Application**:

    * Run the `ImageProcessingApi` class in the `api` subproject to start the application. If using IntelliJ IDEA, then `ImageProcessingApi` run configuration is available to be run. Optionally, run the following command to run api:

       ```shell
       ./gradlew :api:bootRun
       ```
    * Run the `ImageProcessorService` class in the `processor` subproject to start the processor. If using IntelliJ IDEA, then `ImageProcessorService` run configuration is available to be run. Optionally, run the following command to run api:

       ```shell
       ./gradlew :processor:bootRun
       ```

## APIs Exposed

The following APIs are exposed by the service:

- `POST /images`: Upload an image.
  - **Request Body**: `multipart/form-data` with `title` (String) and `file` (file).
  - **Response**: `ImageResponse` JSON object.
- `GET /images`: Get a list of images for the authenticated user.
  - **Response**: `ImageListResponse` JSON object.
- `POST /images/{imageId}/transform`: Apply transformations to an image.
  - **Path Variable**: `imageId` (String).
  - **Request Body**: `ImageTransformationRequest` JSON object.
  - **Response**: `ImageTransformation` JSON object.
- `GET /images/{imageId}/download`: Download an image, with an optional transformation.
  - **Path Variable**: `imageId` (String).
  - **Query Parameter**: `transformationId` (String, optional).
  - **Response**: The image file.

> Note: Look in the `scripts` directory for sample requests
