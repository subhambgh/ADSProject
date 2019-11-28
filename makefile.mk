JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Building.java \
        MinHeap.java \
        RedBlackTree.java \
        DEPQ.java \
        risingCity.java \
        ReadFile.java \
        WriteFile.java

default: classes

classes: $(CLASSES:.java=.class)

%.class : %.java
	$(JC) $(JFLAGS) *.java

clean:
	$(RM) *.class