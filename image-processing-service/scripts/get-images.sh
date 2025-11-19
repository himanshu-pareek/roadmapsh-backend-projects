#!/bin/bash

dir=$(dirname "$0")

output_file="$dir/images.json"

curl localhost:8080/images -u bob:password | jq > "$output_file"

echo "Look at $output_file for output"

