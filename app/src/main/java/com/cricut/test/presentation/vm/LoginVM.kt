package com.cricut.test.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricut.test.data.local.UserLocalDataSource
import com.cricut.test.domain.AuthResult
import com.cricut.test.domain.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val loginValidator: UserLocalDataSource) : ViewModel() {

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult>
        get() = _authResult

    fun onLoginClicked(username: String, password: String) {
        val user = UserData(username = username, pass = password)
        val authResult = loginValidator.checkLoginHash(user.hashCode())
        _authResult.postValue(authResult)
        when(authResult){
            AuthResult.PROGRESS-> Unit
            AuthResult.SUCCESS-> Unit
            else -> _authResult.postValue(AuthResult.PROGRESS)
        }
    }
}