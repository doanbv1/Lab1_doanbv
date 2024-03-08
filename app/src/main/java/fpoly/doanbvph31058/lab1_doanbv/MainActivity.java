package fpoly.doanbvph31058.lab1_doanbv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextInputEditText txt_userName, txt_passWord;
    TextView tv_ForgetPW, tv_SignUp, tv_LoginWidthPhoneNumber;
    Button btn_Login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_userName = findViewById(R.id.txt_userName);
        txt_passWord =findViewById(R.id.txt_passWord);
        tv_ForgetPW = findViewById(R.id.tv_forgetPW);
        tv_SignUp = findViewById(R.id.tv_SignUp);
        tv_LoginWidthPhoneNumber =findViewById(R.id.tv_loginWithPhoneNumber);
        btn_Login = findViewById(R.id.btn_login);


        mAuth = FirebaseAuth.getInstance();

        String userName = null,passWord = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
             userName = bundle.getString("UserName");
             passWord = bundle.getString("PassWord");

        }
        txt_userName.setText(userName);
        txt_passWord.setText(passWord);
//        mAuth = FirebaseAuth.getInstance();
        tv_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txt_userName.getText().toString().trim();
                String password = txt_passWord.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Danh Nhap Thanh Cong", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, Home.class);

                            startActivity(intent);

                        }else{
                            Toast.makeText(MainActivity.this, "Danh khong Nhap Thanh Cong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

       tv_LoginWidthPhoneNumber.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent    = new Intent(MainActivity.this, LoginWithPhoneNumber.class);

               startActivity(intent);

           }
       });

       tv_ForgetPW.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, ForgotPassWord.class));
           }
       });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
//            reload();
        }
    }
}