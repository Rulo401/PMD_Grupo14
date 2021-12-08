package upm.pmd.grupo14.tasks;

import java.util.List;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.WebServices;

public class NotificationThread implements Runnable{

    private String date;

    //TODO constructor
    @Override
    public void run() {
        List<Article> articles = WebServices.getUpdates(date);
        for (Article art : articles){
            //TODO
        }
    }
}
