package com.neko233.validation.impl.jakarta;

import com.neko233.validation.ValidateApi;
import jakarta.validation.constraints.Email;
import com.neko233.common.base.StringUtils233;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email Validator
 */
public class JakartaEmailValidator implements ValidateApi<Email, String> {

    @Override
    public Class<? extends Email> getAnnotationType() {
        return Email.class;
    }

    @Override
    public boolean handle(Email value, String fieldValue) {
        // 大小写敏感
        Pattern pattern = Pattern.compile(value.regexp(), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fieldValue);
        return matcher.matches();
    }

    @Override
    public String getReason(Email email, String fieldValue) {
        if (StringUtils233.isNotBlank(email.message())) {
            return email.message();
        }
        return String.format("%s is not target email format. your email format = %s", fieldValue, email.regexp());
    }

}
