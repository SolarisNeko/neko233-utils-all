package com.neko233.validation;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateDto {

    @Digits.List({@Digits(integer = 1, fraction = 0)})
    private long userId;
    private String name;
    private Integer age;
    @Email(regexp = ".*@qq.com")
    private String email;

}
