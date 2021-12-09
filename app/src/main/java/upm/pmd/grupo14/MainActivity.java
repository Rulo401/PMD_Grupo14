package upm.pmd.grupo14;

import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.notifications.NotificationHandler;
import upm.pmd.grupo14.tasks.DownloadArticlesTask;
import upm.pmd.grupo14.util.Utils;

public class MainActivity extends AppCompatActivity {
    public static Activity mainAct;
    public static final int NUM_ARTICLES = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAct = this;

        //Autologin
        LogContext lc = (LogContext) getApplicationContext();
        if(lc.getLoginToken() == null) lc.setLoginToken(Utils.getUserFromPreferences(mainAct));

        FloatingActionButton btn_log = findViewById(R.id.fab_log);
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
                    findViewById(R.id.fab_create).setVisibility(View.GONE);
                    DownloadArticlesTask dat = new DownloadArticlesTask(mainAct);
                    dat.execute(NUM_ARTICLES);
                }
            }
        });

        FloatingActionButton btn_create = findViewById(R.id.fab_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainAct,ArticleEditActivity.class);
                intent.putExtra(Constants.ID_ARTICLE,"");
                startActivity(intent);
            }
        });

        Spinner spCategory=findViewById(R.id.spn_filter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    DownloadArticlesTask downloadArticles = new DownloadArticlesTask(MainActivity.this);
                    downloadArticles.execute(new Integer [] {NUM_ARTICLES});
                }else{
                    DownloadArticlesTask downloadArticles = new DownloadArticlesTask(MainActivity.this, Category.values()[i-1]);
                    downloadArticles.execute(new Integer [] {NUM_ARTICLES});
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        

        DownloadArticlesTask downloadArticles = new DownloadArticlesTask(this);
        downloadArticles.execute(new Integer [] {NUM_ARTICLES});
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogContext lc = (LogContext) getApplicationContext();

        //TODO
        Toast.makeText(this, (lc.getLoginToken()!= null)?lc.getLoginToken().getApitoken(): "NULL", Toast.LENGTH_LONG).show();

        FloatingActionButton btn_log = findViewById(R.id.fab_log);
        if(lc.getLoginToken() != null){
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logOUT)));
        }else{
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
        }
        int visibility = (lc.getLoginToken()==null) ? View.GONE : View.VISIBLE;
        findViewById(R.id.fab_create).setVisibility(visibility);
    }
}