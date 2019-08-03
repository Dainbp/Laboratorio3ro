package valhalla.juegolab

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_login.editTextEmail
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import valhalla.juegolab.helpers.DatabaseHelper

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        button_Registrer.setOnClickListener {
            var succes: Long

            val dbHandler = DatabaseHelper(this, null)
            val user: Usuario = Usuario( userName = editTextUserName.text.toString(), email = editTextEmail.text.toString(), password = editTextPassword.text.toString() )

            if (validation()) {
                succes = dbHandler.addUsuario(user)
                if (succes > 0) {
                    user.id = succes
                    Toast.makeText(
                        this,
                        user.userName + " Se ha agregado a la base correctamente",
                        Toast.LENGTH_LONG
                    ).show()

                }
                else
                    Toast.makeText(this,user.userName + " Se ha producido un ERROR", Toast.LENGTH_LONG ).show()
            }
        }

    }

    fun validation(): Boolean {
        var validate = false

        if (!editTextUserName.text.toString().equals("") &&
            !editTextEmail.text.toString().equals("") &&
            !editTextPassword.text.toString().equals("")
        ) {
            validate = true
        }
        else {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_LONG).show()
        }

        return validate
    }

}
