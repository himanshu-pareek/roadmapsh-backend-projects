DIRECTORY=".data/metadata"
FILE="${DIRECTORY}/id.seq"

mkdir -p "${DIRECTORY}"

# Initialize the file if not exist
if [[ ! -f "${FILE}" ]]; then
  echo "1" > "${FILE}"
fi

# Read the file to get the id
ID="$(cat "${FILE}")"

# Update the file with next id
let NEXT_ID=ID+1
echo "${NEXT_ID}" > "${FILE}"

# Return id to caller
echo $ID

