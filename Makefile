default:
	javac -d . src/*.java

jar:
	jar cvfe PhysicsLab.jar PhysicsLab *.class *.wav

run:
	java -jar PhysicsLab.jar

runApplet:
	appletviewer PhysicsLab.html

clean:
	rm -f src/*.class
	rm -f *.jar
	rm -f *.class