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
    "$replacement_base"
    "$(echo "$replacement_base" | tr '_' ' ')"
    "$(echo "$replacement_base" | tr -d '_')"
    "$(echo "$replacement_base" | tr '[:lower:]' '[:upper:]' | sed 's/_/ /g')"
    "$(echo "$replacement_base" | tr '[:lower:]' '[:upper:]' | tr '_' ' ')"
    "$(echo "$replacement_base" | tr '[:lower:]' '[:upper:]' | tr -d '_')"
)

echo "${replacement_variants[@]}"

exit 0

# Step 1: Rename directories (deepest first to prevent path conflicts)
find . -depth -type d | while read path; do
    newpath="$path"
    for i in "${!original_variants[@]}"; do
        newpath=$(echo "$newpath" | sed "s/${original_variants[$i]}/${replacement_variants[$i]}/g")
    done
    [ "$path" != "$newpath" ] && mv "$path" "$newpath"
done

# Step 2: Rename files
find . -type f | while read path; do
    newpath="$path"
    for i in "${!original_variants[@]}"; do
        newpath=$(echo "$newpath" | sed "s/${original_variants[$i]}/${replacement_variants[$i]}/g")
    done
    [ "$path" != "$newpath" ] && mv "$path" "$newpath"
done

# Step 3: Replace inside files
grep -rl "${original_variants[0]}" . | while read file; do
    for i in "${!original_variants[@]}"; do
        sed -i "s/${original_variants[$i]}/${replacement_variants[$i]}/g" "$file"
    done
done

# Step 4: Rename the root directory
current_dir=$(basename "$PWD")
parent_dir=$(dirname "$PWD")
new_root_name=$(echo "$current_dir" | tr '[:lower:]' '[:upper:]' | tr -d '_')

cd "$parent_dir"
mv "$current_dir" "$new_root_name"
cd "$new_root_name"
