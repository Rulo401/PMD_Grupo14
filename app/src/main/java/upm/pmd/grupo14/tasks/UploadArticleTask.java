package upm.pmd.grupo14.tasks;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import upm.pmd.grupo14.MainActivity;
import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.WebServices;

/**
 * AsyncTask for uploading an article to the server.
 */
public class UploadArticleTask extends AsyncTask<String,Void,Boolean> {

    private Activity act = null;
    private String id;

    /**
     * Constructor
     * @param act Activity that creates the task.
     * @param id Article id
     */
    public UploadArticleTask(Activity act, String id){
        this.act = act;
        this.id = id;
    }

    /**
     * Builds an article with the data receive as parameters and then upload it to the server.
     * @return If the upload process was successfully
     */
    @Override
    protected Boolean doInBackground(String... strings) {
        Article art = new Article();
        art.setTitle(strings[0]);
        art.setSubtitle(strings[1]);
        art.setResume(strings[2]);
        art.setBody(strings[3]);
        art.setCategory(strings[4]);
        if(strings.length == 7){
            art.setImage_data(strings[5]);
            art.setImage_media_type(strings[6]);
        }else{
            art.setImage_data("");
            art.setImage_media_type("");
        }
        if (!id.equals("")) art.setId(id);
        return WebServices.uploadArticle(art,((LogContext)act.getApplicationContext()).getLoginToken());

    }

    /**
     * Gives feedback to the user if the upload process could not be done, otherwise it returns
     * to the main activity.
     */
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            Intent i = new Intent(act, MainActivity.class);
            act.startActivity(i);
        }
        else{
            Toast.makeText(act, act.getResources().getText(R.string.upload_error), Toast.LENGTH_LONG).show();
        }
    }
}
