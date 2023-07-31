# Logging framework :pencil2:
The Logging Framework offers a comprehensive set of objects, methods, and configurations to effortlessly parse input messages and seamlessly send log strings to a specified file.

## Setup logger

```java
Logger logger = new Logger();
# set level, messages with lower severity won't be stored in a file
logger.setLevel(ErrorLevel.DEBUG);
```

## Setup handler
Set up a handler that is responsible for writing logs to a file. The handler can be customized by adding optional parameters such as the directory where log messages will be stored, the maximum file size, and the maximum number of files. If no parameters are provided, default values will be assigned. When the maximum file size is reached, the handler will automatically rotate the file.

```java
RotatingFileHandler handler = new RotatingFileHandler.FileHandlerBuilder("test.log").fileRoot("testdir/").maxFileSize(200).build();
```
Eventually we must clear the handler.

```java
handler.close();
```

## Setup formatter
If desired, user can determine format in which log messages will be stored in a file. 

```java
SimpleFormatter formatter = new SimpleFormatter("%{time} - %{levelname} - %{message}");
handler.addFormatter(formatter);
```

## Multithreading
For the purpose of demonstrating multithreaded logging, let's create multiple LoggingThread objects. These objects will generate random messages and attempt to write them to a file. The methods are synchronized to prevent race conditions. To ensure consistency when multiple threads write to the same file, a single thread (WritingThread) is responsible for writing to the file.

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

## Tests
Tests can be found in test folder.
