build:
	javac $(shell find src -name "*.java")

run: build
	java src.Main

clean:
	find src -name "*.class" -delete
