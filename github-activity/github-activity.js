function handleUsername(username = '') {
    console.log('Get github activity for username ' + username)
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
