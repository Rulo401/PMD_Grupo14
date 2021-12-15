package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.List;
import java.util.stream.Collectors;

import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.ArticleAdapter;
import upm.pmd.grupo14.util.Utils;
import upm.pmd.grupo14.util.WebServices;

/**
 * Task to download articles
 */
public class DownloadArticlesTask extends AsyncTask<Integer,Void, List<Article>> {

    private Activity act = null;
    private Category cat = null;
    private ArticleAdapter articleAdapter;

    /**
     * Constructor without category
     * @param act Where is going to take place
     * @param articleAdapter Adapter the task is going to use
     */
    public DownloadArticlesTask(Activity act, ArticleAdapter articleAdapter){
        this.act = act;
        this.articleAdapter = articleAdapter;
    }

    /**
     * Constructor for a certain category
     * @param act Where is going to take place
     * @param cat Category for the articles
     * @param articleAdapter Adapter the task is going to use
     */
    public DownloadArticlesTask(Activity act, Category cat, ArticleAdapter articleAdapter){
        this.act = act;
        this.cat = cat;
        this.articleAdapter = articleAdapter;
    }

    /**
     * Asks the web services to get articles, saves the date in preferences and filters them by category if needed
     */
    @Override
    protected List<Article> doInBackground(Integer... ints) {
        if(cat == null){
            List<Article> result = WebServices.getArticles(ints[0], ints[1]);
            if(ints[1] == 0){
                Utils.saveDateInPreferences(act, result.get(0).getUpdate_date(), false);
            }
            return result;
        }
        return WebServices.getArticles(ints[0],ints[1]).stream().filter( art -> art.getCategory().equals(cat)).collect(Collectors.toList());
    }

    /**
     * Adds articles to the adapter
     */
    @Override
    protected void onPostExecute(List<Article> articles){
        super.onPostExecute(articles);
        articleAdapter.addArticles(articles);
    }
}
