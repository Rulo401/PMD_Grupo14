package upm.pmd.grupo14.tasks;

import android.content.Context;

import java.util.List;

import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.notifications.NotificationHandler;
import upm.pmd.grupo14.util.Utils;
import upm.pmd.grupo14.util.WebServices;

/**
 * Thread which implements the notifications of the app
 */
public class NotificationThread implements Runnable{

    private Context ctx;
    private NotificationHandler notificationHandler;

    /**
     * Constructor
     * @param ctx Where is going to take place
     * @param notificationHandler Handler for the Thread
     */
    public NotificationThread(Context ctx, NotificationHandler notificationHandler) {
        this.ctx = ctx;
        this.notificationHandler = notificationHandler;
    }

    /**
     * Executes the getUpdate method from web services since the date saved on preferences, 
     * if the list received is not empty creates a new notification with the number of new articles
     * and the information of the most recent one.
     */
    @Override
    public void run() {
        String[] dates = Utils.getDatesFromPreferences(ctx);
        Utils.saveDateInPreferences(ctx,Utils.getCurrentDate(),true);
        List<Article> articles = WebServices.getUpdates(dates[0]);
        if(articles.size() > 0){
            notificationHandler.getNotificationManager().notify(1,notificationHandler.createNotification(WebServices.getUpdates(dates[1]).size(), articles.get(0)).build());
        }
    }
}
