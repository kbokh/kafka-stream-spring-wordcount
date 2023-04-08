# An example of a kafka streaming application based on the spring cloud framework and Kotlin

This example of the kafka streaming application has been created for the course "Data Streaming Processing" that is read at Ukrainian Catholic University. It implements a very simple topology of word count in Kotlin
The example is based on the spring cloud framework (the kafka stream binding) and Redpanda.

## Building 

```bash
docker build -t wordcount .
```

## Running 
```bash
docker compose up 
```

## Sending messages
```bash
docker exec -it springwordcount-redpanda-1 rpk topic produce wordcount-topic --brokers=localhost:29092
```
