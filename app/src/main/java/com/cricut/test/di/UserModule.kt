package com.cricut.test.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.cricut.test.TestApplication
import com.cricut.test.data.local.UserDataValidator
import com.cricut.test.data.local.UserLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val PREF_USERS = "pref_users"

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources =  context.resources
    @Provides
    fun provideUserSharedPref(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(PREF_USERS, Context.MODE_PRIVATE)

    @Provides
    fun providesUserLocalDataSource(sp: SharedPreferences, res: Resources) = UserLocalDataSource(res, sp)
    @Provides
    fun providesUserValidator() = UserDataValidator()
}