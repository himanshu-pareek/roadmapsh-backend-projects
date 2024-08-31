# Task Tracker CLI Application

[Project URL](https://roadmap.sh/projects/task-tracker)

## Running the project

### Requirements

1. **Node.js** must be instaled on the system. [Click here](https://nodejs.org/en) to install it. I have used version `20.17.0` for the project. It should work with other versions as well.

### Steps

- Clone the github repository from `https://github.com/himanshu-pareek/roadmapsh-backend-projects`.
    ```shell
    git clone https://github.com/himanshu-pareek/roadmapsh-backend-projects.git
    ```
- Go to `task-tracker` directory:
    ```shell
    cd roadmap-backend-projects
    cd task-tracker
    ```
- Use `task-cli` script to run the project.
- To get the help, run `./task-cli help`.
- To add a new task, run the following command specifying the description of the task to create. You will get back the **id** of the task.
    ```shell
    ./task-cli add "Learn Javascript"
    ```
- To update an existing task, use the following command. You need to specify the **id** of the task and updated **description** of the task.
    ```shell
    ./task-cli update 7 "Learn Java"
    ```
- To delete an existing task, use the following command specifying the **id** of the task to delete:
    ```shell
    ./task-cli delete 1
    ```
- To mark a task as **In Progress**, use the following command specifying the **id** of the task:
    ```shell
    ./task-cli mark-in-progress 7
    ```
- To mark a task as **Done**, use the following command specifying the **id** of the task:
    ```shell
    ./task-cli mark-done 7
    ```
- To display the lis of all the task available, use the following command:
    ```shell
    ./task-cli list
    ```
- To display the list of all the task which are marked as **Todo**, that means not started tasks, use the following command:
    ```shell
    ./task-cli list todo
    ```
- To display the list of all the task which are marked as **In Progress**, use the following command:
    ```shell
    ./task-cli list in-progress
    ```
- To display the list of all the task which are marked as **Done**, use the following command:
    ```shell
    ./task-cli list done
    ```
- To see the help for running the script, run the following command:
    ```shell
    ./task-cli help
    ```
