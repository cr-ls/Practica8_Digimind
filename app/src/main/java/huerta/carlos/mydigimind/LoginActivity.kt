package huerta.carlos.mydigimind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn_registrarse: Button = findViewById(R.id.btn_registrarse)
        val btn_contra: TextView = findViewById(R.id.tv_olvidasteContra)
        val btn_ingresar: Button = findViewById(R.id.btn_ingresar)

        btn_registrarse.setOnClickListener {
            val intent: Intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btn_contra.setOnClickListener {
            val intent: Intent = Intent(this, ContrasenaActivity::class.java)
            startActivity(intent)
        }

        btn_ingresar.setOnClickListener {
            valida_ingreso()
        }
    }

    private fun valida_ingreso() {
        val et_correo: EditText = findViewById(R.id.et_correo)
        val et_contra: EditText = findViewById(R.id.et_contra)

        var correo: String = et_correo.text.toString()
        var contra: String = et_contra.text.toString()

        if (!correo.isNullOrBlank() && contra.isNullOrBlank()) {
            //ingresaFirebase
        } else {
            Toast.makeText(
                this, "Ingresar datos",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}