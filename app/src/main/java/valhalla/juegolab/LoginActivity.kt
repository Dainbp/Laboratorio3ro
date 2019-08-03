package valhalla.juegolab
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import valhalla.juegolab.helpers.DatabaseHelper


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_Login.setOnClickListener {
            val dbHandler = DatabaseHelper(this, null)
            var user: Usuario

            if (validation()) {
                    user = dbHandler.getUserByEmail(email = editTextEmail.text.toString())

                 if (!user.email.equals(editTextEmail.text.toString())){
                        Toast.makeText(
                            this,
                            user.userName + "El usuario no existe",
                            Toast.LENGTH_LONG
                        ).show()
                 }
                    else {

                        Toast.makeText(this, user.userName + "", Toast.LENGTH_LONG).show()
                     val intent = Intent(this, MainActivity ::class.java)
                     startActivity(intent);
                    }
                }
            }
        }

    private fun validation(): Boolean {
        var validate = false

        if (!editTextEmail.text.toString().equals("") && !editTextPassword.text.toString().equals(""))
            validate = true
        else
        {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_LONG).show()
        }

        return validate
    }

}


