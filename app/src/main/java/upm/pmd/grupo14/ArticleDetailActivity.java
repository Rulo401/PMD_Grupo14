package upm.pmd.grupo14;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.WebServices;

public class ArticleDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent i = this.getIntent();
        String articleID = i.getStringExtra(MainActivity.ID_ARTICLE);
        Article art = WebServices.getArticle(articleID);

        TextView title = findViewById(R.id.txt_title_art);
        title.setText(art.getTitle());
        TextView subtitle = findViewById(R.id.txt_subtitle_art);
        subtitle.setText(art.getSubtitle());
        TextView category = findViewById(R.id.txt_category_art);
        category.setText(art.getCategory().name());
        TextView resume = findViewById(R.id.txt_abstract_art);
        resume.setText(art.getResume());
        TextView body = findViewById(R.id.txt_body_art);
        body.setText(art.getBody());
        TextView user = findViewById(R.id.txt_user_art);
        user.setText(art.getUsername());
        TextView date = findViewById(R.id.txt_date_art);
        date.setText(art.getUpdate_date());
        ImageView image = findViewById(R.id.img_image_art);
        image.setImageBitmap(art.getImage().getImg());


    }

}
