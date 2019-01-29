package valhalla.juegolab

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity ::class.java)
            startActivity(intent);
        }
        buttonRanking.setOnClickListener {
            val intent = Intent(this, RankingActivity ::class.java)
            startActivity(intent);
        }
    }


}
