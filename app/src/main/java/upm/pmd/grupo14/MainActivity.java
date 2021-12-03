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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.login.LoginToken;
import upm.pmd.grupo14.tasks.DownloadArticlesTask;

public class MainActivity extends AppCompatActivity {
    public static final String ID_ARTICLE = "id";
    public static LoginToken loginToken = null;
    public static Activity mainAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAct = this;

        FloatingActionButton btn_log = findViewById(R.id.fab_log);
        if(loginToken != null){
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logOUT)));
        }else{
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
        }

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginToken == null){
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    loginToken = null;
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

}