package com.epicture.app.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.epicture.app.utils.App
import com.epicture.app.R
import com.epicture.app.utils.AppConstants


class Login : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var appConstants: AppConstants

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        /* POLICY*/
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        appConstants = AppConstants()
        if (savedInstanceState != null)
        {
            webView = findViewById(R.id.webview_login)
            webView.restoreState(savedInstanceState)
        } else {
            webView = findViewById(R.id.webview_login)
        }

        /* WebView Config */
        webView = findViewById(R.id.webview_login)
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.isVerticalScrollBarEnabled = true
        webView.requestFocus()

        webView.loadUrl("https://api.imgur.com/oauth2/authorize"
                + "?client_id=" + appConstants.CLIENT_ID
                + "&response_type=token")

        /* Load the Url to the webView Client : keep page navigation within the WebView and hence within the app*/
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean { // TODO Auto-generated method stub
                return if (url != null && url.startsWith(appConstants.URL_REDIRECT)) {
                    val intent = Intent(applicationContext, Navigation::class.java)
                    val paramList: List<String> = url.toString().split("&")
                    val accessToken = paramList[0].substring(paramList[0].indexOf("=") + 1)
                    val expiredIn = paramList[1].substring(paramList[1].indexOf("=") + 1)
                    val refreshToken = paramList[3].substring(paramList[3].indexOf("=") + 1)
                    val accountName = paramList[4].substring(paramList[4].indexOf("=") + 1)
                    /*
                    Log.i("MyApp", url)
                    Log.i("MyApp", accessToken)
                    Log.i("MyApp", refreshToken)
                    Log.i("MyApp", expiredIn)
                    Log.i("MyApp", accountName)
                    */
                    App.instance?.setData(accessToken, refreshToken, expiredIn, accountName)
                    startActivity(intent)
                    finish()
                    (true)
                } else {
                      view?.loadUrl(url)
                      (false)
                }
            }
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler,
                error: SslError?
            ) {
                handler.proceed() // Ignore SSL certificate errors
            }
        }
        /* Enable log debugging for the View  */
        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d("My App", "I'm in Login")
                return super.onConsoleMessage(consoleMessage)
            }
        }
    }
    /* Set up the button to go back */
    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() // if there is previous page open it
        else super.onBackPressed() //if there is no previous page, close app
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}

//        /* Event Click listener */
//        val b = findViewById<Button>(R.id.login_buton)
//        val clickListener = View.OnClickListener {
//            val intent = Intent(applicationContext, Navigation::class.java)
//            startActivity(intent)
//        }
//        b.setOnClickListener(clickListener)
