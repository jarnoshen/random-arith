#!/bin/bash
java -jar randomWord-0.0.1-SNAPSHOT-jar-with-dependencies.jar -F ../words.txt -D $1 -C 47 -L 3
soffice $1.odt
