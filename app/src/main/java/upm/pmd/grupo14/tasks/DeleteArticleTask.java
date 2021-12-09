package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import upm.pmd.grupo14.MainActivity;
import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.ArticleAdapter;
import upm.pmd.grupo14.util.WebServices;


public class DeleteArticleTask extends AsyncTask<Void,Void,Boolean> {

    private Article art;
    private Activity act;
    private ArticleAdapter ad;

    public DeleteArticleTask(Activity act, Article art, ArticleAdapter ad){
        this.act = act;
        this.art = art;
        this.ad = ad;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        LogContext lc = (LogContext) act.getApplicationContext();
        return WebServices.deleteArticle(art,lc.getLoginToken());
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        String toastText = aBoolean ? act.getString(R.string.delete_okay) : act.getString(R.string.delete_wrong);
        Toast.makeText(act,toastText,Toast.LENGTH_SHORT).show();
        if(aBoolean){
            DownloadArticlesTask dat = new DownloadArticlesTask(MainActivity.mainAct);
            dat.execute(MainActivity.NUM_ARTICLES);
        }
    }
}
