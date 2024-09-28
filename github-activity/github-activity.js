const fs = require('fs')

const ACTIVITY_URL = 'https://api.github.com/users/{USERNAME}/events'

async function handleUsername (username = '') {
    console.log(`Getting github activity for ${username} ...`)
    const activityUrl = getActivityUrl(username)
    const activities = await getActivities(activityUrl)
}

function getActivityUrl(username = '') {
    return ACTIVITY_URL.replace('{USERNAME}', username)
}

async function getActivities(activityUrl = '') {
    console.log(`Sending request to ${activityUrl} ...`)
    const res = await fetch(activityUrl)
    const json = await res.json()
    console.log('Got response. Parsing response...')
    fs.writeFileSync("actitities.json", JSON.stringify(json, undefined, 4))
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