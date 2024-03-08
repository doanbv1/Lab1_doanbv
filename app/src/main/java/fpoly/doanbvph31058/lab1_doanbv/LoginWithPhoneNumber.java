package fpoly.doanbvph31058.lab1_doanbv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.installations.FirebaseInstallationsException;

import java.util.concurrent.TimeUnit;

public class LoginWithPhoneNumber extends AppCompatActivity {

    Button btn_GetOTP, btn_Login;
    EditText edt_PhoneNumber, edt_OTP;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String mVerificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone_number);

        btn_GetOTP = findViewById(R.id.btn_getOTP);
        btn_Login = findViewById(R.id.btn_Login);

        edt_OTP = findViewById(R.id.txt_OTP);
        edt_PhoneNumber = findViewById(R.id.txt_phoneNumber);

        mAuth = FirebaseAuth.getInstance();


        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                edt_OTP.setText(phoneAuthCredential.getSmsCode());

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(LoginWithPhoneNumber.this, "Loi Lay OTP", Toast.LENGTH_SHORT).show();
                Log.e("TAG", e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                mVerificationID = s;
            }
        };

        btn_GetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Phone = edt_PhoneNumber.getText().toString().trim();
                if (Phone.isEmpty()) {
                    Toast.makeText(LoginWithPhoneNumber.this, "Phai Nhap SDT", Toast.LENGTH_SHORT).show();
                } else if (!Phone.matches("[0-9]+")) {
                    Toast.makeText(LoginWithPhoneNumber.this, "SDT chi duoc chua cac ky tu la so", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginWithPhoneNumber.this, "SDT" + Phone, Toast.LENGTH_SHORT).show();
                    getOTP(Phone);
                }
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String maOTP = edt_OTP.getText().toString().trim();
                    if (maOTP.isEmpty()){
                        Toast.makeText(LoginWithPhoneNumber.this, "Phai nhap Ma OTP", Toast.LENGTH_SHORT).show();

                    }
                    Toast.makeText(LoginWithPhoneNumber.this, "OTP:"+ maOTP, Toast.LENGTH_SHORT).show();
                    verifyOTP(maOTP);
                }catch (Exception ex){
                    Log.e("ERRRRRRR", "exxxxx", ex);
                }

            }
        });

    }

    private void getOTP(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84" + phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallback)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void verifyOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginWithPhoneNumber.this, "Danh Nhap Thanh Cong", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginWithPhoneNumber.this, Home.class));
                        } else {
                            edt_OTP.setError("OTP Khong Dung");
                            Log.e("Taaaaaaaa","Fail",task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){

                            }
                        }
                    }
                });
    }
}