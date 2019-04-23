#!/bin/bash
#
# login in the jenkins server with:
#
# heroku login
# heroku keys:add
#
# Doing so, jenkins will have permission to deploy to
# the heroku remote.
#

# exit 1 on errors
set -e

# deal with remote
echo "Checking if remote exists..."
if ! git ls-remote heroku; then
  echo "Adding heroku remote..."
  git remote add heroku https://git.heroku.com/cs4500-sp19-nowayjose.git
fi

# push only origin/master to heroku/master - will do nothing if
# master doesn't change.
echo "Updating heroku master branch..."
git push heroku origin/master:master