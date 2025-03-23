package com.example.mobileapps;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText usernameSignUp, surelSignUp, sandiSignUp, nimSignUp;
    Button btnSignUp;
    FirebaseAuth auth;
    DatabaseReference databaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        usernameSignUp = findViewById(R.id.userSignUp);
        surelSignUp = findViewById(R.id.emailSignUp);
        sandiSignUp = findViewById(R.id.passwordSignUp);
        nimSignUp = findViewById(R.id.nimUserSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(view -> {
            String userName = Objects.requireNonNull(usernameSignUp.getText()).toString().trim();
            String password = Objects.requireNonNull(sandiSignUp.getText()).toString().trim();
            String email = Objects.requireNonNull(surelSignUp.getText()).toString().trim();
            String NIM = Objects.requireNonNull(nimSignUp.getText()).toString().trim();
            if (TextUtils.isEmpty(userName)) {
                usernameSignUp.setError("Masukkan Username!");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                sandiSignUp.setError("Masukkan Password!");
                return;
            }
            if (password.length() < 6) {
                sandiSignUp.setError("Password minimal 6 karakter!");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                surelSignUp.setError("Masukkan Email!");
                return;
            }
            if (TextUtils.isEmpty(NIM)) {
                nimSignUp.setError("Masukkan NIM!");
                return;
            }

            registerUser(userName, email, password, NIM);
        });
    }

    private void registerUser(String nama, String email, String password, String nim) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fUser = auth.getCurrentUser();
                        if (fUser != null) {
                            String uid = fUser.getUid();
                            UserDetails user = new UserDetails(uid, nama, email, password, nim);

                            databaseRef.child(uid).setValue(user).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    fUser.sendEmailVerification();
                                    Toast.makeText(SignUpActivity.this, "Akun berhasil dibuat. Verifikasi email Anda!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginPageActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.e(TAG, "Register: Database Error", task1.getException());
                                    Toast.makeText(SignUpActivity.this, "Registrasi gagal, coba lagi nanti!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Log.e(TAG, "Register: Auth Error", task.getException());
                        Toast.makeText(SignUpActivity.this, "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}


