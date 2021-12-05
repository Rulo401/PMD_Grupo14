package upm.pmd.grupo14.tasks;


import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.WebServices;

public class UploadArticleTask extends AsyncTask<String,Void,Boolean> {

    private Activity act = null;

    public UploadArticleTask(Activity act){
        this.act = act;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Article art = new Article();
        art.setTitle(strings[0]);
        art.setSubtitle(strings[1]);
        art.setResume(strings[2]);
        art.setBody(strings[3]);
        art.setCategory(strings[4]);
        if(strings.length == 6){
            art.setImage_data(strings[5]);
        }
        return WebServices.uploadArticle(art,((LogContext)act.getApplicationContext()).getLoginToken());

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            act.finish();
        }
        else{
            Toast.makeText(act, act.getResources().getText(R.string.upload_error), Toast.LENGTH_LONG).show();
        }
    }
}
