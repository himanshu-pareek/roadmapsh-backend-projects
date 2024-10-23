#!/bin/bash

usage() {
  echo "Find usage here"
}

# If no arguments provided
if [[ $# -eq 0 ]]; then
  usage
  exit 1
fi

COMMAND="$1"

case $COMMAND in

  "help")
    usage
    ;;
  "add")
    echo "Add something"
    ;;
  *)
    echo "No match"
    usage
    exit 1

esac

