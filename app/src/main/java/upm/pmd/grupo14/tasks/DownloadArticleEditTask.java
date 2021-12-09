package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.core.text.HtmlCompat;

import java.util.Locale;

import upm.pmd.grupo14.ArticleEditActivity;
import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.WebServices;

public class DownloadArticleEditTask extends AsyncTask<String,Void,Article> {
    private Activity act = null;

    public DownloadArticleEditTask(Activity act){
        this.act = act;
    }

    @Override
    protected Article doInBackground(String... strings) {
        return WebServices.getArticle(strings[0]);
    }

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
        resume.setText(art.getResume());
        EditText body = act.findViewById(R.id.txt_edit_body);
        body.setText(HtmlCompat.fromHtml(art.getBody(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        ImageView image = act.findViewById(R.id.img_edit_image);
        ((ArticleEditActivity)act).bitmap = art.getImage().getImg();
        ((ArticleEditActivity)act).media_type = art.getImage().getMedia_type();
        image.setImageBitmap(((ArticleEditActivity)act).bitmap);
    }
}
