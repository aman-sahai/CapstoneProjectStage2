package com.example.searchjob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegiterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText useremail, userpass;
    ProgressDialog dialog;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);


        setTitle(getResources().getString(R.string.reg));
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        useremail = findViewById(R.id.email);
        userpass = findViewById(R.id.pwd);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

    }

    public void Register(View view) {
        final String uemail = useremail.getText().toString();
        final String upass = userpass.getText().toString();
        if (TextUtils.isEmpty(uemail) || TextUtils.isEmpty(upass)) {
            Toast.makeText(RegiterActivity.this, getResources().getString(R.string.emailusername), Toast.LENGTH_LONG).show();


        } else {
            auth.createUserWithEmailAndPassword(uemail, upass)
                    .addOnCompleteListener(RegiterActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        dialog.dismiss();

                                        Toast.makeText(RegiterActivity.this,
                                                getResources().getString(R.string.regsuc), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegiterActivity.this, MainActivity.class));
                                        finish();

                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(RegiterActivity.this, getResources()
                                                .getString(R.string.regunsuc), Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
        }
    }
}
