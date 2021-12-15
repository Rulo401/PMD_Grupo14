package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.core.text.HtmlCompat;

import upm.pmd.grupo14.ArticleEditActivity;
import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.WebServices;

/**
 * AsyncTask for downloading an article to edit its content.
 */
public class DownloadArticleEditTask extends AsyncTask<String,Void,Article> {
    private Activity act = null;

    /**
     * Constructor.
     * @param act Activity where the task is created.
     */
    public DownloadArticleEditTask(Activity act){
        this.act = act;
    }

    /**
     * Retrieves an article from the server, specified by the article id.
     */
    @Override
    protected Article doInBackground(String... strings) {
        return WebServices.getArticle(strings[0]);
    }

    /**
     * Introduce the article data into its respective fields in the UI.
     */
    @Override
    protected void onPostExecute(Article art){
        super.onPostExecute(art);

        EditText title = act.findViewById(R.id.txt_edit_title);
        title.setText(art.getTitle());
        Spinner categories = act.findViewById(R.id.spn_categories);
        categories.setSelection(art.getCategory().ordinal());
        EditText subtitle = act.findViewById(R.id.txt_edit_subtitle);
        subtitle.setText(art.getSubtitle());
        EditText resume = act.findViewById(R.id.txt_edit_abstract);
        resume.setText(art.getResume()!=null ? HtmlCompat.fromHtml(art.getResume(), HtmlCompat.FROM_HTML_MODE_LEGACY) : "");
        EditText body = act.findViewById(R.id.txt_edit_body);
        body.setText(art.getBody()!=null ? HtmlCompat.fromHtml(art.getBody(), HtmlCompat.FROM_HTML_MODE_LEGACY) : "");
        if(art.getImage().getImg() != null){
            ImageView image = act.findViewById(R.id.img_edit_image);
            ((ArticleEditActivity)act).bitmap = art.getImage().getImg();
            ((ArticleEditActivity)act).media_type = art.getImage().getMedia_type();
            image.setImageBitmap(((ArticleEditActivity)act).bitmap);
        }
    }
}
