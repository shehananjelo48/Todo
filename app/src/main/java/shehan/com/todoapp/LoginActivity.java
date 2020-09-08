package shehan.com.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN_GOOGLE=1;
    private static final String EMAIL_PASSWORD_LOGIN = "EMAIL_PASSWORD_LOGIN";
    private static final String UID = "UID";
     private TextView registor;
    String uid;

     private Button login;
     private TextInputEditText email;
     private TextInputEditText password;
     private GoogleSignInClient mGoogleSignInClient;

    SignInButton continuegoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        email=findViewById(R.id.emailto);
        password=findViewById(R.id.passwordto);
        continuegoogle = findViewById(R.id.sign_in_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usreEmail = email.getText().toString();
                String usrePassword = password.getText().toString();
                doLogin(usreEmail,usrePassword);

            }
        });


        registor = findViewById(R.id.registra);
        registor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Registration.class);
                intent.putExtra(EMAIL_PASSWORD_LOGIN,true);
                startActivity(intent);
            }
        });
        continuegoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                google();
            }
        });
    }

    private void google() {
        GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN_GOOGLE && data!=null && resultCode == RESULT_OK ){
            Task<GoogleSignInAccount> signedInAccountFromIntent = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignResult(signedInAccountFromIntent);
        }
    }

    private void handleSignResult(Task<GoogleSignInAccount> signedInAccountFromIntent) {
        GoogleSignInAccount result = signedInAccountFromIntent.getResult();
        AuthCredential credential = GoogleAuthProvider.getCredential(result.getIdToken(),null );
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    uid = currentUser.getUid();
                    //TODO: retrieve the corresponding user document from the firestore using the given uid
                    FirebaseFirestore.getInstance().collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot result1 = task.getResult();
                                boolean exists = result1.exists();
                                if (exists){
                                    //redirect to home
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    intent.putExtra(UID,uid);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    //redirect to registration page
                                    Intent intent = new Intent(getApplicationContext(),Registration.class);
                                    intent.putExtra(EMAIL_PASSWORD_LOGIN,false);
                                    intent.putExtra(UID,uid);
                                    startActivity(intent);
                                }

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    //if there is on any user document with the document id equals to uid , then redirect to registration page , else redirect to home page
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private void doLogin(String userEmail, String userpas){
        FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(userEmail,userpas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Something went try wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}