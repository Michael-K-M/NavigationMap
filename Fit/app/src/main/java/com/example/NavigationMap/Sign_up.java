package com.example.NavigationMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class Sign_up extends AppCompatActivity {
    EditText F_FN,F_SUR,F_Email,F_Password,FCal,FWeight;
    Button F_Btn_Reg;
    TextView F_Btn_Log;
    ProgressBar P_bar;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        F_FN = findViewById(R.id.Fname);
        F_SUR = findViewById(R.id.Surname);
        FCal = findViewById(R.id.tCal);
        FWeight = findViewById(R.id.tWeg);
        F_Email = findViewById(R.id.Email);
        F_Password = findViewById(R.id.Password);
        F_Btn_Reg = findViewById(R.id.Btn_register);
        F_Btn_Log = findViewById(R.id.btn_Sin);


        P_bar = findViewById(R.id.progressBar);

        F_Btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = F_Email.getText().toString().trim();
                String FirstN = F_FN.getText().toString().trim();
                String Surname = F_SUR.getText().toString().trim();
                String calories = FCal.getText().toString().trim();
                String weight = FWeight.getText().toString().trim();
                String password = F_Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    F_Email.setError("Plz insert Correct Email");
                    return;
                }
                if (TextUtils.isEmpty(FirstN)) {
                    F_FN.setError("Plz insert Correct name");
                    return;
                }
                if (TextUtils.isEmpty(Surname)) {
                    F_SUR.setError("Plz insert Correct Surname");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    F_Password.setError("Plz insert Correct Password");
                    return;
                }
                if (TextUtils.isEmpty(weight)) {
                    FWeight.setError("Plz insert Weight");
                    return;
                }
                if (TextUtils.isEmpty(calories)) {
                    FCal.setError("Plz insert Calories");
                    return;
                }
                if (password.length() < 6) {
                    F_Password.setError("Password Must be more than 6 Characters my FRIEND");
                    return;
                }
                P_bar.setVisibility(View.VISIBLE);


                User user = new User();
                user.Goalcalories = calories;
                user.email = email;
                user.FirstN = FirstN;
                user.Surname = Surname;
                user.password = password;
                user.Goalweight = weight;
                dbHelper.addUser(user);

                // this is used to register the user in the firebase DB
                Toast.makeText(Sign_up.this, "User was happily Created. Enjoy!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
                F_Btn_Log.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });
        }
    }