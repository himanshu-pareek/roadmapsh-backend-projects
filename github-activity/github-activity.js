const fs = require('fs')

const ACTIVITY_URL = 'https://api.github.com/users/{USERNAME}/events'

async function handleUsername (username = '') {
    console.log(`Getting github activity for ${username} ...`)
    const activityUrl = getActivityUrl(username)
    try {
        const activities = await getActivities(activityUrl)
        activities.map(activity => getMessage(activity))
            .filter(message => message)
            .forEach(x => console.log(x))
    } catch (e) {
        console.error(e.toString())
    }
}

function getActivityUrl(username = '') {
    return ACTIVITY_URL.replace('{USERNAME}', username)
}

async function getActivities(activityUrl = '') {
    console.log(`Sending request to ${activityUrl} ...`)
    const res = await fetch(activityUrl)
    if (res.status == 404) {
        throw new Error('User not found. Try with another username.')
    }
    console.log('Got response. Parsing response...\n')
    return res.json()
}

function getMessage(event = { type: '' }) {
    switch(event.type) {
        case 'CreateEvent':
            return getMessageForCreateEvent(event)
        case 'DeleteEvent':
            return getMessageForDeleteEvent(event)
        case 'ForkEvent':
            return getMessageForForkEvent(event)
        default:
            return null
    }
}

// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#createevent
function getMessageForCreateEvent(event = { payload: { ref: '', ref_type: '' } }) {
    switch (event?.payload?.ref_type) {
        case 'repository':
            return getMessageForRepositoryCreateEvent(event)
        case 'branch':
            return getMessageForBranchCreateEvent(event)
        case 'tag':
            return getMessageForTagCreateEvent(event)
        default:
            return null
    }
}

// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#deleteevent
function getMessageForDeleteEvent(event = { payload: { ref: '', ref_type: '' } }) {
    switch(event?.payload?.ref_type) {
        case 'branch':
            return getMessageForBranchDeleteEvent(event)
        case 'tag':
            return getMessageForTagDeleteEvent(event)
        default:
            return null
    }
}

// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#forkevent
function getMessageForForkEvent(event) {
    if (event?.repo?.name && event?.payload?.forkee?.name) {
        return `Forked repository ${event.repo.name} to ${event.payload.forkee.name}`
    }
    return null
}

function getMessageForRepositoryCreateEvent(event = { repo: { name: '' } }) {
    if (!event?.repo?.name) {
        return null;
    }
    return `Created repository ${event.repo.name}`
}

function getMessageForBranchCreateEvent(event = { repo: { name: '' }, payload: { ref: '' } }) {
    if (event?.repo?.name && event?.payload?.ref) {
        return `Created branch ${event.payload.ref} inside repository ${event.repo.name}`
    }
    return null
}

function getMessageForTagCreateEvent(event = { repo: { name: '' }, payload: { ref: '' } }) {
    if (event?.repo?.name && event?.payload?.ref) {
        return `Created tag ${event.payload.ref} inside repository ${event.repo.name}`
    }
    return null
}

function getMessageForBranchDeleteEvent(event = { repo: { name: '' }, payload: { ref: '' } }) {
    if (event?.repo?.name && event?.payload?.ref) {
        return `Deleted branch ${event.payload.ref} from repository ${event.repo.name}`
    }
    return null
}

function getMessageForTagDeleteEvent(event = { repo: { name: '' }, payload: { ref: '' } }) {
    if (event?.repo?.name && event?.payload?.ref) {
        return `Deleted tag ${event.payload.ref} from repository ${event.repo.name}`
    }
    return null
}

function main(args = []) {
    if (args.length != 1) {
        usage()
    }
    if (!args[0]) {
        usage()
    }
    const username = args[0].trim()
    if (username.length == 0) {
        usage()
    }
    handleUsername(username)
    // handleUsername('lsmoosn')
}

function usage() {
    console.error('Usage: ./github-activity <username>')
    process.exit(1)
}

main(process.argv.splice(2))
