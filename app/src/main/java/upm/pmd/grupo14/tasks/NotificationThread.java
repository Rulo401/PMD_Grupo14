package upm.pmd.grupo14.tasks;

import java.util.List;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.notifications.NotificationHandler;
import upm.pmd.grupo14.util.WebServices;

public class NotificationThread implements Runnable{

    private NotificationHandler notificationHandler;
    private String date;

    public NotificationThread(NotificationHandler notificationHandler, String date) {
        this.notificationHandler = notificationHandler;
        this.date = date;
    }

    //TODO id
    @Override
    public void run() {
        List<Article> articles = WebServices.getUpdates(date);
        if(articles.size() > 0){
            notificationHandler.getNotificationManager().notify(1,notificationHandler.createNotification(articles.size(), articles.get(0)).build());
        }
    }
}
