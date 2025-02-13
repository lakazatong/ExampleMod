#!/bin/bash

if [ "$#" -ne 1 ]; then
	echo "Usage: $0 <new_mod_name (a_b_c format)>"
	exit 1
fi

original_base="example_mod"
replacement_base="$1"

original_variants=(
	"example_mod"
	"example mod"
	"examplemod"
	"Example_Mod"
	"Example Mod"
	"ExampleMod"
)

replacement_variants=(
	"$replacement_base"  # example_mod
	"$(echo "$replacement_base" | tr '_' ' ')"  # example mod
	"$(echo "$replacement_base" | tr -d '_')"  # examplemod
	"$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS='_')"  # Example_Mod
	"$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS=' ')"  # Example Mod
	"$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS='')"  # ExampleMod
)

echo "Make sure Gradle is not running in this project (close any IDE that has this project open)"
echo "Press Enter to continue..."
read -r

rm -rf .gradle .vscode .idea

for i in "${!original_variants[@]}"; do
	find . -depth -type d -name "*${original_variants[$i]}*" ! -path "./.git*" | while read path; do
		newpath=$(echo "$path" | sed "s/${original_variants[$i]}/${replacement_variants[$i]}/g")
		[ "$path" != "$newpath" ] && mv "$path" "$newpath"
	done

	find . -depth -type f -name "*${original_variants[$i]}*" ! -path "./.git*" | while read path; do
		newpath=$(echo "$path" | sed "s/${original_variants[$i]}/${replacement_variants[$i]}/g")
		[ "$path" != "$newpath" ] && mv "$path" "$newpath"
	done

	grep -rl "${original_variants[$i]}" . --exclude-dir=".git" | while read file; do
		sed -i "s/${original_variants[$i]}/${replacement_variants[$i]}/g" "$file"
	done
done

[ "$(basename "$PWD")" != "${replacement_variants[5]}" ] && echo "Do not forget to rename the root folder to ${replacement_variants[5]}"
