

# RoadMap

### v0.1.5
1. [Add] Validator 注解,参数校验器. 和 spring-boot-starter-validation 差不多

data
```java
public class ValidateDto {
    @Digits.List({@Digits(integer = 1, fraction = 0)})
    private long userId;
    private String name;
    private Integer age;
    @Email(regexp = ".*@qq.com")
    private String email;
}
```

validate
```java
        Validator.scanPackage(JakartaEmailValidator.class.getPackage().getName());
        ValidateDto build = ValidateDto.builder()
                .email("123@gmail.com")
                .build();
        ValidateContext validate = Validator.validate(build);
        Assert.assertFalse(validate.isOk());
```


### v0.1.4
1. [Add] IdGenerator | RDS, SnowFlake

```java

```