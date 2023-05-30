package com.example.backlogapi.core.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@PropertySource("classpath:application.yml")
public class EmailValidator {

    private final Pattern USER_INPUT_PATTERN;

    @Autowired
    public EmailValidator(@Value("${string.regex}") String regex) {
        USER_INPUT_PATTERN = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public boolean checkEmailPattern(String email) {
        return USER_INPUT_PATTERN.matcher(email).matches();
    }
}
