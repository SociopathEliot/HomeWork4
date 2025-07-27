package be.buithg.homework4

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result = intent.getStringExtra("result") ?: ""
        findViewById<TextView>(R.id.tv_title).text = result

        // handle heart color toggle
        val heart = findViewById<ImageView>(R.id.iv_favorite)
        var favorite = false
        heart.setOnClickListener {
            favorite = !favorite
            val color = if (favorite) 0xFFE91E63.toInt() else 0xFFFFFFFF.toInt()
            heart.setColorFilter(color)
        }

        // Next button closes all activities
        findViewById<MaterialButton>(R.id.btn_next).setOnClickListener {
            finishAffinity()
        }
    }
}
