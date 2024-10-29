#!/bin/bash

# Expense (id, date, description, amount)
# id - auto generated, date - when the script is executed
# description, amount, category - user provided

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
    shift
    CATEGORY="-"
    while [[ $# -gt 0 ]]; do
      key="$1"
      case "$key" in
        "--description")
          DESCRIPTION="$2"
          DESCRIPTION_PROVIDED=1
          shift
          shift
          ;;
        "-d")
          DESCRIPTION="$2"
          DESCRIPTION_PROVIDED=1
          shift
          shift
          ;;
        "--amount")
          AMOUNT="$2"
          AMOUNT_PROVIDED=1
          shift
          shift
          ;;
        "-a")
          AMOUNT="$2"
          AMOUNT_PROVIDED=1
          shift
          shift
          ;;
        "--category")
          CATEGORY="$2"
          shift
          shift
          ;;
        "-c")
          CATEGORY="$2"
          shift
          shift
          ;;
        *)
          echo "Unknown option: $key"
          exit 1
          ;;

      esac

    done

    if [[ $DESCRIPTION_PROVIDED -ne 1 ]]; then
      echo "Expense's description not provided"
      exit 1
    fi

    if [[ $AMOUNT_PROVIDED -ne 1 ]]; then
      echo "Expenses's amount not provided"
      exit 1
    fi

    if [[ ! "$AMOUNT" =~ ^[0-9]+(\.[0-9]+)?$ ]]; then
      echo "$AMOUNT is not a valid amount for an expense"
      exit 1
    fi

    echo "Adding expense '$DESCRIPTION' for amount \$$AMOUNT in category $CATEGORY"

    ID=$(./generate_id.sh)
    DATE=$(./current_date.sh)

    ./expense-save.sh $ID $DATE "$CATEGORY" "$DESCRIPTION" $AMOUNT
    ;;
  "list")
    # Display list of all expenses
    ./expenses-display.sh || usage
    ;;
  *)
    echo "No match"
    usage
    exit 1

esac
