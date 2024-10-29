#!/bin/bash

set -e

FILENAME=data/expenses.csv
IDS_SEEN=()

function should_visit_id() {
    if [[ ! "$1" =~ ^[0-9]+(\.[0-9]+)?$ ]]; then
        echo 0
        exit 0
    fi
    for seen_id in "${IDS_SEEN[@]}"
    do
        if [[ "${seen_id}" == "$1" ]]; then
            echo 0
            exit 0
        fi
    done
    echo 1
}

if [[ -f "$FILENAME" ]]; then
    # Read the CSV file in reverse order and skip the header
    tac "$FILENAME" | while IFS=',' read -r id dt category description amount removed
    do
        if [[ $(should_visit_id "$id") -eq "1" ]]; then
            if [[ "$removed" -ne 1 ]]; then
                # Expense is present (not removed)
                echo "------------- ${description} --------------"
                echo "🪪 ${id}, 💰 \$${amount}, 🗓️ ${dt}"
                echo ""
            fi
            IDS_SEEN+=("$id")
        fi
    done
fi
