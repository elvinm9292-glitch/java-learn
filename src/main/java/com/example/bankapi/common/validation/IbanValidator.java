package com.example.bankapi.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IbanValidator implements ConstraintValidator<ValidIban, String> {

    private static final Pattern AZ_IBAN = Pattern.compile("^AZ\\d{2}[A-Z]{4}\\d{20}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;      // за null отвечает @NotBlank
        }
        return AZ_IBAN.matcher(value).matches();
    }
}