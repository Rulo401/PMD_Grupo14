package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.List;

import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.ArticleAdapter;
import upm.pmd.grupo14.util.WebServices;

public class DownloadArticlesTask extends AsyncTask<Integer,Void, List<Article>> {

    private Activity act = null;

    public DownloadArticlesTask(Activity act){
        this.act = act;
    }

    @Override
    protected List<Article> doInBackground(Integer... ints) {
        return WebServices.getArticles(ints[0]);
    }

    @Override
    protected void onPostExecute(List<Article> articles){
        super.onPostExecute(articles);
        ListView lv = act.findViewById(R.id.lv_articles);
        ArticleAdapter adapter = new ArticleAdapter();
        lv.setAdapter(adapter);
    }
}
