# Logging framework :pencil2:
Logging framework provides the objects, methods, and configuration necessary to parse input message and send log strings to desired file.

## Setup logger

```java
Logger logger = new Logger();
# set level, messages with lower severity won't be stored in a file
logger.setLevel(ErrorLevel.DEBUG);
```

## Setup handler
Set up handler, responsible for writing logs to file. Handler can be utilized by adding optional parameters such as directory where log messages will be stored, 
maximum size of file and maximum number of files. If parameters are not provided, default values will be assigned.

```java
RotatingFileHandler handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(200).build();
```

## Setup formatter
If desired, user can determine format in which log messages will be stored in a file. 

```java
SimpleFormatter formatter = new SimpleFormatter("%{time} - %{levelname} - %{message}");
handler.addFormatter(formatter);
```

## Multithreading
