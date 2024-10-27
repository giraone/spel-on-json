[![GitHub license](https://img.shields.io/github/license/giraone/spel-on-json)](https://github.com/giraone/spel-on-json/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/badge/Maven%20Central-1.2.2-blue)](https://mvnrepository.com/artifact/com.giraone.rules/spel-on-json)
[![GitHub issues](https://img.shields.io/github/issues/giraone/spel-on-json)](https://github.com/giraone/spel-on-json/issues)
[![GitHub stars](https://img.shields.io/github/stars/giraone/spel-on-json)](https://github.com/giraone/spel-on-json/stargazers)
[![Platform](https://img.shields.io/badge/platform-jre17%2B-blue)](https://github.com/giraone/spel-on-json/pom.xml)

# Spel on JSON

Library for template replacements based on

- [SpEL](https://docs.spring.io/spring-framework/reference/core/expressions.html) as the expression language
- *JSON* or `Map<String,Object>` as the source for variable data input

It is also planned use *JSON pointer* notation to access the source.

## Hints

- [SpEL Tutorial on *tutorialspoint*](https://www.tutorialspoint.com/spring_expression_language/index.htm)

## Basic Use Case

### Using x.y notation working on `Map<String,Object>`

Assuming, this is the *template*:

```
Hello ${'Jimmy'.substring(0,3)},

you are invited to my birthday in ${data.location.toUpperCase()}.
I am now ${data.sender.age}.

Yours sincerly,
${data.sender.name}
```

When using this *JSON data*: 

```
{
 "sender": {
  "name": "John",
  "age": 55
 },
 "location": "New York"
}
```

This is the **result**:

```
Hello Jim,

you are invited to my birthday in NEW YORK.
I am now 55.

Yours sincerly,
John
```

## Coding 

### Using x.y notation working on `Map<String,Object>`

```java
import com.giraone.templating.SpelConverter;

public class Test {
  
  void run() {
    String result = SpelConverter.convert("Hey ${name}", Map.of("name", "John"));
    // result is "Hey John"
  }
}
```

---

## Build

- Use JDK 17+
- `mvn package`

## Release Notes

- 0.0.1 (2024-10-17)
  - Initial version
