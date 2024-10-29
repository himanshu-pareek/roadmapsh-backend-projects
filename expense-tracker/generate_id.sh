#!/bin/bash
set -e

mkdir -p data

ID=1

if [[ -f "data/id" ]]; then
  ID=$(cat data/id)
fi

let next_id=$ID+1

echo $next_id > data/id
echo $ID

