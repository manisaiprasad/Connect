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

public class RegisterActivity extends AppCompatActivity {
    private EditText regEmail;
    private EditText regPassword;
    private EditText regConPassword;
    private Button regBttn;
    private ProgressBar reg_progress;
    private Button reg_login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        regEmail =(EditText)findViewById(R.id.reg_email);
        regPassword =(EditText)findViewById(R.id.reg_password);
        regConPassword =(EditText)findViewById(R.id.reg_password_confrim);
        regBttn=(Button)findViewById(R.id.rig);
        reg_login=(Button)findViewById(R.id.reg_login);
        reg_progress=(ProgressBar)findViewById(R.id.rigprocress);
        reg_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        regBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=regEmail.getText().toString();
                String pass=regPassword.getText().toString();
                String conpass=regConPassword.getText().toString();

                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(conpass)){

                    if (pass.equals(conpass)){
                        reg_progress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                sendToMain();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String errorMsg=e.getMessage();
                                Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                                reg_progress.setVisibility(View.INVISIBLE);

                            }
                        });

                    }else {
                        Toast.makeText(RegisterActivity.this,"password is not match",Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(RegisterActivity.this,"Enter the Email & Password",Toast.LENGTH_SHORT);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser!=null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
