const fs = require('fs')

const FILE_NAME = "tasks.json"

const STATUS_TO_LABEL = new Map()
STATUS_TO_LABEL.set('todo', 'Todo')
STATUS_TO_LABEL.set('in-progress', 'In progress')
STATUS_TO_LABEL.set('done', 'Done')

const STATUS_TO_SYMBOL = new Map()
STATUS_TO_SYMBOL.set('todo', '😊')
STATUS_TO_SYMBOL.set('in-progress', '⏳')
STATUS_TO_SYMBOL.set('done', '✅')

main(process.argv.splice(2))

function handleAddCommand(args = []) {
    if (args.length < 2) {
        console.error('ERROR: Please specify the task description')
        console.error('Ex. task-cli add "Buy Groceries"')
        process.exit(1)
    }
    const taskDescription = args[1].trim()
    validateTaskDescription(taskDescription)
    const createdTask = createTask(taskDescription)
    console.log(`Task added successfully (ID: ${createdTask.id})`)
}

function handleUpdateCommand(args = []) {
    if (args.length < 3) {
        console.error('ERROR: Please specify the task id and updated task description')
        console.error('Ex. task-cli update 1 "Cook dinner"')
        process.exit(1)
    }
    const id = parseInt(args[1].trim())
    const description = args[2].trim()
    validateTaskId(id)
    validateTaskDescription(description)
    try {
        const oldDescription = updateTask(id, description)
        console.log(`Task (ID: ${id}) updated successfully.`)
        console.log(`${oldDescription} -> ${description}`)
    } catch (e) {
        console.error(e.toString())
    }
}

function handleDeleteCommand(args = []) {
    if (args.length < 2) {
        console.error('ERROR: Please specify the task id to delete')
        console.error('Ex. task-cli delete 1')
        process.exit(1)
    }
    const id = parseInt(args[1].trim())
    validateTaskId(id)
    try {
        const task = deleteTask(id)
        console.log(`Task "${task.description}" (id = ${id}, status = ${task.status}) deleted successfully.`)
    } catch (e) {
        console.error(e.toString())
    }
}

function handleMarkInProgressCommand(args = []) {
    if (args.length < 2) {
        console.error('ERROR: Please specify the task id to mark in progress')
        console.error('Ex. task-cli mark-in-progress 1')
        process.exit(1)
    }
    handleChangeStatusCommand(args, 'in-progress')
}

function handleMarkDoneCommand(args = []) {
    if (args.length < 2) {
        console.error('ERROR: Please specify the task id to mark done')
        console.error('Ex. task-cli mark-done 1')
        process.exit(1)
    }
    handleChangeStatusCommand(args, 'done')
}

function handleListCommand(args = []) {
    if (args.length > 1) {
        handleListByStatusCommand(args[1].trim())
        return
    }
    displayAllTasks()
}

function handleListByStatusCommand(status = '') {
    if (!STATUS_TO_LABEL.has(status)) {
        console.error('ERROR: Invalid task status: ' + status)
        console.error(`VALID STATUS: todo / in-progress / done`)
        process.exit(1)
    }
    displayAllTasksByStatus(status)
}

function handleChangeStatusCommand(args = [], status) {
    const id = parseInt(args[1].trim())
    validateTaskId(id)
    try {
        const task = changeTaskStatus(id, status)
        console.log(`Task "${task.description}" is marked as "${task.status}"`)
    } catch (e) {
        console.error(e.toString())
    }
}

function createTask(description = "") {
    const tasks = getAllTasks();
    const task = taskFromIdAndDescription(tasks.nextId, description);
    tasks.items.push(task);
    tasks.nextId += 1;
    saveAllTasks(tasks);
    return task;
}

function updateTask(id, description) {
    const tasks = getAllTasks()
    const task = getMatchingTask(tasks, id)
    if (!task) {
        throw new Error(`Task with id = ${id} does not exist`)
    }
    const oldDescription = task.description
    task.description = description
    task.updatedAt = new Date()
    saveAllTasks(tasks)
    return oldDescription
}

function deleteTask(id) {
    const tasks = getAllTasks()
    const task = getMatchingTask(tasks, id)
    if (!task) {
        throw new Error(`Task with id = ${id} does not exist`);
    }
    removeMatchingTask(tasks, id)
    saveAllTasks(tasks)
    return task
}

function changeTaskStatus(id, status) {
    const tasks = getAllTasks()
    const task = getMatchingTask(tasks, id)
    if (!task) {
        throw new Error(`Task with id = ${id} does not exist`);
    }
    task.status = status
    saveAllTasks(tasks)
    return task
}

function displayAllTasks() {
    const tasks = getAllTasks()
    let legendText = ''
    STATUS_TO_SYMBOL.forEach((value, key) => {
        legendText += `${value} ${STATUS_TO_LABEL.get(key)}  `
    })
    console.log(`Here are all the tasks (${legendText})\n`)
    tasks.items.map(getTaskToDisplay)
        .forEach(displayTask)
}

function displayAllTasksByStatus(status = '') {
    console.log(`Here are all ${STATUS_TO_SYMBOL.get(status)} ${STATUS_TO_LABEL.get(status)} tasks\n`)
    getAllTasksByStatus(status)
        .map(getTaskToDisplay)
        .forEach(displayTaskWithoutStatus)
}

function getMatchingTask(tasks, id) {
    for (let task of tasks.items) {
        if (task.id === id) {
            return task
        }
    }
    return null
}

function removeMatchingTask(tasks, id) {
    tasks.items = tasks.items.filter(task => task.id !== id)
}

function getAllTasks() {
    if (fs.existsSync(FILE_NAME)) {
        const content = fs.readFileSync(FILE_NAME).toString();
        return JSON.parse(content);
    }
    return {
        nextId: 1,
        items: []
    };
}

function getAllTasksByStatus(status = '') {
    return getAllTasks().items.filter(task => task.status === status)
}

function saveAllTasks(tasks) {
    fs.writeFileSync(FILE_NAME, JSON.stringify(tasks, undefined, 4));
}

function taskFromIdAndDescription(id, description) {
    const now = new Date();
    return {
        id,
        description,
        status: 'todo',
        createdAt: now,
        updatedAt: now,
    };
}

function validateTaskDescription(description) {
    if (description.length == 0) {
        console.error('ERROR: Task description must not be empty');
        process.exit(1);
    }
}

function validateTaskId(id) {
    if (isNaN(id)) {
        console.error('ERROR: Task id must be an integer');
        process.exit(1);
    }
}

function displayTask(task) {
    console.log(`${task.status} ${task.description} (ID: ${task.id}, Last updated: ${task.updatedAt})`)
}

function displayTaskWithoutStatus(task) {
    console.log(`${task.description} (ID: ${task.id}, Last updated: ${task.updatedAt})`)
}

function getTaskToDisplay(task = { id: 1, description: '', status: '', createdAt: new Date(), updatedAt: new Date() }) {
    return {
        ...task,
        status: STATUS_TO_SYMBOL.get(task.status),
        createdAt: getDateToDisplay(task.createdAt),
        updatedAt: getDateToDisplay(task.updatedAt)
    }
}

function getDateToDisplay(date = '') {
    return new Date(date).toUTCString()
}

function main(args = []) {
    if (args.length == 0) {
        usage();
    }
    const command = args[0].trim()
    if (command === 'add') {
        handleAddCommand(args)
    } else if (command === 'update') {
        handleUpdateCommand(args)
    } else if (command === 'delete') {
        handleDeleteCommand(args)
    } else if (command === 'mark-in-progress') {
        handleMarkInProgressCommand(args)
    } else if (command === 'mark-done') {
        handleMarkDoneCommand(args)
    } else if (command === 'list') {
        handleListCommand(args)
    }
}

function usage() {
    console.error("Usage: todo");
    process.exit(1);
}
