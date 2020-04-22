package com.s17131890.carelite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.s17131890.carelite.databinding.ActivityLoginBinding;

import java.util.jar.Manifest;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText Email;
    private EditText Password;
    private Button LoginButton;
    private ActivityLoginBinding binding;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();

        
        Email = binding.username;
        Password = binding.password;
        LoginButton=binding.submit;


        
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                if(email.equals("")){
                    Email.setError("Please Fill");
                }
                else if (password.equals("")){
                    Password.setError("Please Fill");
                }
                else if(email.equals("") && password.equals("")){
                    Email.setError("Please Fill");
                    Password.setError("Please Fill");
                }
                else if(!email.equals("") && !password.equals("")){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("LOGIN", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if(user!=null){

                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Log.d("LOGIN","AAAAAAA");

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();;
        }
    }


}
