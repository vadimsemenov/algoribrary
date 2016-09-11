#!/bin/sh

cp ~/contests/algoribrary/output/Main.java .
javac Main.java
java -Xmx1G -Xss512m "-javaagent:/Users/vadim/Library/Application Support/IntelliJIdea2016.1/chelper/lib/cojac.jar=-Cints -Clongs -Ccasts -Cmath" Main
rm Main*.class

echo done!
