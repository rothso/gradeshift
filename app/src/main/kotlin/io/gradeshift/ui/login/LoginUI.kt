package io.gradeshift.ui.login

import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.*

class LoginUI : AnkoComponent<LoginActivity> {

    lateinit var loginButton: Button
    lateinit var usernameField: EditText
    lateinit var passwordField: EditText

    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {
        verticalLayout {
            padding = dip(30)
            usernameField = editText {
                hint = "Name"
                textSize = 24f
            }
            passwordField = editText {
                hint = "Password"
                textSize = 24f
            }
            loginButton = button("Login") {
                textSize = 26f
            }
        }
    }
}