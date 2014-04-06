CLASSPATH := .:../lib/commons-lang3-3.3.1.jar:../lib/jdatepicker-1.3.2.jar:/usr/share/java/junit4.jar

.PHONY: check-syntax

check-syntax:
	javac -Xlint -d ../build/classes -cp $(CLASSPATH) $(CHK_SOURCES)
