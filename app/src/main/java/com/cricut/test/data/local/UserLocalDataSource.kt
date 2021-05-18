package com.cricut.test.data.local

import android.content.SharedPreferences
import android.content.res.Resources
import com.cricut.test.R
import com.cricut.test.domain.AuthResult
import com.cricut.test.domain.PutResult
import com.cricut.test.domain.UserData
import javax.inject.Inject
import kotlin.math.absoluteValue

private const val PREF_USER_HASH = "pref_user_hash"

class UserLocalDataSource @Inject constructor(
    private val resources: Resources,
    private val userSharedPref: SharedPreferences,
) {

    private val defaultUser by lazy { resources.getString(R.string.login_hash) }

    fun checkLoginHash(hash: Int) = if (getRegisteredUsersHashes().contains(hash.absoluteValue.toString())) AuthResult.SUCCESS else AuthResult.FAILED

    private fun getRegisteredUsersHashes() =
        userSharedPref.getStringSet(PREF_USER_HASH, mutableSetOf(defaultUser))!!

    fun registerNewUser(user: UserData): PutResult {
        val hashUser = user.apply { email = "" }
        val hash = "${hashUser.hashCode().absoluteValue}"
        if(getRegisteredUsersHashes().contains(hash)) return PutResult.EXIST
        else userSharedPref.edit()
            .putStringSet(PREF_USER_HASH,
                getRegisteredUsersHashes().apply {
                    add(hash)
                })
            .apply()
        return PutResult.SUCCESS
    }
}