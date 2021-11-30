package upm.pmd.grupo14;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.tasks.DownloadOneArticleTask;
import upm.pmd.grupo14.util.WebServices;

public class ArticleDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent i = this.getIntent();
        String articleID = i.getStringExtra(MainActivity.ID_ARTICLE);

        DownloadOneArticleTask task = new DownloadOneArticleTask(this);
        task.execute(new String[]{articleID});

    }

}
