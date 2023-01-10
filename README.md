# 🍒 Cherry

Cherry is an ORM (Object-relational mapping) tool that tries to achieve a simple and logic way of interacting with databases. While at
the same time avoiding bloated dependencies and the requirement to install IDE plugins for code generation.

## Download
If you want to utilize Cherry, you will either have to build it locally or depend on it.

### Building from source
Java 17 and Git are required to clone and build Cherry. Cloning and building can be done with the following commands. 

```
git clone git@github.com:alexwith/cherry.git
cd cherry
./gradlew build
```


### From repository

**Gradle**<br>

```groovy
dependencies {
    implementation("")
}
```

**Maven**<br>

```xml
```

## Usage
Underneath you can see some simple examples of how to utilize Cherry using the mongo client.

### Example Entity
```java
public class TestEntity implements Entity<TestEntity> {

    @EntityId
    private final UUID id;

    private String name;
    private List<String> todo;

    protected TestEntity(UUID id, String name, List<String> todo) {
        this.id = id;
        this.name = name;
        this.todo = todo;
    }

    @Override
    public UnaryOperator<EntitySettings<TestEntity>> settings() {
        return (settings) -> settings
            .database("collection")
            .defaults(new TestEntity(
                null,
                "Bob",
                List.of(
                    "Do the dishes",
                    "Make dinner"
                )
            ));
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTodo() {
        return this.todo;
    }

    public void setTodo(List<String> todo) {
        this.todo = todo;
    }
}

```

### Example Interactions
```java
Cherry.connect(new CherryMongoClient("mongodb://localhost:27017", "database"));
```

## License
Cherry is free and open source software. The software is released under the terms of
the [GPL-3.0 license]("https://github.com/alexwith/cherry/blob/main/LICENSE").