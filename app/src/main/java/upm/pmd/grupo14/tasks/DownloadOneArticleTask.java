package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import java.util.Locale;

import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.WebServices;

public class DownloadOneArticleTask extends AsyncTask<String,Void,Article> {

    private Activity act = null;

    public DownloadOneArticleTask(Activity act){
        this.act = act;
    }

    @Override
    protected Article doInBackground(String... strings) {
        return WebServices.getArticle(strings[0]);
    }

    @Override
    protected void onPostExecute(Article art){
        super.onPostExecute(art);

        TextView title = act.findViewById(R.id.txt_title_art);
        title.setText(art.getTitle());
        TextView catSub = act.findViewById(R.id.txt_CatSub);
        catSub.setText(art.getCategory().name().toUpperCase(Locale.ROOT) + " - " +art.getSubtitle());
        TextView resume = act.findViewById(R.id.txt_abstract_art);
        resume.setText(art.getResume());
        TextView body = act.findViewById(R.id.txt_body_art);
        body.setText(HtmlCompat.fromHtml(art.getBody(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        TextView user = act.findViewById(R.id.txt_user_art);
        user.setText(art.getUsername());
        TextView date = act.findViewById(R.id.txt_date_art);
        date.setText(art.getUpdate_date());
        ImageView image = act.findViewById(R.id.img_image_art);
        image.setImageBitmap(art.getImage().getImg());
    }
}