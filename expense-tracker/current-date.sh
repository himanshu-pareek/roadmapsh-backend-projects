if [[ $1 == "-y" || $1 == "--year" ]]; then
  date "+%Y"
elif [[ $1 == "-m" || $1 == "--month" ]]; then
  date "+%m"
elif [[ $1 == "-d" || $1 == "--day" ]]; then
  date "+%m"
elif [[ $1 == "-ym" || $1 == "--year-month" ]]; then
  date "+%Y-%m"
else
  date "+%Y-%m-%d"
fi

