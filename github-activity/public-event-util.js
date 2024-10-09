const { getRepositoryNameToDisplay } = require("./repo-util")

// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#publicevent
module.exports.getMessageForPublicEvent = event => {
    const repositoryName = getRepositoryNameToDisplay(event)
    if (repositoryName) {
        return `Made ${repositoryName} public 🎉`
    }
}
