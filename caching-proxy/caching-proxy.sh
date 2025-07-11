#!/usr/bin/bash

set -e

function show_usage {
    echo "To run the server on port <port> to act as a proxy server for <origin>:"
    echo "$ caching-proxy --port <port> --origin <origin>"
    echo ""
    echo "Example - caching-proxy --port 8080 --origin https://jsonplaceholder.typicode.com"
    echo ""
    echo "To clear the cache of proxy server:"
    echo "$ caching-proxy --clear-cache"
    echo ""
}

function start_redis_server {
    redisPort=$1
    echo "Starting redis on port ${redisPort}..."
    docker compose down
    REDIS_PORT=${redisPort} docker compose up -d
    echo "📦 Redis started on port ${redisPort}"
}

function start_java_application {
    port=$1
    redisPort=$2
    origin=$3
    cacheDurationSeconds=$4

    echo ""
    echo "Starting Java Application on port ${port} and origin ${origin}"
    echo ""
    SERVER_PORT=${port} REDIS_HOST=localhost REDIS_PORT=${redisPort} PROXY_UPSTREAM_SERVER_HOST=${origin} CACHE_DURATION_SECONDS=${cacheDurationSeconds} ./gradlew bootRun
    echo ""
    echo "Started Java Application"
}

function start_server {
    port=$1
    origin=$2
    cacheDurationSeconds=$3

    redisPort=6379
    if [ $port = $redisPort ]; then
        redisPort=6380
    fi

    start_redis_server ${redisPort}
    
    start_java_application ${port} ${redisPort} ${origin} ${cacheDurationSeconds}
}

start_server 6379 https://jsonplaceholder.typicode.com 60


