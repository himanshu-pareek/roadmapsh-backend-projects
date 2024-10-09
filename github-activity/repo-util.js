module.exports.getRepositoryNameToDisplay = event => {
    let repoNameToDisplay = event?.repo?.name
    const actorName = event?.actor?.login
    if (repoNameToDisplay && actorName && repoNameToDisplay.startsWith(actorName + '/')) {
        repoNameToDisplay = repoNameToDisplay.substring(actorName.length + 1)
    }
    return repoNameToDisplay
}
