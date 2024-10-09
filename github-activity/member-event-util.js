const { getRepositoryNameToDisplay } = require("./repo-util")

// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#memberevent
module.exports.getMessageForMemberEvent = (event) => {
    const collaborator = event?.payload?.member?.login
    const repositoryName = getRepositoryNameToDisplay(event)
    switch (event?.payload?.action) {
        case 'added':
            return getMessageForMemberAddedEvent(repositoryName, collaborator)
        case 'removed':
            return getMessageForMemberRemovedEvent(repositoryName, collaborator)
        case 'edited':
            return getMessageForMemberEditedEvent(repositoryName, collaborator)
    }
}

function getMessageForMemberAddedEvent(repositoryName, collaborator) {
    if (repositoryName && collaborator) {
        return `Added ${collaborator} as a collaborator to the repository ${repositoryName}`
    }
}

function getMessageForMemberRemovedEvent(repositoryName, collaborator) {
    if (repositoryName && collaborator) {
        return `Removed ${collaborator} as a collaborator from the repository ${repositoryName}`
    }
}

function getMessageForMemberEditedEvent(repositoryName, collaborator) {
    if (repositoryName && collaborator) {
        return `Updated permissions for ${collaborator} in the repository ${repositoryName}`
    }
}
