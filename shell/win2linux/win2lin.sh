#!/bin/bash

if [[ -n $1  ]]; then
	MY_TARGET_FILES=$@

	echo "Changing mode to 644..."
	echo -n $MY_TARGET_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME chmod 644 FILENAME
else
	MY_SOURCE_FILES=$(find ./ -name '*.java' -o -name '*.cc' -o -name '*.cpp' -o -name '*.aidl' -o -name '*.h' -o -name '*.c' -o -name '*.xml' -o -name '*.txt' -o -name '*.html' -o -name '*.cfg')
	MY_EXE_FILES=$(find ./ -name '*.py' -o -name '*.bat')
	MY_MK_FILES=$(find ./ -name '*.mk')
	MY_BINARY_FILES=$(find ./ -name '*.png' -o -name '*.jar' -o -name '*.so' -o -name '*.jpg')

	echo "Changing mode to 644..."
	echo -n $MY_SOURCE_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME chmod 644 FILENAME
	echo -n $MY_MK_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME chmod 644 FILENAME
	echo -n $MY_BINARY_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME chmod 644 FILENAME

	MY_TARGET_FILES="$MY_SOURCE_FILES $MY_EXE_FILES"
fi

echo "Changing dos to unix file type..."
# dos2unix makes...
# file encoding type as utf-8
# remove BOM of utf-8
# remove CR from CRLF
echo -n $MY_TARGET_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME dos2unix -q FILENAME

echo "Removing BOM in case utf-8"
echo -n $MY_TARGET_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e 's/^\xEF\xBB\xBF//g' FILENAME


echo "Removing CR"
echo -n $MY_TARGET_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e 's/\x0D//g' FILENAME


echo "Changing tab into 4-spaces..."
echo -n $MY_TARGET_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e 's/\x09/\x20\x20\x20\x20/g' FILENAME


echo "Removing trailing spaces..."
echo -n $MY_TARGET_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e 's/\ +$//' FILENAME


echo "Removing blank lines at EOF..."
echo -n $MY_TARGET_FILES | xargs -P 4 -d ' ' -n 1 -I FILENAME sed -s -i -r -e ':a;/^\n*$/{$d;N;ba;}' FILENAME

