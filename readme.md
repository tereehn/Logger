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
For demonstration purposes of multithreaded logging. Let's create multiple LoggingThread objects, which will produce random messages and try writing them to file.
Methods are synchronized to avoid race conditions. Write to file is provided by one thread.

```java
LoggingThread x1 = new LoggingThread("1", logger, 4);
LoggingThread x2 = new LoggingThread("2", logger, 3);
WritingThread y = new WritingThread("3", logger);

x1.start();
x2.start();
y.start();
x1.join();
x2.join();
y.join();
```
