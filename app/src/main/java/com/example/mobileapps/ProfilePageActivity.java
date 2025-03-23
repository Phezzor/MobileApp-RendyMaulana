package com.example.mobileapps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePageActivity extends AppCompatActivity {

    TextView txtNama, txtEmail, txtNIM;
     Button btnLogout;
     FirebaseAuth mAuth;
     DatabaseReference databaseReference;
     FirebaseUser currentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Inisialisasi Referensi Database Firebase
        databaseReference = FirebaseDatabase.getInstance("https://mobile-apps-6aff7-default-rtdb.firebaseio.com/").getReference("Users");

        // Inisialisasi UI
        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmail);
        txtNIM = findViewById(R.id.txtNIM);
        btnLogout = findViewById(R.id.btnLogout);

        // Cek apakah pengguna sedang login
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Log.d("FirebaseDebug", "User ID: " + userId);

            // Ambil data pengguna dari Firebase
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        try {
                            String username = snapshot.child("username").getValue(String.class);
                            String userEmail = snapshot.child("userEmail").getValue(String.class);
                            String userNIM = snapshot.child("userNIM").getValue(String.class);

                            // Set data ke UI
                            txtNama.setText(username != null ? username : "Nama Tidak Ditemukan");
                            txtEmail.setText(userEmail != null ? userEmail : "Email Tidak Ditemukan");
                            txtNIM.setText(userNIM != null ? userNIM : "NIM Tidak Ditemukan");

                        } catch (Exception e) {
                            Log.e("FirebaseError", "Error parsing data", e);
                        }
                    } else {
                        txtNama.setText("Data tidak ditemukan");
                        txtEmail.setText("-");
                        txtNIM.setText("-");
                        Log.e("FirebaseError", "Data snapshot tidak ditemukan");
                    }
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    txtNama.setText("Error Loading Data");
                    Log.e("FirebaseError", "Database error: " + error.getMessage());
                }
            });
        } else {
            Log.e("FirebaseError", "Pengguna belum login");
        }

        // Logout Button
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(ProfilePageActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
