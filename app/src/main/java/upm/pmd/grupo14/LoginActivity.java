package upm.pmd.grupo14;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.login.LoginToken;
import upm.pmd.grupo14.util.Utils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Sig-in button
        Button btn_sigIn = findViewById(R.id.login);
        btn_sigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Credentials
                String username = ((EditText) findViewById(R.id.username)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                //Remind me check box
                boolean remindMe = ((CheckBox) findViewById(R.id.check_remindMe)).isChecked();
                //The login token
                LoginToken token = new LoginToken(username, null);

                //if the user could sig in, then save the Login Token in the LogContext
                //otherwise shows a Toast with invalid credentials
                if(token.signIn(LoginActivity.this, password)){
                    ((LogContext) getApplicationContext()).setLoginToken(token);
                    //if the check box is marked, save in preferences the username and api token
                    if(remindMe){
                        Utils.saveUserInPreferences(LoginActivity.this,username, token.getApitoken());
                    }
                    //creates the intent and move to the main MainActivity
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
