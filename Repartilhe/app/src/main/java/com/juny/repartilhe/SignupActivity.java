package com.juny.repartilhe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;

    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog PD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
    }

    public boolean exitClicked(View view) {

        Intent myIntent = new Intent(getApplicationContext(), StartActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

    public void emailClicked(View view) {
        final ProgressDialog pg = ProgressDialog.show(this, "", "", true);
        auth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pg.dismiss();
                if(task.isSuccessful()) {

                    Toast.makeText(SignupActivity.this, "Bem-Vindo!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this, ScreenActivity.class);
                    startActivity(intent);

                } else {

                    auth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pg.dismiss();

                                    if(task.isSuccessful()){
                                        Toast.makeText(SignupActivity.this, "Cadastrado com Sucesso", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignupActivity.this, NameActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Log.e("ERROR: ", task.getException().toString());
                                        Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                    });
                }
            }
        });

    }
}
