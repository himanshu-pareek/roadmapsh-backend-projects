curl -X POST \
  -u bob:password \
  -F title="$1" \
  -F file=@"$2" \
  http://localhost:8080/images \
  --verbose

