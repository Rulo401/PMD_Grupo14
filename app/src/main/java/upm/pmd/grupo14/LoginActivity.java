package upm.pmd.grupo14;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.login.LoginToken;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_sigIn = findViewById(R.id.login);
        btn_sigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username =((EditText) findViewById(R.id.username)).getText().toString();
                String password =((EditText) findViewById(R.id.password)).getText().toString();
                boolean remindMe = ((CheckBox) findViewById(R.id.check_remindMe)).isChecked();

                LoginToken token = new LoginToken(username, password);

                if(token.signIn(LoginActivity.this)){
                    if(remindMe){
                        SharedPreferences pref = MainActivity.mainAct.getSharedPreferences(Constants.PreferenceNames.CONNECTION_FILENAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Constants.PreferenceNames.USERNAME, username);
                        editor.putString(Constants.PreferenceNames.PASSWORD, password);
                        editor.commit();
                    }
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
