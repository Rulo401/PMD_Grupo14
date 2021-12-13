package upm.pmd.grupo14.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.Utils;

public class NewsDataSource {

    private SQLiteDatabase database;
    private NewsDatabaseHelper helper;
    private String[] columns_list = {NewsDatabaseHelper.COLUMN_ID, NewsDatabaseHelper.COLUMN_ARTICLE_ID,
            NewsDatabaseHelper.COLUMN_TITLE, NewsDatabaseHelper.COLUMN_CATEGORY, NewsDatabaseHelper.COLUMN_RESUME,
            NewsDatabaseHelper.COLUMN_USER, NewsDatabaseHelper.COLUMN_THUMBNAIL};
    private String[] columns_detail = {NewsDatabaseHelper.COLUMN_ID, NewsDatabaseHelper.COLUMN_ARTICLE_ID,
            NewsDatabaseHelper.COLUMN_TITLE, NewsDatabaseHelper.COLUMN_SUBTITLE,
            NewsDatabaseHelper.COLUMN_CATEGORY, NewsDatabaseHelper.COLUMN_RESUME,
            NewsDatabaseHelper.COLUMN_BODY, NewsDatabaseHelper.COLUMN_USER,
            NewsDatabaseHelper.COLUMN_UPDATE,NewsDatabaseHelper.COLUMN_IMAGE,
            NewsDatabaseHelper.COLUMN_IMAGE_MEDIA};

    public NewsDataSource(Context ctx){
        helper = new NewsDatabaseHelper(ctx);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close(){
        database.close();
    }

    public Article getOneArticle(int id){
        Article article = new Article();
        Cursor cursor = database.query(NewsDatabaseHelper.TABLE_ARTICLES, columns_detail,
                NewsDatabaseHelper.COLUMN_ARTICLE_ID + "=" + id, null, null, null, null);
        cursor.moveToFirst();
        article.setId(cursor.getString(1));
        article.setTitle(cursor.getString(2));
        article.setSubtitle(cursor.getString(3));
        article.setCategory(cursor.getString(4));
        article.setResume(cursor.getString(5));
        article.setBody(cursor.getString(6));
        article.setUsername(cursor.getString(7));
        article.setUpdate_date(Utils.longDateToString(cursor.getLong(8)));
        article.setImage_data(cursor.getString(9));
        article.setImage_media_type(cursor.getString(10));
        return article;
    }

    public List<Article> getArticles(int num, int from){
        List<Article> result = new ArrayList<Article>();
        Cursor cursor = database.query(NewsDatabaseHelper.TABLE_ARTICLES, columns_list,
                null,null, null ,null ,NewsDatabaseHelper.COLUMN_UPDATE + " DESC");
        cursor.moveToPosition(from);
        for(int i = 0; i < num; i++){
            result.add(cursorToArticle(cursor));
            cursor.moveToNext();
        }
        return result;
    }

    public void addArticles(List<Article> articles){
        for(Article article: articles){
            addOneArticle(article);
        }
    }

    public void addOneArticle(Article article){
        Cursor cursor = database.query(NewsDatabaseHelper.TABLE_ARTICLES, columns_detail,
                NewsDatabaseHelper.COLUMN_ARTICLE_ID + "=" + article.getId(), null, null, null, null);
        String id = null;
        if(cursor.moveToFirst()){
            id = cursor.getString(0);
        }
        ContentValues values = new ContentValues();
        values.put(NewsDatabaseHelper.COLUMN_ARTICLE_ID, article.getId());
        values.put(NewsDatabaseHelper.COLUMN_TITLE, article.getId());
        values.put(NewsDatabaseHelper.COLUMN_SUBTITLE, article.getSubtitle());
        values.put(NewsDatabaseHelper.COLUMN_CATEGORY, article.getCategory().name());
        values.put(NewsDatabaseHelper.COLUMN_RESUME, article.getResume());
        values.put(NewsDatabaseHelper.COLUMN_BODY, article.getBody());
        values.put(NewsDatabaseHelper.COLUMN_USER, article.getUsername());
        values.put(NewsDatabaseHelper.COLUMN_UPDATE, Utils.stringDateToLong(article.getUpdate_date()));
        if(article.getImage_data() != null){
            values.put(NewsDatabaseHelper.COLUMN_IMAGE, article.getImage_data());
            values.put(NewsDatabaseHelper.COLUMN_IMAGE_MEDIA, article.getImage_media_type());
        }
        if(article.getThumbnail_data() != null){
            values.put(NewsDatabaseHelper.COLUMN_THUMBNAIL, article.getThumbnail_data());
        }
        database.insert(NewsDatabaseHelper.TABLE_ARTICLES, id, values);
    }

    public void deleteArticle(Article article){
        database.delete(NewsDatabaseHelper.TABLE_ARTICLES, NewsDatabaseHelper.COLUMN_ARTICLE_ID + "=" + article.getId(), null);
    }

    private Article cursorToArticle(Cursor cursor){
        Article article = new Article();
        article.setId(cursor.getString(1));
        article.setTitle(cursor.getString(2));
        article.setCategory(cursor.getString(3));
        article.setResume(cursor.getString(4));
        article.setUsername(cursor.getString(5));
        article.setThumbnail_data(cursor.getString(6));
        return article;
    }
}
