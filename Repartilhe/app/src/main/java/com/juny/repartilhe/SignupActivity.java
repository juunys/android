package com.juny.repartilhe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;

    String email;
    String password;

    private ProgressBar progressBar;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        editTextEmail.addTextChangedListener(mTextWatcher);
        editTextPassword.addTextChangedListener(mTextWatcher);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }


    public void emailClicked(View view) {

        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email vazio");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email inválido");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Senha vazia");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Senha menor que 6 dígitos");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()) {

                    Toast.makeText(SignupActivity.this, "Bem-Vindo", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this, ScreenActivity.class);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(SignupActivity.this, NameActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });

    }

    public boolean exitClicked(View view) {

        Intent myIntent = new Intent(getApplicationContext(), StartActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmptyValues();
        }
    };


    void checkFieldsForEmptyValues(){

        Button b = findViewById(R.id.enterButton);

        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();

        if((!email.equals("")) && (!pass.equals(""))){

            Drawable d = getResources().getDrawable(R.drawable.login_button_shape_enable);
            b.setEnabled(true);
            b.setBackground(d);
            b.setTextColor(Color.WHITE);

        } else {

            Drawable d = getResources().getDrawable(R.drawable.login_button_shape_disable);
            b.setEnabled(false);
            b.setBackground(d);
            b.setTextColor(Color.GRAY);

        }
    }

}
