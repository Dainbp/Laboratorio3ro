package valhalla.juegolab

import android.text.LoginFilter
import java.security.MessageDigest

class Usuario {
    var id: Long = 0
    lateinit var userName: String
    lateinit var email: String
    lateinit var password : String

    constructor(userName: String, email: String, password: String) {
        try {
            /*para el password sE crear una funcion para guardarla en formato hash*/
            this.userName = userName
            this.email = email
            this.password = this.hasPas(password)


        } catch (e: Exception) {
        }

    }

    constructor(){
        try {
            /*para el password sE crear una funcion para guardarla en formato hash*/
            this.id = 0
            this.userName = ""
            this.email = ""
            this.password = ""
        } catch (e: Exception) {
        }
    }

    fun hasPas(password: String): String {
            val HEX_CHARS = "0123456789ABCDEF" /* se utiliza para transformar el resultado de hexa a chart */
            val bytes = MessageDigest
                .getInstance("SHA-256") /*Llama a la funcion para aplicar el algoritmo sha-256*/
                .digest(password.toByteArray())   /*Se castea el string a byte para poder aplicar el algoritmo*/
            val result = StringBuilder(bytes.size * 2)

            bytes.forEach {
                val i = it.toInt()
                result.append(HEX_CHARS[i shr 4 and 0x0f])  /* le cambia la base de 16 a 10 y lo transforma a caracteres*/
                result.append(HEX_CHARS[i and 0x0f])        /* de esta manera devuelve un strig que ocupa menos espacio en la base que en hexa*/
            }


            return result.toString()
        }


}