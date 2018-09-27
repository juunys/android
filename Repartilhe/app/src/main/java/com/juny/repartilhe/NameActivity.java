package com.juny.repartilhe;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.Calendar;

public class NameActivity extends AppCompatActivity {

    private static final String TAG = "NameActivity";


    Calendar myCalendar = Calendar.getInstance();

    DatabaseReference mDatabase;

    User user;

    private FirebaseAuth auth;

    private ProgressBar progressBar;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private EditText editTextname;
    private EditText editTextLastName;
    private TextView textViewBirthday;

    String email;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        editTextname = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        textViewBirthday = findViewById(R.id.textViewBirthday);

        editTextname.addTextChangedListener(mTextWatcher);
        editTextLastName.addTextChangedListener(mTextWatcher);
        textViewBirthday.addTextChangedListener(mTextWatcher);

        setBirthDay();

    }

    public void setBirthDay() {

        textViewBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NameActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Add 1 because month was -1
                month = month + 1;

                String date = dayOfMonth + "/" + month + "/" + year;

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                textViewBirthday.setText(date);

            }
        };

    }

    public void goClicked(View view) {

        final String name = editTextname.getText().toString();
        final String lname = editTextLastName.getText().toString().trim();
        final String bday = textViewBirthday.getText().toString().trim();
        final String age = getAge(myCalendar);
        final String password = getIntent().getStringExtra("password");
        final String email = getIntent().getStringExtra("email");

        if (name.isEmpty()) {

            editTextname.setError("Nome Vazio");
            editTextname.requestFocus();
            return;

        }

        if (lname.isEmpty()) {

            editTextLastName.setError("Último nome vazio");
            editTextLastName.requestFocus();
            return;

        }

        if (bday.isEmpty()) {

            textViewBirthday.setError("Data vazia");
            textViewBirthday.requestFocus();
            return;

        }

        if (Integer.parseInt(age) < 18) {
            textViewBirthday.setError("Menor de idade!");
            textViewBirthday.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(

                                    name,
                                    lname,
                                    email,
                                    bday

                            );

                            mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                            mDatabase.child(auth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {

                                        Toast.makeText(NameActivity.this, "Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(NameActivity.this, ScreenActivity.class);
                                        startActivity(intent);

                                    } else {

                                        Toast.makeText(NameActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        } else {

                            Toast.makeText(NameActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });


        progressBar.setVisibility(View.GONE);

    }

    private String getAge(Calendar born){
        Calendar today = Calendar.getInstance();


        int age = today.get(Calendar.YEAR) - born.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
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

            checkFieldsForEmptyValues();

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmptyValues();
        }
    };


    void checkFieldsForEmptyValues(){

        Button b = findViewById(R.id.enterButton);

        String name = editTextname.getText().toString();
        String lname = editTextLastName.getText().toString();
        String bday = textViewBirthday.getText().toString();

        System.out.println("bana: " + name+ " "+lname + " " + bday);

        if((!name.equals("")) && (!lname.equals("")) && (!bday.equals(""))){

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
