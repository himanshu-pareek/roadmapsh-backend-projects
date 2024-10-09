// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#pullrequestevent
module.exports.getMessageForPullRequestEvent = event => {
    const repositoryName = event?.repo?.name
    const pullRequest = event?.payload?.pull_request
    switch (event?.payload?.action) {
        case 'opened':
            return getMessageForPullRequestOpenedEvent(repositoryName, pullRequest)
        case 'edited':
            return getMessageForPullRequestEditedEvent(repositoryName, pullRequest, event?.payload?.changes)
        case 'closed':
            return getMessageForPullRequestClosedEvent(repositoryName, pullRequest)
        case 'reopened':
            return getMessageForPullRequestReopenedEvent(repositoryName, pullRequest)
        case 'assigned':
            return getMessageForPullRequestAssignedEvent(repositoryName, pullRequest)
    }
}

function getMessageForPullRequestOpenedEvent(repositoryName, pullRequest) {
    if (repositoryName && pullRequest) {
        return `Opened PR #${pullRequest.number} '${pullRequest.title}' in ${repositoryName}`
    }
}

function getMessageForPullRequestEditedEvent(repositoryName, pullRequest, changes) {
    if (repositoryName && pullRequest && changes) {
        const isTitleChanged = pullRequest.title !== changes.title.from
        const isBodyChanged = pullRequest.body !== changes.body.from
        let whatChanged = undefined
        if (isTitleChanged && isBodyChanged) {
            whatChanged = 'the title and body'
        } else if (isTitleChanged) {
            whatChanged = 'the title'
        } else if (isBodyChanged) {
            whatChanged = 'the body'
        }
        if (whatChanged) {
            return `Updated ${whatChanged} of PR #${pullRequest.number} '${pullRequest.title}' in ${repositoryName}`
        }
    }
}

function getMessageForPullRequestClosedEvent(repositoryName, pullRequest) {
    if (repositoryName && pullRequest) {
        return `Closed PR #${pullRequest.number} '${pullRequest.title}' in ${repositoryName}`
    }
}

function getMessageForPullRequestReopenedEvent(repositoryName, pullRequest) {
    if (repositoryName && pullRequest) {
        return `Reopened PR #${pullRequest.number} '${pullRequest.title}' in ${repositoryName}`
    }
}

function getMessageForPullRequestAssignedEvent(repositoryName, pullRequest) {
    if (repositoryName && pullRequest?.assignees) {
        const assigneesToDisplay = pullRequest.assignees.length == 1 ?
            pullRequest.assignees[0].login :
            `${pullRequest.assignees.length} persons`;
        return `Assigned ${assigneesToDisplay} to PR #${pullRequest.number} '${pullRequest.title}'`
    }
}
