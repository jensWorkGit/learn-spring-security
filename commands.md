# git

# remove git stuff
git ls-files -i -X .gitignore | xargs -I{} git rm --cached "{}"
git rm --cached `git ls-files -i -X .gitignore
