#!/bin/bash

curl "localhost:8080/images/$1/transform" \
  -u bob:password \
  -H "Content-Type: application/json" \
  -d "$2"
