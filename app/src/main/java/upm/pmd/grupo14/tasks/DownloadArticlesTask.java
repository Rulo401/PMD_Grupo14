package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import upm.pmd.grupo14.R;
import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.ArticleAdapter;
import upm.pmd.grupo14.services.ArticleUpdateJob;
import upm.pmd.grupo14.util.Utils;
import upm.pmd.grupo14.util.WebServices;

public class DownloadArticlesTask extends AsyncTask<Integer,Void, List<Article>> {

    private Activity act = null;
    private Category cat = null;
    private ArticleAdapter articleAdapter;

    public DownloadArticlesTask(Activity act, ArticleAdapter articleAdapter){
        this.act = act;
        this.articleAdapter = articleAdapter;
    }

    public DownloadArticlesTask(Activity act, Category cat, ArticleAdapter articleAdapter){
        this.act = act;
        this.cat = cat;
        this.articleAdapter = articleAdapter;
    }

    @Override
    protected List<Article> doInBackground(Integer... ints) {
        ArticleUpdateJob.lastUpdate = Utils.getCurrentDate();
        return (cat == null) ? WebServices.getArticles(ints[0], ints[1]) :
                WebServices.getArticles(ints[0],ints[1]).stream().filter( art -> art.getCategory().equals(cat)).collect(Collectors.toList());
    }

    @Override
    protected void onPostExecute(List<Article> articles){
        super.onPostExecute(articles);
        articleAdapter.addArticles(articles);
    }
}
