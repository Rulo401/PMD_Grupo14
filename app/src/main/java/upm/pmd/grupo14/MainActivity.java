package upm.pmd.grupo14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.login.LoginToken;
import upm.pmd.grupo14.models.login.LoginTokenHolder;
import upm.pmd.grupo14.tasks.DownloadArticlesTask;
import upm.pmd.grupo14.util.Utils;

public class MainActivity extends AppCompatActivity {
    public static final String ID_ARTICLE = "id";
    public static Activity mainAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAct = this;

        LogContext lc = (LogContext) getApplicationContext();

        FloatingActionButton btn_log = findViewById(R.id.fab_log);
        if(lc.getLoginToken() != null){
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logOUT)));
        }else{
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
        }

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lc.getLoginToken() == null){
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    lc.setLoginToken(null);
                    Utils.deleteUserInPreferences(MainActivity.this);
                    btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
                    btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
                }
            }
        });

        DownloadArticlesTask downloadArticles = new DownloadArticlesTask(this);
        downloadArticles.execute(new Integer [] {30});

        ImageButton btn = findViewById(R.id.btn_menu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogContext lc = (LogContext) getApplicationContext();

        Toast.makeText(this, (lc.getLoginToken()!= null)?lc.getLoginToken().getApitoken(): "NULL", Toast.LENGTH_LONG).show();

        FloatingActionButton btn_log = findViewById(R.id.fab_log);
        if(lc.getLoginToken() != null){
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logOUT)));
        }else{
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
        }
    }
}