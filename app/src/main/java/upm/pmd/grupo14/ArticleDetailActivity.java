package upm.pmd.grupo14;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.tasks.DownloadOneArticleTask;
import upm.pmd.grupo14.util.WebServices;

public class ArticleDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        FloatingActionButton btn_log = findViewById(R.id.fab_log);
        if(MainActivity.loginToken != null){
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logOUT)));
        }else{
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
            btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
        }

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.loginToken == null){
                    Intent i = new Intent(ArticleDetailActivity.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    MainActivity.loginToken = null;
                    btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
                    btn_log.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
                }
            }
        });

        Intent i = this.getIntent();
        String articleID = i.getStringExtra(MainActivity.ID_ARTICLE);

        DownloadOneArticleTask task = new DownloadOneArticleTask(this);
        task.execute(new String[]{articleID});

    }

}
