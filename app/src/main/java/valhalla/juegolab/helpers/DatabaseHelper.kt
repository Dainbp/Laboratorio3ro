package valhalla.juegolab.helpers
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import valhalla.juegolab.Usuario

class DatabaseHelper(context: Context, factory:SQLiteDatabase.CursorFactory?)
    : SQLiteOpenHelper(context,
    DATABASE_NAME, factory,
    DATABASE_VERSION
) {

    /*Variables de instancia*/
    private val context : Context? = null
    var db:SQLiteDatabase? = null

    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //Se elimina la tabla usuari si existe
        db.execSQL(DROP_USER_TABLE)

        // Se vuelve a crear
        onCreate(db)

    }

    /**
     * Este metodo crea un nuevo usuario
     *
     * @param user
     * @return el id si se guardo correctamente/ -1 si fallo
     */
    fun addUsuario(user: Usuario):Long
    {
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.userName)
        values.put(COLUMN_USER_EMAIL,user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        var id:Long
        val db = this.writableDatabase

        id = db.insert(TABLE_USER, null, values)
        db.close()

        return id
    }

    /**
     * Este metodo elimina un usuario
     *
     * @param user
     */
    fun deleteUser(user: Usuario) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(
            TABLE_USER, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()


    }

    /**
     * Este metodo retorna una lista de los usuarios
     *
     *
     * @return list
     */

    fun getAllName(): List<Usuario>
    {
        //variable para que la base sea solo lectura
        val db = this.readableDatabase
        //columnas requeridas en la consulta
        val columns = arrayOf(
            COLUMN_USER_ID,
            COLUMN_USER_NAME,
            COLUMN_USER_EMAIL,
            COLUMN_USER_PASSWORD
        )
        // Ordenar por nombre de usuario
        val sortOrder = "$COLUMN_USER_NAME ASC"
        //lista de usuarios a devolver
        val userList = ArrayList<Usuario>()

        // query the user table
        val cursor = db.query(
            TABLE_USER, //Tabla en donde se ejecuta la query
            columns,            //columas que retorna
            null,     //columnas para la clausula WHERE
            null,  //Los valores para la clausula WHERE
            null,
            null,
            sortOrder)         //Metodo a ordenar
        if (cursor.moveToFirst()) {
            do {
                val user = Usuario(
                    userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
                )
                user.id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toLong()
                userList.add(user)
            } while (cursor.moveToNext()) //itera mientras existan mas row en la variable cursor
        }
        cursor.close()
        db.close()
        return userList

    }

    /**
     * Este metodo retorna un Usuario a partir de el email
     *
     * @param email
     * @return list
     */
    fun getUserByEmail(email: String): Usuario {
        //variable para que la base sea solo lectura
        val db = this.readableDatabase
        //columnas requeridas en la consulta
        val columns = arrayOf(
            COLUMN_USER_ID,
            COLUMN_USER_NAME,
            COLUMN_USER_EMAIL
        )
        //criterio para consultar
        val criterio = "$COLUMN_USER_EMAIL = ?"
        // seleccion de argumentos
        val selectionArgs = arrayOf(email)
        //se genera una varieble de tipo usuario para retornar
        val user: Usuario = Usuario()

        // query de tabla de usurario con una condicion
        /**
         * SELECT id, userName,email FROM usuario WHERE email = 'ejemplo@gmail.com';
         */

        val cursor = db.query(
            TABLE_USER, //tabla en donde se ejecuta la query
            columns, //columnas que retorna
            criterio, //columnas que se utilizan en el WHERE
            selectionArgs, // valores que se chequean en el WHERE
            null,
            null,
            null)

        if (cursor != null) {
            cursor.moveToFirst()
            //while (cursor.moveToNext()) {
                user.id = cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID))
                user.userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
                user.email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))

            //}
        }
        cursor.close()
        db.close()

        return user
    }

    /**
     * Este metodo chequea dado un email si el usuario ya se encuentra registrado
     *
     * @param email
     * @return true/false
     */
    fun chekEmail(email: String):Boolean /* */
    {
        //variable para que la base sea solo lectura
        val db = this.readableDatabase
        //columnas requeridas en la consulta
        val columns = arrayOf(COLUMN_USER_ID)


        //criterio para consultar
        val criterio = "$COLUMN_USER_EMAIL = ?"
        // seleccion de argumentos
        val selectionArgs = arrayOf(email)

        // query de tabla de usurario con una condicion
        /**
         * SELECT _id FROM usuario WHERE email = 'ejemplo@gmail.com';
         */

        val cursor = db.query(
            TABLE_USER, //tabla en donde se ejecuta la query
            columns, //columnas que retorna
            criterio, //columnas que se utilizan en el WHERE
            selectionArgs, // valores que se chequean en el WHERE
            null,
            null,
            null)

        val cursorCount = cursor.count
        var existe = false
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            existe = true
        }

        return existe
    }

    /**
     * Este metodo es para logear el usuario
     *
     * @param email
     * @param password Ya debe ser el has no el texto plano
     * @return true/false
     */
    fun checkUser(email: String, password: String): Boolean {

        //columnas requeridas en la consulta
        val columns = arrayOf(COLUMN_USER_ID)
        //accedemos a la BD en solo lectura
        val db = this.readableDatabase

        // criterio de seleccion para el WHERE
        val criterio = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        // seleccion de argumentos
        val selectionArgs = arrayOf(email, password)

        // consultamos la tabla de usuarios
        /**
         * SELECT user_id FROM user WHERE user_email = 'ejemplo@gmail.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns, //columnas a retornar
            criterio, //columnas para la clausula WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        var existe = false
        cursor.close()
        db.close()

        if (cursorCount > 0)
            existe =  true

        return existe

    }

    companion object /*Definicion de variables de clases*/
    {
        /*Version de la base de datos*/
        private val DATABASE_VERSION = 1
        /*Nombre de la base de datos*/
        private val DATABASE_NAME = "test.db"
        /*Direccion en donde se encuenta la BD*/
        private val DATABASE_PATH = "Context.getFilesDir().getPath()"

        /*Tabla Usuario*/
        private val TABLE_USER = "usuario"
        /*Columnas de la tabla Usuario*/
        private val COLUMN_USER_ID = "_id"
        private val COLUMN_USER_NAME = "username"
        private val COLUMN_USER_EMAIL = "email"
        private val COLUMN_USER_PASSWORD = "password"

    }





}