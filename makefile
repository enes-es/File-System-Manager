JC = javac
JFLAGS = -classpath .
JD = javadoc
JDFLAGS = -protected -splitindex -use -author -version -d ./javadoc
RM = rm
JR = java

CLASSES = \
		Main.java \
		Inventory.java \
		Device.java \
		DeviceObj.java \
		SmartPhone.java \
		Laptop.java \
		Smartwatch.java \
		TV.java \
		Headphones.java \


all : Main.class

run : 
	$(JR) Main

classes : $(CLASSES:.java=.class)

%.class : %.java
	$(JC) $(JFLAGS) $<

doc:
	$(JD) $(JDFLAGS) *.java 

clean:
	$(RM) *.class 

cleanreport:
	rm report.txt
	find . -type f -regex '.*report(\([1-9][0-9]*\))?\.txt' -exec rm {} +
cleandoc:
	$(RM) -r ./javadoc