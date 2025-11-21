for i in {1..20}; do
  echo "Request $i"
  curl -i http://localhost:8080/images -u bob:password
  sleep 0.1
done
