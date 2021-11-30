package upm.pmd.grupo14;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.tasks.DownloadArticlesTask;

public class MainActivity extends AppCompatActivity {
    public static final String ID_ARTICLE = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadArticlesTask downloadArticles = new DownloadArticlesTask(this);
        downloadArticles.execute(new Integer [] {1});
    }
}