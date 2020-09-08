package shehan.com.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registration extends AppCompatActivity {

    private static final String EMAIL_PASSWORD_LOGIN = "EMAIL_PASSWORD_LOGIN";
    private static final String UID = "UID";

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText age;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText mobile;
    private TextView loginHere;

    private Button creatAccountBtn;
    boolean isEmailPasswordLoginEnabled;
    String uid_Google;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent = getIntent();
        isEmailPasswordLoginEnabled = intent.getBooleanExtra(EMAIL_PASSWORD_LOGIN, true);
        uid_Google = intent.getStringExtra(UID);


        firstName=findViewById(R.id.first_name);
        lastName=findViewById(R.id.last_name);
        age=findViewById(R.id.age);
        email=findViewById(R.id.emailto);
        password=findViewById(R.id.passwordto);
        loginHere = findViewById(R.id.loginHere);
        mobile = findViewById(R.id.phone_number);

        creatAccountBtn=findViewById(R.id.create_accountbtn);

        if (!isEmailPasswordLoginEnabled){
            email.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
        }

        creatAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmailPasswordLoginEnabled) {
                    createUserAccount(email.getText().toString(), password.getText().toString());
                }else {
                    saveUserInfoToFirbase(uid_Google);
                }

            }
        });
        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    private void createUserAccount(String email,String password){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String uid = task.getResult().getUser().getUid();
                    saveUserInfoToFirbase(uid);
                }
                Toast.makeText(Registration.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registration.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    void saveUserInfoToFirbase(String uid){
        String firstname = this.firstName.getText().toString();
        String lastname = this.lastName.getText().toString();
        String age = this.age.getText().toString();
        String mobile = this.mobile.getText().toString();
        User user= new User(firstname,lastname,age,mobile);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Registration.this, "User successfully saved to firestore!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registration.this, "User save failed: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}