

# RoadMap

## version

### v0.1.7
deploy v0.1.7
1. [Update] [idGenerator] add 'cache' API
2. [Add] [Validate] add my validate demo & test unit.
3. [Add] [Event] add dispatcher helper.


### 0.1.6
deploy 0.1.6
1. [Add] ReactiveData 响应式, 基础
2. [Update] [事件机制] 破坏性改动. destroy history API. DispatcherCenter.java 删除 --> EventDispatcher.java
3. [Add] [Test] 事件委托 & 响应式的单元测试.

### 0.1.5
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