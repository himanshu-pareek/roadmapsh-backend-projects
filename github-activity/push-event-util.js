const { getRepositoryNameToDisplay } = require("./repo-util")

// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#pushevent
module.exports.getMessageForPushEvent = event => {
    const numberOfCommits = event?.payload?.size
    const repositoryName = getRepositoryNameToDisplay(event)
    const branchOrTagName = event?.payload?.ref
    if (numberOfCommits && repositoryName && branchOrTagName) {
        const commitOrCommits = numberOfCommits > 1 ? 'commits' : 'commit'
        const branchComponents = 'ref/head/main'.split('/')
        const branchNameToShow = branchComponents[branchComponents.length - 1]
        return `Pused ${numberOfCommits} ${commitOrCommits} to ${branchNameToShow} in ${repositoryName}`
    }
}
