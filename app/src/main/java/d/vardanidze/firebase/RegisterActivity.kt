package d.vardanidze.firebase

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repasswordEditText: EditText
    private lateinit var submitButton: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        init()
        registartion()
    }
    private fun init(){
        emailEditText = findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        repasswordEditText = findViewById(R.id.reeditTextTextPassword)
        submitButton = findViewById(R.id.SubmitButton)

    }
    private fun registartion(){

        submitButton.setOnClickListener{
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val repassword = repasswordEditText.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (isValidEmail(email) == false){
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.length < 8){
                Toast.makeText(this, "Password must contain min 8 charachter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passContaintNUmberNChar(password) == false){
                Toast.makeText(this, "Passwords must contain min one digit and character", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password != repassword){
                Toast.makeText(this, "Passwords unmatch", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Authentication failed!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }


        }
    }

    private fun passContaintNUmberNChar(pass:String): Boolean {
        var a = 0
        var b = 0
        for (i in pass){
            if(i.isDigit()){
                a += 1
            }
            if(i.isLetter()){
                b += 1
            }
        }
        return a >= 1 && b >=1
    }
    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

}