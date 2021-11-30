package upm.pmd.grupo14.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.models.article.ArticleAdapter;
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
        TextView subtitle = act.findViewById(R.id.txt_subtitle_art);
        subtitle.setText(art.getSubtitle());
        TextView category = act.findViewById(R.id.txt_category_art);
        category.setText(art.getCategory().name());
        TextView resume = act.findViewById(R.id.txt_abstract_art);
        resume.setText(art.getResume());
        TextView body = act.findViewById(R.id.txt_body_art);
        body.setText(art.getBody());
        TextView user = act.findViewById(R.id.txt_user_art);
        user.setText(art.getUsername());
        TextView date = act.findViewById(R.id.txt_date_art);
        date.setText(art.getUpdate_date());
        ImageView image = act.findViewById(R.id.img_image_art);
        image.setImageBitmap(art.getImage().getImg());
    }
}
