package com.videxedge.fbaudemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import static com.videxedge.fbaudemo.FBref.refUsers;

public class MainActivity extends AppCompatActivity {

    EditText eTphone, eTemail, eTpass;
    CheckBox cBstayconnect;
    String phone, email, password;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTphone=(EditText)findViewById(R.id.eTphone);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTpass=(EditText)findViewById(R.id.eTpass);
        cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);

    }

    public void register(View view) {
        phone=eTphone.getText().toString();
        email=eTemail.getText().toString();
        password=eTpass.getText().toString();
        User user=new User(email,phone);
        refUsers.child(email.replace("."," ")).setValue(user);

    }
}
