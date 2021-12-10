package upm.pmd.grupo14.models.article;

import android.content.Context;
import android.content.Intent;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import java.util.LinkedList;
import java.util.List;

import upm.pmd.grupo14.ArticleDetailActivity;
import upm.pmd.grupo14.ArticleEditActivity;
import upm.pmd.grupo14.MainActivity;
import upm.pmd.grupo14.R;
import upm.pmd.grupo14.common.Category;
import upm.pmd.grupo14.common.Constants;
import upm.pmd.grupo14.models.appContext.LogContext;
import upm.pmd.grupo14.models.login.LoginToken;
import upm.pmd.grupo14.tasks.DeleteArticleTask;
import upm.pmd.grupo14.tasks.DownloadArticlesTask;

public class ArticleAdapter extends BaseAdapter {

    List<Article> articles = new LinkedList<Article>();


    public void addArticles(List<Article> newArticles){
        articles.addAll(newArticles);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int i) {
        return articles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null){
            view = inflater.inflate(R.layout.article_row_layout,null);
        }
        Article art = articles.get(i);
        //Fill article info
        ((TextView)view.findViewById(R.id.txt_category)).setText(art.getCategory().show(MainActivity.mainAct));
        ((TextView)view.findViewById(R.id.txt_title)).setText(art.getTitle());
        ((TextView)view.findViewById(R.id.txt_abstract)).setText(art.getResume()!= null ? HtmlCompat.fromHtml(art.getResume(), HtmlCompat.FROM_HTML_MODE_LEGACY) : "");
        ((ImageView)view.findViewById(R.id.img_thumbnail)).setImageBitmap(art.getThumbnail().getImg());
        //If user is logged and the article is owned by the user, show buttons
        LoginToken lt = ((LogContext)MainActivity.mainAct.getApplicationContext()).getLoginToken();
        if(lt!=null && art.getUsername().equals(lt.getUsername())){
            view.findViewById(R.id.lay_loggedActions).setVisibility(View.VISIBLE);
            LinearLayout lay_confirmation = view.findViewById(R.id.lay_confirmation);
            //Confirmation button deletes the article.
            Button btn_confirmation = view.findViewById(R.id.btn_confirmation);
            btn_confirmation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteArticleTask dat = new DeleteArticleTask(MainActivity.mainAct,art,ArticleAdapter.this);
                    dat.execute();
                }
            });
            //Cancel button stops cancel.
            Button btn_cancel = view.findViewById(R.id.btn_cancel_del);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lay_confirmation.setVisibility(View.GONE);
                }
            });


            //Edit button throws an ArticleEditActivity
            Button btn_edit = view.findViewById(R.id.btn_edit);
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(viewGroup.getContext(), ArticleEditActivity.class);
                    in.putExtra(Constants.ID_ARTICLE,art.getId());
                    view.getContext().startActivity(in);
                }
            });

            //Delete button show the confirmation button
            Button btn_delete = view.findViewById(R.id.btn_delete);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lay_confirmation.setVisibility(View.VISIBLE);
                }
            });
        }else{
            view.findViewById(R.id.lay_loggedActions).setVisibility(View.GONE);
            view.findViewById(R.id.lay_confirmation).setVisibility(View.GONE);
        }

        //Click on the article view to see the article details
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(viewGroup.getContext(), ArticleDetailActivity.class);
                in.putExtra(Constants.ID_ARTICLE,art.getId());

                viewGroup.getContext().startActivity(in);
            }
        });
        return view;
    }
}
