package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.ArticleAdapter;
import upm.pmd.grupo14.util.WebServices;

/**
 * The task that is responsable of deleting an article of the server and removing it from the list view
 */
public class DeleteArticleTask extends AsyncTask<Void,Void,Boolean> {

    private Article art;
    private Activity act;
    private ArticleAdapter ad;
    /**
     * Constructor
     * @param act the Activity that invoke this task
     * @param art the Article to delete from the server
     * @param ad  the Adapter that contains the list of the Articles
     */
    public DeleteArticleTask(Activity act, Article art, ArticleAdapter ad){
        this.act = act;
        this.art = art;
        this.ad = ad;
    }
    /**
     * deletes the Article art from the server if the user is logged
     * and return true if the article was deleted from the server and false otherwise
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        LogContext lc = (LogContext) act.getApplicationContext();
        return WebServices.deleteArticle(art,lc.getLoginToken());
    }

    /**
     * shows a Toast with a successful message if the server deleted the Article and a wrong
     * message if the server couldn't delete it
     */
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        String toastText = aBoolean ? act.getString(R.string.delete_okay) : act.getString(R.string.delete_wrong);
        Toast.makeText(act,toastText,Toast.LENGTH_SHORT).show();
        if(aBoolean){
            ad.deleteArticle(art);
        }
    }
}
