package upm.pmd.grupo14.tasks;

import java.util.List;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.notifications.NotificationHandler;
import upm.pmd.grupo14.util.WebServices;

public class NotificationThread implements Runnable{

    private NotificationHandler notificationHandler;
    private String lastUpdate;
    private String lastAccess;

    public NotificationThread(NotificationHandler notificationHandler, String lastUpdate, String lastAccess) {
        this.notificationHandler = notificationHandler;
        this.lastUpdate = lastUpdate;
        this.lastAccess = lastAccess;
    }

    @Override
    public void run() {
        List<Article> articles = WebServices.getUpdates(lastUpdate);
        if(articles.size() > 0){
            notificationHandler.getNotificationManager().notify(1,notificationHandler.createNotification(WebServices.getUpdates(lastAccess).size(), articles.get(0)).build());
        }
    }
}
