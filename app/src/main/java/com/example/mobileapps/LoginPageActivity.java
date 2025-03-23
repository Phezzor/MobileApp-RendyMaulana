package com.example.mobileapps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPageActivity extends AppCompatActivity {
    TextInputEditText username, password;
    CheckBox checkBoxes;
    Button tmblLogin;
    TextView lupapassword, signup;
     FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        checkBoxes = findViewById(R.id.checkboxes);
        tmblLogin = findViewById(R.id.btnLogin);
        lupapassword = findViewById(R.id.forgotPass);
        signup = findViewById(R.id.signUp);


            tmblLogin.setOnClickListener(view -> {
                String email = String.valueOf(username.getText()).trim();
                String passwordUser = String.valueOf(password.getText()).trim();

                if (email.isEmpty()) {
                    username.setError("Email tidak boleh kosong!");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    username.setError("Format Email tidak valid!");
                    return;
                }
                if (passwordUser.isEmpty()) {
                    password.setError("Password tidak boleh kosong!");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, passwordUser)
                        .addOnSuccessListener(authResult -> {
                            Toast.makeText(LoginPageActivity.this, "Login Berhasil", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginPageActivity.this, HomePageActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(LoginPageActivity.this, "Login Gagal: " + e.getMessage(), Toast.LENGTH_LONG).show());
            });
        signup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPageActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}