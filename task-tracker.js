const fs = require('fs')

const FILE_NAME = "tasks.json"

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

function saveAllTasks(tasks) {
    fs.writeFileSync(FILE_NAME, JSON.stringify(tasks, undefined, 4));
}

function taskFromIdAndDescription(id, description) {
    const now = new Date();
    return {
        id,
        description,
        status: 'not-started',
        createdAt: now,
        updatedAt: now,
    };
}

function main(args = []) {
    if (args.length == 0) {
        usage();
    }
    const command = args[0].trim()
    if (command === 'add') {
        handleAddCommand(args);
    } else if (command === 'update') {
        handleUpdateCommand(args);
    } else if (command === 'delete') {
        handleDeleteCommand(args)
    }
}

function usage() {
    console.error("Usage: todo");
    process.exit(1);
}
