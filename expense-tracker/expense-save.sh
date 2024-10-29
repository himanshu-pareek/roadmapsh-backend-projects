#!/bin/bash
set -e

if [[ $# -ne 5 ]]; then
  exit 1
fi

FILENAME='data/expenses.csv'
CSV_DELIMETER=','

id=$1
dt=$2
category=$3
description=$4
amount=$5

# Create file if not exists
if [[ ! -f $FILENAME ]]; then
  echo "id,date,category,description,amount" > $FILENAME
fi

row_to_write="$id$CSV_DELIMETER$dt$CSV_DELIMETER$category$CSV_DELIMETER$description$CSV_DELIMETER$amount"

# Option 1:
#   If any expense with given id exists in file, replace that
#   Otherwise, add new expense to the end of the file
# Option 2:
#   Always add expense at the end of the file
#   While reading, read the expense from the end of the file

echo $row_to_write >> "$FILENAME"

