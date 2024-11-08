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

DATA_DIR=".data"
EXPENSES_DIR="${DATA_DIR}/expenses"
SUMMARIES_DIR="${DATA_DIR}/summaries"
BUDGETS_DIR="${DATA_DIR}/budgets"

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
  echo "Adding expense with:"
  echo "    Category - $category"
  echo "    Description - $description"
  echo "    Amount - $amount"
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
  current_date="$(./current-date.sh)"
  # 2. Get the current month (YYYY-MM)
  current_month="$(./current-date.sh --year-month)"
  # 3. Get the id to add expense
  id="$(./generate-id.sh)"

  # 4. Add the expense to expenses
  # 4.1 Create expenses directory if not exists
  mkdir -p "${EXPENSES_DIR}"
  # 4.2 Write expense to the file
  expense_file="${EXPENSES_DIR}/${id}.expense"
  echo "${current_date}" > "${expense_file}"
  echo "${category}" >> "${expense_file}"
  echo "${description}" >> "${expense_file}"
  echo "${amount}" >> "${expense_file}"

  # 5. Update the summary for given month
  # 5.1 Create the summary directory if not present
  summary_directory="${SUMMARIES_DIR}/${current_month}"
  mkdir -p "${summary_directory}"
  # 5.2 Create file with zero spending for category if not present
  summary_file="${summary_directory}/${category}.spending"
  if [[ ! -f "${summary_file}" ]]; then
    echo 0 > "${summary_file}"
  fi
  # 5.3 Read the summary file to get previous spending
  previous_spending=`cat "${summary_file}"`
  # 5.4 Calculate the total spending by adding amount
  let total_spending=previous_spending+amount
  # 5.5 Store the total spending in summary file
  echo "${total_spending}" > "${summary_file}"

  echo "Added expense with ID - ${id}"
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

