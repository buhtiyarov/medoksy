CLASSPATH := .:`find ../lib -name '*.jar' | tr '\n' ':'`

.PHONY: check-syntax

check-syntax:
	javac -Xlint -d ../build/classes -cp $(CLASSPATH) $(CHK_SOURCES)
