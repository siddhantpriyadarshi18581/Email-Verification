package org.bnmit.www.emailverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button Register;
    TextView username;
    TextView pass;
    TextView mail;
    TextView phone;
    TextView con_pass;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView signin;
    String str_name, str_pass, str_mail, str_conf_pass, str_phone, str_des;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Register = findViewById(R.id.reg_btn);
        username = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        mail = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);
        con_pass = findViewById(R.id.con_pass);
        signin = findViewById(R.id.sign_tv);

        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        mAuth = FirebaseAuth.getInstance();

        //progress Dialog
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Please Wait...");
//        progressDialog.setCanceledOnTouchOutside(false);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
                str_phone = "+91" + phone.getText().toString();

//                sendVerificationCode(str_phone);
            }
        });
    }

//    private void sendVerificationCode(String number) {
//
//    }


    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);


    }

    private void Validation() {
        str_name = username.getText().toString();
        str_mail = mail.getText().toString();
        str_pass = pass.getText().toString();
        str_conf_pass = con_pass.getText().toString();
//        str_phone = phone.getText().toString();
        str_des = radioButton.getText().toString();

        if (str_name.isEmpty()) {
            username.setError("Please fill Field");
            username.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(phone.getText().toString())) {
            phone.setError("Please fill the phone number");
            phone.requestFocus();
            return;
        }
        else if (!numberCheck(str_phone)) {
            phone.setError("Invalid Mobile Number");
            phone.requestFocus();
            return;
        }
        else if (str_mail.isEmpty()) {
            mail.setError("Please fill the email");
            mail.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(str_mail).matches()) {
            mail.setError("Please enter the valid email");
            mail.requestFocus();
            return;
        }
        else if (str_pass.isEmpty()) {
            pass.setError("Please fill field");
            pass.requestFocus();
            return;
        } else if (!passwordValidation(str_pass)) {
            pass.setError("Enter maximum 6 digits");
            pass.requestFocus();
        }
        else if (str_conf_pass.isEmpty()) {
            pass.setError("Please fill field");
            pass.requestFocus();
            return;
        }
        else{
            Intent intent1 = new Intent(getApplicationContext(), VerifyOTP.class);
            intent1.putExtra("phone", str_phone);
            startActivity(intent1);
            finish();
        }
//        mAuth.createUserWithEmailAndPassword(str_mail, str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Toast.makeText(MainActivity.this, "Registered Successfully. Please Check your mail", Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
////                                startActivity(intent);
////                                finish();
//                            }
//                            else{
//                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                }
//            }
//        });


    }

    private boolean numberCheck(String str_phone) {
        Pattern p = Pattern.compile("[0-9]{10}");
        Matcher m = p.matcher(str_phone);
        return m.matches();
    }
    private boolean passwordValidation(String str_pass) {
        Pattern p = Pattern.compile(".{6}");
        Matcher m = p.matcher(str_pass);
        return m.matches();
    }
}
