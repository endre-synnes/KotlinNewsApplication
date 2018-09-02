package com.endre.kotlin.news.util

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
class CountryValidator : ConstraintValidator<Country, String> {

    override fun initialize(constraintAnnotation: Country) {
    }

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        return CountryList.isValidCountry(value)
    }
}