package com.cricut.test

import android.content.Context
import com.cricut.test.domain.UserData
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mock
import org.junit.Test
import org.junit.runner.RunWith

private const val USERDATA_USERNAME = "Username"
private const val USERDATA_PASSWORD = "Password"

@RunWith(MockitoJUnitRunner::class)
class LoginPageTest {

    @Mock
    private lateinit var context: Context

    @Test
    fun testDefaultLoginHash_Validation(){
        val resHash = context.resources.getInteger(R.string.login_hash)
        val testUserData = UserData(username = USERDATA_USERNAME, pass = USERDATA_PASSWORD)
        assert(testUserData.hashCode() == resHash)
    }
}