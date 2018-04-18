package com.example.manisaiprasad.myblogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBttn;
    private ProgressBar progressBar;
    private Button loginReg;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginBttn=(Button)findViewById(R.id.login);
        loginReg=(Button)findViewById(R.id.login_reg);
        loginEmail=(EditText)findViewById(R.id.reg_email);
        loginPassword=(EditText)findViewById(R.id.reg_password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);


        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=loginEmail.getText().toString();
                String pass=loginPassword.getText().toString();

                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pass)){
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendToMain();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String errorMsg=e.getMessage();
                            Toast.makeText(loginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });
                }else {
                    Toast.makeText(loginActivity.this, "Please Enter Email and PassWord", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void sendToRegister(View view) {
        Intent intent=new Intent(loginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //checking user loged in
        FirebaseUser currentuser = mAuth.getInstance().getCurrentUser();
        if(currentuser!=null){

            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent=new Intent(loginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
