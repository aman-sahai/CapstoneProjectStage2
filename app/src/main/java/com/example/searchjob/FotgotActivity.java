package com.example.searchjob;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FotgotActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotgot);
        setTitle(getResources().getString(R.string.fp));
        auth = FirebaseAuth.getInstance();
        editText = findViewById(R.id.forgotemail);
    }

    public void fotgot(View view) {
        String email = editText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(FotgotActivity.this, getResources().getString(R.string.enteremail), Toast.LENGTH_LONG).show();


        } else {
            final ProgressDialog progressDialog = new ProgressDialog(FotgotActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.ver));
            progressDialog.show();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(FotgotActivity.this, getResources().getString(R.string.resetpass), Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.emailnotpresent), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}