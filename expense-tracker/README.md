# Expense Tracker

A CLI application to track, view, summarize and delete expenses.

[Project URL](https://roadmap.sh/projects/expense-tracker)

## Running the project

### Requirements

1. Bash must be installed in the system. The script will try to use base installed in directory `/bin/bash`, which should be fine for most of the linux systems. For windows operating system, you may try to install git bash and use that.

### Steps

1. Clone the github repository from `https://github.com/himanshu-pareek/roadmapsh-backend-projects`.
    ```shell
    git clone https://github.com/himanshu-pareek/roadmapsh-backend-projects.git
    ```
2. Go to `expense-tracker` directory:
    ```shell
    cd roadmap-backend-projects
    cd expense-tracker
    ```
3. Use the CLI to manipulate the expenses and get the information related to them.

### Getting help

Run the below command to get the help for the CLI:

```shell
./expense-tracker help
```

### Adding expense

To add an expense, the amount and description are necessary. You can also provide optional category field. Default category is none, which is representated by hypen(`-`). Below are some commands to add expenses:

```shell
# Add expense without category
./expense-tracker add --amount 100 --description "Trip to Ooty"

# Add expense with category
./expense-tracker add --amount 200 --description "Movie tickets" --category entertainment

# Using short argument names
./expense-tracker add -a 20 -d "Travel home" -c travel
```

Once expense is added, you will see the `id` assigned to the expense. The `id` can be used to delete the expense.

### Displaying list of expenses

Run the following command to see the list of all of the expenses. The list contains `id`, `description`, `category`, `date` and `amount` for each expense

```shell
./expense-tracker list
```

If you want the list of all the expenses for a particular category, then run the following command:

```shell
./expense-tracker list --category food

./expense-tracker list -c travel
```

### Deleting expense

Run the following command in order to delete an expense by specifying correct `id` of the expense.

```shell
./expense-tracker delete --id 10
```

### Expenses summary

You can view the summary of the expenses by category. That means, summary contains total spends on each category. You can specify the time frame for summary. Time frame can be for given year, given month of given year, given month of current year or all expenses. Below are some examples:

```shell
# View summary of expenses for year 2023
./expense-tracker summary --year 2023

# View summary of expenses for January, 2023
./expense-tracker summary --year 2023 --month 1

# View summary of expenses for November, current year
./expense-tracker summary --month 11

# View summary of all expenses
./expense-tracker summary
```

### Setting budget

You can set budget for a month or all months in given year.

In order to set a budget for year *July, 2024* of *2000*, run the following command:

```shell
./expense-tracker budget --year 2024 --month 7 --amount 2000
```

In order to set a budget for all months of year *2023* of *1200*, run the following command:

```shell
./expense-tracker budget --year 2023 --amount 1200
```

In order to set a budget of 3000 for month of November of current year, run the following command:

```shell
./expense-tracker budget --month 11 --amount 3000
```

In order to set a budget of 4000 for current month of current year, run the following command:

```shell
./expense-tracker budget --amount 4000
```

