package com.example.searchjob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    SharedPreferences preferences;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.mainpage));
        email = findViewById(R.id.et1);
        password = findViewById(R.id.et2);
        auth = FirebaseAuth.getInstance();
        if (getSharedPreferences(getResources().getString(R.string.aman),
                Context.MODE_PRIVATE).getInt(getResources().getString(R.string.aman), 0) == 1) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
        preferences = getSharedPreferences(getResources().getString(R.string.aman), Context.MODE_PRIVATE);

    }

    public void signin(View view) {
        final String mail = email.getText().toString();
        final String passw = password.getText().toString();


        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(passw)) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.emailusername), Toast.LENGTH_LONG).show();


        } else {
            auth.signInWithEmailAndPassword(mail, passw)

                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i1 = new Intent(getApplicationContext(), HomeActivity.class);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt(getResources().getString(R.string.aman), 1);
                                editor.apply();

                                startActivity(i1);
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.loginsuccess), Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.invalid), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }


    }

    public void register(View view) {
        Intent intent = new Intent(getApplicationContext(), RegiterActivity.class);
        startActivity(intent);
    }

    public void fotgot(View view) {
        Intent intent = new Intent(getApplicationContext(), FotgotActivity.class);
        startActivity(intent);
    }
}
