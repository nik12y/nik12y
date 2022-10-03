# Check if log file exists
OLD_FILE=$2
NEW_FILE=$3

logFunc() {
    echo "$OLD_FILE $NEW_FILE"
    if [ -e $OLD_FILE ];
    then
        echo “File exists $1 $2”
    else
        echo “File does not exist”
        echo "$CI_COMMIT_BRANCH"
        mv $NEW_FILE $OLD_FILE;
        touch $NEW_FILE;
    fi;
}


mergeFunc() {
    NumCommitsAhead=$(git log iut..dev --oneline | wc -l)
    if [ $NumCommitsAhead -eq 0 ]; then
        echo "sourceBranch has no new commits to merge. Exiting."
        exit 1
    fi
}

"$@"