package com.videxedge.fbaudemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.videxedge.fbaudemo.FBref.refAuth;
import static com.videxedge.fbaudemo.FBref.refUsers;

public class Loginok extends AppCompatActivity {

    String name, email, uid;
    TextView tVnameview, tVemailview, tVuidview;
    CheckBox cBconnectview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginok);

        tVnameview=(TextView)findViewById(R.id.tVnameview);
        tVemailview=(TextView)findViewById(R.id.tVemailview);
        tVuidview=(TextView)findViewById(R.id.tVuidview);
        cBconnectview=(CheckBox)findViewById(R.id.cBconnectview);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = refAuth.getCurrentUser();
        name = user.getDisplayName();
        tVnameview.setText(name);
        email = user.getEmail();
        tVemailview.setText(email);
        uid = user.getUid();
        tVuidview.setText(uid);
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",0);
        Boolean isChecked=settings.getBoolean("stayConnect",false);
        cBconnectview.setChecked(isChecked);
    }

    public void update(View view) {
        FirebaseUser user = refAuth.getCurrentUser();
        if (!cBconnectview.isChecked()){
            refAuth.signOut();
        }
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putBoolean("stayConnect",cBconnectview.isChecked());
        editor.commit();
        finish();
    }
}
