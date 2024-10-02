// https://docs.github.com/en/rest/using-the-rest-api/github-event-types?apiVersion=2022-11-28#publicevent
module.exports.getMessageForPublicEvent = event => {
    if (event?.repo?.name) {
        return `Made ${event?.repo?.name} public 🎉`
    }
}
