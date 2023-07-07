# bytebuddy-sandbox

A pet project to play with ByteBudy's capabilities.

### Compile and run:

```
## Build the agent's jar and an uber-Jar with a sample app
sbt assembly

## Execute the sample app with the agent weaving the code
java -javaagent:agent/target/scala-2.13/bb-agent-35-assembly-0.1.0-SNAPSHOT.jar \
     -jar sample/target/scala-2.13/bb-sample-35-assembly-0.1.0-SNAPSHOT.jar"
```
