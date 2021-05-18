package com.cricut.test.data.local

import com.cricut.test.domain.PutResult
import com.cricut.test.domain.UserData
import java.util.regex.Pattern
import javax.inject.Inject

private const val USERNAME_MIN_SYMBOLS = 2
private const val PASS_MIN_SYMBOLS = 8

class UserDataValidator @Inject constructor() {
    fun validateUserData(data: UserData): PutResult =
        if (data.username.length < USERNAME_MIN_SYMBOLS
            || data.pass.length < PASS_MIN_SYMBOLS
              || !Pattern.matches("[a-zA-Z]+", data.username)
                || !android.util.Patterns.EMAIL_ADDRESS.matcher(data.email).matches()) PutResult.NOT_VALID else PutResult.PROGRESS
}