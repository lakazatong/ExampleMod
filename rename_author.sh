#!/bin/bash

if [ "$#" -ne 1 ]; then
	echo "Usage: $0 <replacement>"
	exit 1
fi

original='lakazatong'
replacement="$1"

find . -depth -name "*$original*" | while read path; do
	newpath=$(echo "$path" | sed "s/$original/$replacement/g")
	mv "$path" "$newpath"
done

grep -rl "$original" . | while read file; do
	sed -i "s/$original/$replacement/g" "$file"
done
