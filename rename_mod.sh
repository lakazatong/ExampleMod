#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <new_mod_name (a_b_c format)>"
    exit 1
fi

original_base="example_mod"
replacement_base="$1"

# Generate different case variations
original_variants=(
    "example_mod"
    "example mod"
    "examplemod"
    "Example_Mod"
    "Example Mod"
    "ExampleMod"
)

replacement_variants=(
    "$replacement_base"  # compact_circuits_mod
    "$(echo "$replacement_base" | tr '_' ' ')"  # compact circuits mod
    "$(echo "$replacement_base" | tr -d '_')"  # compactcircuitsmod
    "$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS='_')"  # Compact_Circuits_Mod
    "$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS=' ')"  # Compact Circuits Mod
    "$(echo "$replacement_base" | awk -F'_' '{for(i=1;i<=NF;i++) $i=toupper(substr($i,1,1)) tolower(substr($i,2))}1' OFS='')"  # CompactCircuitsMod
)

echo "${replacement_variants[@]}"

exit 0

find . -depth -type d | while read path; do
    newpath="$path"
    for i in "${!original_variants[@]}"; do
        newpath=$(echo "$newpath" | sed "s/${original_variants[$i]}/${replacement_variants[$i]}/g")
    done
    [ "$path" != "$newpath" ] && mv -r "$path" "$newpath"
done

find . -depth -type f | while read path; do
    newpath="$path"
    for i in "${!original_variants[@]}"; do
        newpath=$(echo "$newpath" | sed "s/${original_variants[$i]}/${replacement_variants[$i]}/g")
    done
    [ "$path" != "$newpath" ] && mv "$path" "$newpath"
done

grep -rl "${original_variants[0]}" . | while read file; do
    for i in "${!original_variants[@]}"; do
        sed -i "s/${original_variants[$i]}/${replacement_variants[$i]}/g" "$file"
    done
done

current_dir=$(basename "$PWD")
parent_dir=$(dirname "$PWD")
new_root_name=$(echo "$current_dir" | tr '[:lower:]' '[:upper:]' | tr -d '_')

cd "$parent_dir"
mv "$current_dir" "$new_root_name"
cd "$new_root_name"
