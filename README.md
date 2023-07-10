# bytebuddy-sandbox

A pet project to play with ByteBudy's capabilities.

### Compile and run:

```
## Build the agent's jar and an uber-Jar with a sample app
sbt assembly

## Execute the sample app with the agent weaving the code
java -javaagent:agent/target/scala-2.12/bb-agent-35-assembly-0.1.0-SNAPSHOT.jar \
     -jar sample/target/scala-2.12/bb-sample-35-assembly-0.1.0-SNAPSHOT.jar"
```

### Publish

TODO: make the agent scala-agnostic. It doesn't use scala so it's should have to be cross-published.
```
sbt +publishLocal
```
