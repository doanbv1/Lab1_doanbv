package fpoly.doanbvph31058.lab1_doanbv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    EditText edt_UserName, edt_PassWord, edt_ConfirmPassWord;
    Button btn_SignUp;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    private void initView(){
        edt_UserName = findViewById(R.id.edt_UserName);
        edt_PassWord = findViewById(R.id.edt_PassWord);
        edt_ConfirmPassWord = findViewById(R.id.edt_ConfirmPassWord);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();



        btn_SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = edt_UserName.getText().toString().trim();
                String passWord = edt_PassWord.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email, passWord)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Dang Ky Thanh Cong", Toast.LENGTH_SHORT).show();
                                    Intent intent   = new Intent(SignUp.this, MainActivity.class);

                                    Bundle bundle    = new Bundle();
                                    bundle.putString("UserName", email);
                                    bundle.putString("PassWord", passWord);
                                    intent.putExtras(bundle);

                                    startActivity(intent);
//                            updateUI(user);
                                } else {
                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}