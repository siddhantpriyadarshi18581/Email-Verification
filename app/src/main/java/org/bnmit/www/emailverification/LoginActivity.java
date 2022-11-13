package org.bnmit.www.emailverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText mail, password;
    Button log_btn;
    TextView reg_log;
    public String str_mail, str_pass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        mail = findViewById(R.id.name_log);
        password = findViewById(R.id.password_log);
        log_btn = findViewById(R.id.login_btn);
        reg_log = findViewById(R.id.register_tv_log);
        mAuth = FirebaseAuth.getInstance();

        reg_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                finish();
            }
        });
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Validation();
            }
        });

        //login_btn


    }

    private void Validation() {
        str_mail = mail.getText().toString();
        str_pass = password.getText().toString();
        if (str_mail.isEmpty()) {
            mail.setError("Please fill the field");
            mail.requestFocus();
            return;
        }
        else if (str_pass.isEmpty()) {
            password.setError("Please fill the field");
            password.requestFocus();
            return;
        }
        else{
            mAuth.signInWithEmailAndPassword(str_mail, str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, Home.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Not Logged in", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }
}
