# Caching Proxy Server

A caching server that caches responses from other servers

This server is written in Java and uses Redis for cache database.

## Running the project

The following pieces of software (tools) should be installed on your system:

1. Java-21
2. docker

In order to run the caching proxy server on port `8000` to cache the responses of `https://jsonplaceholder.typicode.com`, run the following command:

```shell
./caching-proxy --port 8000 --origin https://jsonplaceholder.typicode.com
```

Then if you need to make a request to endpoint, `https://jsonplaceholder.typicode.com/todos/1` using the *caching proxy server*, then send a request to `http://localhost:8000/todos/1` endpoint. You will see a response header `X-cache` with value `MISS` or `HIT` depending on whether the response is coming from the upstream server of from the cache.

If you need to clear cache, then run the following command in a new terminal:

```shell
./caching-proxy --clear-cache
```

By default, the response is cached for **1 hour** in the cache. If you need to change this behaviour, then you can set the TTL in seconds while starting up the server by providing an optional argument `--ttl` with the value of ttl for response in seconds. For example,

```shell
./caching-proxy --port 8000 --origin https://jsonplaceholder.typicode.com --ttl 7200 # Response will be cached for 2 hours in the cache
```

You can run the following command to get the usage documentation of the `caching-proxy` script:

```shell
./caching-proxy --help
```

## Useful links

1. Project requirements - https://roadmap.sh/projects/caching-server
2. Youtube video - https://youtu.be/ja5evmFAGac?si=Zr44lBHM1zxCuqUM
