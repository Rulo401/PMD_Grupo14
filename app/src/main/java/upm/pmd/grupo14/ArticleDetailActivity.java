package upm.pmd.grupo14;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.tasks.DownloadOneArticleTask;
import upm.pmd.grupo14.util.Utils;

/**
 * Class dedicated to the article detail screen activities 
 */
public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        LogContext lc = (LogContext) getApplicationContext();

        // Swaps log button color and icon if is logged or not
        FloatingActionButton btn_log = findViewById(R.id.fab_log);
        if(lc.getLoginToken() != null && lc.getLoginToken().isLogged()){
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout));
            btn_log.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logOUT)));
        }else{
            btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
            btn_log.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
        }

        // When the log button is clicked
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If isn't logged sends the user to the login screen
                if(lc.getLoginToken() == null){
                    Intent i = new Intent(ArticleDetailActivity.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    // When the log button is clicked logs out and sends the user to the main screen
                    // Also changes color and icon of the button
                    lc.setLoginToken(null);
                    Utils.deleteUserInPreferences(ArticleDetailActivity.this);
                    btn_log.setImageDrawable(getResources().getDrawable(R.drawable.ic_login));
                    btn_log.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.clr_logIN)));
                }
            }
        });

        Intent i = this.getIntent();
        String articleID = i.getStringExtra(Constants.ID_ARTICLE);

        // Creates and executes the task to download and show one article
        DownloadOneArticleTask task = new DownloadOneArticleTask(this);
        task.execute(new String[]{articleID});

    }

}
