default:
	jar xf jcommon-1.0.21.jar
	jar xf jfreechart-1.0.17.jar
	javac *.java

jar:
	jar cvfe PhysicsLab.jar PhysicsLab *.class *.wav org/** com/**

run:
	java -jar PhysicsLab.jar

runApplet:
	appletviewer PhysicsLab.html

clean:
	rm -f *.class
	rm -f PhysicsLab.jar