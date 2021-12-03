package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import upm.pmd.grupo14.R;
import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.ArticleAdapter;
import upm.pmd.grupo14.util.WebServices;

public class DownloadArticlesTask extends AsyncTask<Integer,Void, List<Article>> {

    private Activity act = null;
    private Category cat = null;

    public DownloadArticlesTask(Activity act){ this.act = act; }

    public DownloadArticlesTask(Activity act, Category cat){
        this.act = act;
        this.cat = cat;
    }

    @Override
    protected List<Article> doInBackground(Integer... ints) {
        return (cat == null) ? WebServices.getArticles(ints[0]) :
                WebServices.getArticles(ints[0]).stream().filter( art -> art.getCategory().equals(cat)).collect(Collectors.toList());
    }

    @Override
    protected void onPostExecute(List<Article> articles){
        super.onPostExecute(articles);
        ListView lv = act.findViewById(R.id.lv_articles);
        ArticleAdapter adapter = new ArticleAdapter();
        adapter.addArticles(articles);
        lv.setAdapter(adapter);
    }
}
