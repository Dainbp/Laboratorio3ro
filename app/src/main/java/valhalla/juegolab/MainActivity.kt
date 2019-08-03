package valhalla.juegolab

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import valhalla.juegolab.helpers.DatabaseHelper

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init db
        dbHelper = DatabaseHelper(this, null)

        buttonConfig.setOnClickListener{
            val intent = Intent(this, ConfigActivity ::class.java)
            startActivity(intent);
        }
        buttonLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity ::class.java)
            startActivity(intent);
        }
        buttonRanking.setOnClickListener {
            val intent = Intent(this, RankingActivity ::class.java)
            startActivity(intent);
        }
        buttonList.setOnClickListener {
            val intent = Intent(this, ListUserActivity ::class.java)
            startActivity(intent);
        }
    }


}
