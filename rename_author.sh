#!/bin/bash

if [ "$#" -ne 1 ]; then
	echo "Usage: $0 <replacement>"
	exit 1
fi

replacement="$1"

if ! [[ "$replacement" =~ ^[a-z][_a-z]{9}$ ]]; then
	echo "Error: Replacement must match the pattern [a-z][_a-z]{9}"
	exit 1
fi

original='lakazatong'

find . -depth -name "*$original*" | while read -r path; do
	newpath=$(echo "$path" | sed "s/$original/$replacement/g")
	mv "$path" "$newpath"
done

grep -rl "$original" . | while read -r file; do
	sed -i "s/$original/$replacement/g" "$file"
done
