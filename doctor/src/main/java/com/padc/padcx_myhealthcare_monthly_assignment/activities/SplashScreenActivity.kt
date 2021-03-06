package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.activities.BaseActivity

class SplashScreenActivity :BaseActivity(){

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, SplashScreenActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed({
            if (!SessionManager.login_status) {
                startActivity(LoginActivity.newIntent(this))
            } else {
                startActivity(MainActivity.newIntent(this, "null"))
            }
            finish()
        }, 1800)
    }
}