#!/bin/bash

MY_SOURCE_FILES=$(find ./ -name '*.java' -o -name '*.cpp' -o -name '*.aidl' -o -name '*.h' -o -name '*.c' -o -name '*.xml' -o -name '*.txt' -o -name '*.html' -o -name '*.jj' -o -name '*.cfg')
MY_EXE_FILES=$(find ./ -name '*.py' -o -name '*.bat')
MY_MK_FILES=$(find ./ -name '*.mk')
MY_BINARY_FILES=$(find ./ -name '*.png' -o -name '*.jar' -o -name '*.so' -o -name '*.jpg')

echo "Changing mode to 644..."
echo -n $MY_SOURCE_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME chmod 644 FILENAME
echo -n $MY_MK_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME chmod 644 FILENAME
echo -n $MY_BINARY_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME chmod 644 FILENAME


MY_SOURCE_FILES="$MY_SOURCE_FILES $MY_EXE_FILES"
echo "Changing dos to unix file type..."
# dos2unix makes...
# file encoding type as utf-8
# remove BOM of utf-8
# remove CR from CRLF
echo -n $MY_SOURCE_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME dos2unix -q FILENAME
echo -n $MY_MK_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME dos2unix -q FILENAME

echo "Removing BOM in case utf-8"
echo -n $MY_SOURCE_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e 's/^\xEF\xBB\xBF//g' FILENAME
echo -n $MY_MK_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -i -s -r -e 's/^\xEF\xBB\xBF//g' FILENAME


echo "Removing CR"
echo -n $MY_SOURCE_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e 's/\x0D//g' FILENAME
echo -n $MY_MK_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -i -s -r -e 's/\x0D//g' FILENAME


echo "Changing tab into 4-spaces..."
echo -n $MY_SOURCE_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e 's/\x09/\x20\x20\x20\x20/g' FILENAME


echo "Removing trailing spaces..."
echo -n $MY_SOURCE_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e 's/\ +$//' FILENAME
echo -n $MY_MK_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -i -s -r -e 's/\ +$//' FILENAME


echo "Removing blank lines at EOF..."
echo -n $MY_SOURCE_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e ':a;/^\n*$/{$d;N;ba;}' FILENAME
echo -n $MY_MK_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -i -s -r -e ':a;/^\n*$/{$d;N;ba;}' FILENAME


######
## If you don't use git then Below lines are not required 
######

echo "Modified files are:"
git ls-files -m

echo ""

echo "Review the change by using 'git diff'"
echo "Check additional problems by 'git show --check'"
echo "In order to discard the change, use 'git reset --hard HEAD'"

if [ "$1" = "-o" ];then
    COMMIT_SHA1=$(git show --pretty=oneline --check | head -1 | awk -F ' ' '{print $1}')
    FILE_LIST=$(git show --pretty=oneline --check | grep -v $COMMIT_SHA1 | grep -v '+' | awk -F ':' '{print $1}' | uniq)
    echo "Just add files only in your commit..."
    for name in $(echo $FILE_LIST);do echo "- add file $name ...";git add $name;done;
else
    echo "If satisfied with the change, upload the commit by"
    echo "$ git add -u"
fi
echo "$ git commit --amend"
echo "$ repo upload ./"
