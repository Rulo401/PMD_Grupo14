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
import android.widget.AbsListView;
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
import upm.pmd.grupo14.models.article.ArticleAdapter;
import upm.pmd.grupo14.notifications.NotificationHandler;
import upm.pmd.grupo14.tasks.DownloadArticlesTask;
import upm.pmd.grupo14.util.Utils;

public class MainActivity extends AppCompatActivity {
    public static Activity mainAct;
    public static final int NUM_ARTICLES = 30;
    private ArticleAdapter articleAdapter;
    private int articleIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAct = this;
        articleIndex = 0;
        articleAdapter = new ArticleAdapter();

        //Autologin
        LogContext lc = (LogContext) getApplicationContext();
        if(lc.getLoginToken() == null) lc.setLoginToken(Utils.getUserFromPreferences(mainAct));

        ListView lv = findViewById(R.id.lv_articles);
        lv.setAdapter(articleAdapter);
        //TODO
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

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
                clearAdapter();
                DownloadArticlesTask downloadArticles = (i==0) ? new DownloadArticlesTask(MainActivity.this, articleAdapter) :
                                        new DownloadArticlesTask(MainActivity.this, Category.values()[i-1], articleAdapter);
                downloadArticles.execute(new Integer [] {NUM_ARTICLES, articleIndex});
                articleIndex += NUM_ARTICLES;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                    spCategory.setSelection(0);
                    clearAdapter();
                    DownloadArticlesTask dat = new DownloadArticlesTask(mainAct, articleAdapter);
                    dat.execute(new Integer[]{NUM_ARTICLES, articleIndex});
                    articleIndex += NUM_ARTICLES;
                }
            }
        });


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

    private void clearAdapter(){
        articleAdapter.clearArticles();
        articleIndex = 0;
    }
}