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

  echo "✅  Added expense with ID - ${id}"
}

delete() {
  id="-"
  idProvided=0
  while [[ $# > 0 ]]; do
    case "$1" in
      "--id")
        if [[ $# < 2 ]]; then
          usage
        fi
        id="$2"
        idProvided=1
        shift
        shift
        ;;
      *)
        echo "Unrecognized argument: $1"
        usage
    esac
  done
  echo "Deleting expense with id - ${id}"
  expense_file="${EXPENSES_DIR}/${id}.expense"
  # 1. Check if the expense file exists
  if [[ ! -f "${expense_file}" ]]; then
    echo "No expense found with id ${id}"
    exit 0
  fi

  # 2. Read the file to get data
  expense_date=`head -n 1 "${expense_file}"`
  category=`head -n 2 "${expense_file}" | tail -n 1`
  amount=`head -n 4 "${expense_file}" | tail -n 1`

  # 3. Calculate the month from expense_date
  expense_month=`dateToYearMonth "${expense_date}"`

  # 4. Reduce the spending for category by expense's amount
  # 4.1 Check if file exists
  summary_file="${SUMMARIES_DIR}/${expense_month}/${category}.spending"
  if [[ -f "${summary_file}" ]]; then
    # 4.2 Read the previoud spending from the file
    previous_spending=`cat "${summary_file}"`
    # 4.3 Calculate new spending
    remaining_spending=$(echo "${previous_spending}-${amount}" | bc)
    # 4.4 Write remaining spending back to the summary file
    echo "${remaining_spending}" > "${summary_file}"
  fi

  # 5. Delete the expense file
  rm "${expense_file}"

  echo "🗑️  Deleted expense with id ${id}"
}

summary() {
  echo "Summary"
}

budget() {
  echo "Budget"
}

dateToYearMonth() {
  year=`echo $1 | cut -d "-" -f 1`
  month=`echo $1 | cut -d "-" -f 2`
  echo "$year-$month"
}

"$@"

