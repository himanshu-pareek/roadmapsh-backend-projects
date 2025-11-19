#!/bin/bash

url="http://localhost:8080/images/$1/download"

if [[ -n $2 ]]; then
  url="$url?transformationId=$2"
fi

echo "Click - $url"
