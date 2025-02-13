#!/bin/bash

current_dir=$(basename "$PWD")

# ExampleMod -> example_mod
replacement_base=$(echo "$current_dir" | sed -E 's/([a-z])([A-Z])/\1_\2/g' | tr '[:upper:]' '[:lower:]')

original_variants=(
    "example_mod"
    "example mod"
    "examplemod"
    "Example_Mod"
    "Example Mod"
    "ExampleMod"
)

replacement_variants=(
    "$replacement_base"
    "$(echo "$replacement_base" | tr '_' ' ')"
    "$(echo "$replacement_base" | tr -d '_')"
    "$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS='_')"
    "$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS=' ')"
    "$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS='')"
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