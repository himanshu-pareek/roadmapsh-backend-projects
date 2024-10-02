const fs = require('fs')
const { getMessageForIssuesEvent } = require('./issue-event-util')
const { getMessageForMemberEvent } = require('./member-event-util')
const { getMessageForPublicEvent } = require('./public-event-util')
const ACTIVITY_URL = 'https://api.github.com/users/{USERNAME}/events'

async function handleUsername(username = '') {
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
    switch (event.type) {
        case 'CreateEvent':
            return getMessageForCreateEvent(event)
        case 'DeleteEvent':
            return getMessageForDeleteEvent(event)
        case 'ForkEvent':
            return getMessageForForkEvent(event)
        case 'GollumEvent':
            return getMessageForGollumEvent(event)
        case 'IssueCommentEvent':
            return getMessageForIssueCommentEvent(event)
        case 'IssuesEvent':
            return getMessageForIssuesEvent(event)
        case 'MemberEvent':
            return getMessageForMemberEvent(event)
        case 'PublicEvent':
            return getMessageForPublicEvent(event)
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
    switch (event?.payload?.ref_type) {
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

// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#gollumevent
function getMessageForGollumEvent(event) {
    if (event?.repo?.name && event?.payload?.pages) {
        const pages = event.payload.pages
        const created = pages.findIndex(page => page.action === 'created') != -1
        const edited = pages.findIndex(page => page.action === 'edited') != -1
        let messagePrefix = null
        if (created && edited) {
            messagePrefix = 'Created / edited'
        } else if (created) {
            messagePrefix = 'Created'
        } else if (edited) {
            messagePrefix = 'Edited'
        }
        if (!messagePrefix) {
            return null
        }
        if (pages.length === 1) {
            return `${messagePrefix} wiki page ${pages[0].page_name} in ${event.repo.name}`
        }
        return `${messagePrefix} ${pages.length} wiki pages in ${event.repo.name}`
    }
    return null
}

// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#issuecommentevent
function getMessageForIssueCommentEvent(event) {
    const action = event?.payload?.action
    let actionMessage = null
    switch (action) {
        case 'created':
            actionMessage = 'Created'
            break
        case 'edited':
            actionMessage = 'Edited'
            break
        case 'deleted':
            actionMessage = 'Deleted'
            break
    }
    const issueNumber = event?.payload?.issue?.number
    const repoName = event?.repo?.name
    if (actionMessage && issueNumber && repoName) {
        return `${actionMessage} a comment on #${issueNumber} in ${repoName}`
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
}

function usage() {
    console.error('Usage: ./github-activity <username>')
    process.exit(1)
}

main(process.argv.splice(2))
