package fpoly.doanbvph31058.lab1_doanbv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassWord extends AppCompatActivity {
    Button btn_senEmail;
    EditText edt_Email;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_word);

        btn_senEmail = findViewById(R.id.btn_senEmail);
        edt_Email = findViewById(R.id.edt_email);

        btn_senEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_Email.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(ForgotPassWord.this, "Email khong duoc de trong !", Toast.LENGTH_SHORT).show();

                }
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassWord.this, "Chúng tôi đã gửi mail đến hộp thư của bạn để đổi mật khẩu!" + email, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPassWord.this, "Không thể gửi mail. Hãy kiểm tra lại địa chỉ email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}