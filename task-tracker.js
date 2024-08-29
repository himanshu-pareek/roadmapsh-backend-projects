const fs = require('fs');

const FILE_NAME = "tasks.json";

main(process.argv.splice(2));

function handleAddCommand(args = []) {
    if (args.length < 2) {
        console.error('ERROR: Please specify the task description');
        console.error('Ex. task-cli add "Buy Groceries"');
        return;
    }
    const taskDescription = args[1].trim();
    if (taskDescription.length == 0) {
        throw new Error("Task description must not be empty");
    }
    const createdTask = createTask(taskDescription);
    console.log(`Task added successfully (ID: ${createdTask.id})`);
}

function createTask(description = "") {
    let tasks = getAllTasks();
    const task = taskFromIdAndDescription(tasks.nextId, description);
    tasks.items.push(task);
    tasks.nextId += 1;
    saveAllTasks(tasks);
    return task;
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
    }
}

function usage() {
    console.error("Usage: todo");
    process.exit(1);
}
