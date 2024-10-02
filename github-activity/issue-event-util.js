/**
 * Issue Event - https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#issuesevent
 */
module.exports.getMessageForIssuesEvent = (event) => {
    const repoName = event?.repo?.name
    const issueNumber = event?.payload?.issue?.number
    const issueTitle = event?.payload?.issue?.title || 'NO TITLE'
    switch (event?.payload?.action) {
        case 'opened':
            return getMessageForIssueOpenedEvent(repoName, issueNumber, issueTitle)
        case 'closed':
            return getMessageForIssueClosedEvent(repoName, issueNumber, issueTitle)
        case 'edited':
            return getMessageForIssueEditedEvent(repoName, issueNumber, issueTitle)
        case 'reopened':
            return getMessageForIssueReopenedEvent(repoName, issueNumber, issueTitle)
        case 'assigned':
            return getMessageForIssueAssignedEvent(repoName, issueNumber, issueTitle)
        case 'unassigned':
            return getMessageForIssueUnassignedEvent(repoName, issueNumber, issueTitle)
        case 'labeled':
            return getMessageForIssueLabeledEvent(repoName, issueNumber, issueTitle, event?.payload?.label?.name)
        case 'unlabeled':
            return getMessageForIssueUnlabeledEvent(repoName, issueNumber, issueTitle, event?.payload?.label?.name)
    }
}

function getMessageForIssueOpenedEvent(repoName, issueNumber, issueTitle) {
    if (repoName && issueNumber && issueTitle) {
        return `Opened issue #${issueNumber} '${issueTitle}' in ${repoName}`
    }
}

function getMessageForIssueClosedEvent(repoName, issueNumber, issueTitle) {
    if (repoName && issueNumber && issueTitle) {
        return `Closed issue #${issueNumber} '${issueTitle}' in ${repoName}`
    }
}

function getMessageForIssueEditedEvent(repoName, issueNumber, issueTitle) {
    if (repoName && issueNumber && issueTitle) {
        return `Edited issue #${issueNumber} '${issueTitle}' in ${repoName}`
    }
}

function getMessageForIssueReopenedEvent(repoName, issueNumber, issueTitle) {
    if (repoName && issueNumber && issueTitle) {
        return `Reopened issue #${issueNumber} '${issueTitle}' in ${repoName}`
    }
}

function getMessageForIssueAssignedEvent(repoName, issueNumber, issueTitle) {
    if (repoName && issueNumber && issueTitle) {
        return `Assigned to issue #${issueNumber} '${issueTitle}' in ${repoName}`
    }
}

function getMessageForIssueUnassignedEvent(repoName, issueNumber, issueTitle) {
    if (repoName && issueNumber && issueTitle) {
        return `Unassigned from issue #${issueNumber} '${issueTitle}' in ${repoName}`
    }
}

function getMessageForIssueLabeledEvent(repoName, issueNumber, issueTitle, newLabel) {
    if (repoName && issueNumber && issueTitle && newLabel) {
        return `Added label '${newLabel}' to issue #${issueNumber} '${issueTitle}' in ${repoName}`
    }
}

function getMessageForIssueUnlabeledEvent(repoName, issueNumber, issueTitle, oldLabel) {
    if (repoName && issueNumber && issueTitle && oldLabel) {
        return `Removed label '${oldLabel}' from issue #${issueNumber} '${issueTitle}' in ${repoName}`
    }
}
