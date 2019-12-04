package com.videxedge.fbaudemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import static com.videxedge.fbaudemo.FBref.refAuth;
import static com.videxedge.fbaudemo.FBref.refUsers;

/**
 * @author      Albert Levy <albert.school2015@gmail.com>
 * @version     1.6
 * @since		4/12/2019
 * Main registration / login activity
 */
public class MainActivity extends AppCompatActivity {

    EditText eTname, eTphone, eTemail, eTpass;
    CheckBox cBstayconnect;

    String name, phone, email, password, uid;
    User userdb;
    Boolean stayConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTname=(EditText)findViewById(R.id.eTname);
        eTphone=(EditText)findViewById(R.id.eTphone);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTpass=(EditText)findViewById(R.id.eTpass);
        cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);

        stayConnect=false;
    }

    /**
     * On activity start - Checking if user already logged in.
     * If logged in & asked to be remembered - pass on.
     * <p>
     */
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        Boolean isChecked=settings.getBoolean("stayConnect",false);
        Intent si = new Intent(MainActivity.this,Loginok.class);
        if (refAuth.getCurrentUser()!=null && isChecked) {
            stayConnect=true;
            si = new Intent(MainActivity.this, Loginok.class);
            startActivity(si);
        }
    }

    /**
     * On activity pause - If logged in & asked to be remembered - kill activity.
     * <p>
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
    }

    /**
     * Registering to the application
     * Using:   Firebase Auth with email & password
     *          Firebase Realtime database with the object User to the branch Users
     * If register process is Ok saving stay connect status & pass to next activity
     * <p>
     */
    public void register(View view) {
        name=eTname.getText().toString();
        phone=eTphone.getText().toString();
        email=eTemail.getText().toString();
        password=eTpass.getText().toString();

        final ProgressDialog pd=ProgressDialog.show(this,"Register","Registering...",true);
        refAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                            editor.commit();
                            Log.d("MainActivity", "createUserWithEmail:success");
                            FirebaseUser user = refAuth.getCurrentUser();
                            uid = user.getUid();
                            userdb=new User(name,email,phone,uid);
                            refUsers.child(name).setValue(userdb);
                            Toast.makeText(MainActivity.this, "Successful registration", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(MainActivity.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                            else {
                                Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "User create failed.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    /**
     * login to the application
     * Using:   Firebase Auth with email & password
     * If login process is Ok saving stay connect status & pass to next activity
     * <p>
     */
    public void login(View view) {
        email=eTemail.getText().toString();
        password=eTpass.getText().toString();

        final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);
        refAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                            editor.commit();
                            Log.d("MainActivity", "signinUserWithEmail:success");
                            Intent si = new Intent(MainActivity.this,Loginok.class);
                            startActivity(si);
                        } else {
                            Log.d("MainActivity", "signinUserWithEmail:fail");
                            Toast.makeText(MainActivity.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}