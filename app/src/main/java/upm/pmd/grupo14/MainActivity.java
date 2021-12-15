package upm.pmd.grupo14;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.article.ArticleAdapter;
import upm.pmd.grupo14.services.UpdateScheduler;
import upm.pmd.grupo14.tasks.DownloadArticlesTask;
import upm.pmd.grupo14.util.Utils;

public class MainActivity extends AppCompatActivity {
    public static final int NUM_ARTICLES = 20; // Num of articles to be downloaded per connection
    public static Activity mainAct; // Instance
    private ArticleAdapter articleAdapter;
    private int articleIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize attributes
        mainAct = this;
        articleIndex = 0;
        articleAdapter = new ArticleAdapter();

        //Service initialize
        UpdateScheduler.schedule(this);

        //Autologin from preference if remind me
        LogContext lc = (LogContext) getApplicationContext();
        if(lc.getLoginToken() == null) lc.setLoginToken(Utils.getUserFromPreferences(mainAct));

        //Setting the list adapter
        ListView lv = findViewById(R.id.lv_articles);
        lv.setAdapter(articleAdapter);

        //Setting click listener for article create activity
        FloatingActionButton btn_create = findViewById(R.id.fab_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainAct,ArticleEditActivity.class);
                intent.putExtra(Constants.ID_ARTICLE,"");
                startActivity(intent);
            }
        });

        //Setting spinner adapter
        Spinner spCategory=findViewById(R.id.spn_filter);
        spCategory.setVisibility(View.VISIBLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);
        //Spinner item listener
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Clears up the adapter and begin downloading articles when changing selection
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
        clearAdapter();

        //Setting list onScroll listener
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            //If scroll reaches the bottom of the list, download NUM_ARTICLES new articles
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(i2 <= i + i1){
                    DownloadArticlesTask downloadArticles = (spCategory.getSelectedItemPosition()==0) ? new DownloadArticlesTask(MainActivity.this, articleAdapter) :
                            new DownloadArticlesTask(MainActivity.this, Category.values()[spCategory.getSelectedItemPosition()-1], articleAdapter);
                    downloadArticles.execute(new Integer [] {NUM_ARTICLES, articleIndex});
                    articleIndex += NUM_ARTICLES;
                }
            }
        });

        //Setting onClick listener for login and logout
        FloatingActionButton btn_log = findViewById(R.id.fab_log);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If is not logged, launch a Login activity
                //Else, deletes user in preferences and reloads the adapter
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

        //Setting image and background of the log button depending on the log status
        if(lc.getLoginToken() != null && lc.getLoginToken().isLogged()){
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout));
            btn_log.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logOUT)));
        }else{
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
            btn_log.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
        }

        //If user is logged in, shows the create article button
        int visibility = (lc.getLoginToken()==null) ? View.GONE : View.VISIBLE;
        findViewById(R.id.fab_create).setVisibility(visibility);
    }

    private void clearAdapter(){
        articleAdapter.clearArticles();
        articleIndex = 0;
    }
}