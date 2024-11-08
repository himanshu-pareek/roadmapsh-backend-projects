#!/bin/bash

set -e

cd $(dirname $0)

function usage() {
  echo "Usage goes here..."
  if [[ $1 == "success" ]]; then
    exit 0
  else
    exit 1
  fi
}

add() {
  category="-"
  amountProvided=0
  descriptionProvided=0
  while [[ $# > 0 ]]; do
    case "$1" in
      "--amount")
        amount="$2"
        amountProvided=1
        shift
        shift
        ;;
      "-a")
        amount="$2"
        amountProvided=1
        shift
        shift
        ;;
      "--description")
        description="$2"
        descriptionProvided=1
        shift
        shift
        ;;
      "-d")
        description="$2"
        descriptionProvided=1
        shift
        shift
        ;;
      "--category")
        category="$2"
        shift
        shift
        ;;
      "-c")
        category="$2"
        shift
        shift
        ;;
      *)
        echo "Invalid argument: $1"
        usage
    esac
  done
  echo "Category - $category"
  echo "Description - $description"
  echo "Amount - $amount"
  if [[ $amountProvided -ne 1 ]]; then
    echo "Amount is required to add an expense."
    usage
  fi

  if [[ $descriptionProvided -ne 1 ]]; then
    echo "Description is required to add an expense."
    usage
  fi

  if [[ ! $amount =~ ^[0-9]+(\.[0-9]+)?$ ]]; then
    echo "Amount must be a number."
    usage
  fi

  # 1. Get the current date (YYYY-MM-DD)
  #
  # 2. Get the current month (YYYY-MM)
  #
  # 3. Get the id to add expense
}

delete() {
  echo "Delete"
}

summary() {
  echo "Summary"
}

budget() {
  echo "Budget"
}

"$@"

