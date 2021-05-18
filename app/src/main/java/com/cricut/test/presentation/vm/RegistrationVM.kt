package com.cricut.test.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricut.test.data.local.UserDataValidator
import com.cricut.test.data.local.UserLocalDataSource
import com.cricut.test.domain.PutResult
import com.cricut.test.domain.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationVM @Inject constructor (val userLocalDataSource: UserLocalDataSource, private val userDataValidator: UserDataValidator): ViewModel() {

    private val _createUserState = MutableLiveData<PutResult>()
    val createUserState get() = _createUserState

    fun onCreateNewProfile(userData: UserData) {
        when(userDataValidator.validateUserData(userData)){
            PutResult.PROGRESS -> createProfile(userData)
            PutResult.NOT_VALID -> onUserDataNotValid()
        }
    }
    private fun createProfile(userData: UserData){
        when(userLocalDataSource.registerNewUser(userData)){
            PutResult.EXIST -> onUserAlreadyExits()
            PutResult.PROGRESS -> Unit
            PutResult.SUCCESS -> onPutProfileSuccess()
            PutResult.NOT_VALID -> onUserDataNotValid()
        }
    }

    private fun onPutProfileSuccess() = _createUserState.postValue(PutResult.SUCCESS)

    private fun onUserAlreadyExits() = _createUserState.postValue(PutResult.EXIST)

    private fun onUserDataNotValid() = _createUserState.postValue(PutResult.NOT_VALID)

}