package com.epicture.app.utils

import android.app.Application
import android.util.Log

class App : Application() {
    var accessTokenAuthorize : String? = null
    var tokenBearer: String? = null
    var refreshToken: String? = null
    var requestToken: String? = null
    var requestTokenId: String? = null
    var accountName: String? = null
    var expiredIn: String? = null
    var clientId: String? = null
    private lateinit var appConstants: AppConstants

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setData(accessTokenAuthorizeTmp : String, refreshTokenTmp: String, expiredInTmp: String, accountNameTmp: String) {
        appConstants = AppConstants()
        accessTokenAuthorize = accessTokenAuthorizeTmp
        refreshToken = refreshTokenTmp
        accountName = accountNameTmp
        expiredIn = expiredInTmp
        requestToken = "Authorization: Bearer $accessTokenAuthorize"
        clientId = appConstants.CLIENT_ID
        requestTokenId = "Authorization: Client-ID ${appConstants.CLIENT_ID}"
        tokenBearer = "Bearer $accessTokenAuthorizeTmp"
        Log.i("MyApp", accessTokenAuthorize as String)
        Log.i("MyApp", refreshToken as String)
        Log.i("MyApp", expiredIn as String)
        Log.i("MyApp", accountName as String)
        Log.i("MyApp", requestToken as String)
        Log.i("MyApp", requestTokenId as String)
        Log.i("MyApp", tokenBearer as String)
    }

    fun getBearerToken() : String?  {
        return this.tokenBearer
    }

    fun setBearerToken(value: String?) {
        tokenBearer = value
    }

    fun setIdClient(value: String) {
        clientId = value
    }

    fun getIdClient(): String? {
        return this.clientId
    }

    fun getIdTokenRequest(): String? {
        return this.requestTokenId
    }

    fun getTokenRequest(): String? {
        return this.requestToken
    }

    fun setTokenRequest(value: String?) {
        requestToken = "Authorization: Bearer ${getTokenAuthorize()}"
    }

    fun getInExpired(): String? {
        return this.expiredIn
    }

    fun setInExpired(value: String) {
        expiredIn = value
    }

    fun getNameAccount(): String? {
        return this.accountName
    }

    fun setNameAccount(value: String) {
        accountName = value
    }

    fun getTokenAuthorize(): String? {
        return this.accessTokenAuthorize
    }

    fun setTokenAuthorize(value: String) {
        accessTokenAuthorize = value
    }


    fun getTokenRefresh(): String? {
        return this.refreshToken
    }

    fun setTokenRefresh(value: String) {
        refreshToken = value
    }

    fun hasLoggedIn(): Boolean {
        return accessTokenAuthorize != null
    }

    fun loggedOut(): Any {
        accessTokenAuthorize = null
        refreshToken = null
        requestToken = null
        requestTokenId = null
        accountName = null
        expiredIn = null
        return (true)
    }

    companion object {
        var instance: App? = null
            private set
    }

}
//    fun hasTokenExpired(): Boolean { // ecc..
//    }
