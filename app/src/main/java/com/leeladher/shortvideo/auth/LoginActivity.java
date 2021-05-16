package com.leeladher.shortvideo.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.LocusId;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.leeladher.shortvideo.DashBoardActivity;
import com.leeladher.shortvideo.R;
import com.leeladher.shortvideo.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in... ");
        progressDialog.setCancelable(false);

        binding.gotoreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class );
                startActivity(intent);
                finish();
            }
        });

        binding.emailBox.requestFocus();
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email =  binding.emailBox.getText().toString();
                String pass= binding.passwordBox.getText().toString();


                if (email.isEmpty()) {
                    binding.emailBox.setError("required");
                    binding.emailBox.requestFocus();
                }
                else if (pass.isEmpty()) {
                    binding.passwordBox.setError("required");
                    binding.passwordBox.requestFocus();

                }
                else {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent intent  = new Intent(LoginActivity.this, DashBoardActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                }

            }
        });

        binding.gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

    }
}