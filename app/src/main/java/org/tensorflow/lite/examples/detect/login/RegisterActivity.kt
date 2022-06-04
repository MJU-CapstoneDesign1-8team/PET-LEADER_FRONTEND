package org.tensorflow.lite.examples.detect.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.HomeActivity
import org.tensorflow.lite.examples.detect.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        database = Firebase.database.reference



        val joinBtn = findViewById<Button>(R.id.registerBtn)
        joinBtn.setOnClickListener {

            val email = findViewById<EditText>(R.id.registerEditTextID)
            val password = findViewById<EditText>(R.id.registerEditTextPassword)
            val nickname = findViewById<EditText>(R.id.registerEditTextNickname)

            val database = Firebase.database
            val nick = database.getReference("Nickname")


            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        nick.child(auth.currentUser!!.uid).setValue(nickname.text.toString())

                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                            Toast.makeText(baseContext, "형식이 올바르지 않습니다.",Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }
}