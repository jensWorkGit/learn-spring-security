# git

# remove git stuff
git ls-files -i -X .gitignore | xargs -I{} git rm --cached "{}"
git rm --cached `git ls-files -i -X .gitignore


INSERT INTO `user` (`created`, `email`, `enabled`, `password`) VALUES ('2016-07-01 09:19:52', 'jho@orgcomnet.de', b'1', 'QcIYn%Wbs16~AQ2wSeW8')
