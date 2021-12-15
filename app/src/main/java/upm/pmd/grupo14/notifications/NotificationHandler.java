package upm.pmd.grupo14.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;

import upm.pmd.grupo14.MainActivity;
import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.ImageSerializer;
import upm.pmd.grupo14.util.Utils;

/**
 * Handler for creating and launching notifications.
 */
public class NotificationHandler extends ContextWrapper {

    NotificationManager notificationManager;

    //High priority channel
    private static final String HIGH_PRIORITY_ID = "highPriorityChannel";

    /**
     * Constructor that creates the notification channels.
     * @param base Environment of the call
     */
    public NotificationHandler(Context base) {
        super(base);
        createChannels();
    }

    /**
     * NotificationManager getter.
     * @return NotificationManager
     */
    public NotificationManager getNotificationManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    /**
     * Builder method of a notification.
     * @param num Number of pending articles
     * @param art Last article uploaded
     * @return Notification indicating the number of new articles and some info about the last article
     */
    public Notification.Builder createNotification(int num, Article art){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return createNotificationUnderO(num, art.getUpdate_date());

        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notification = new Notification.Builder(this, HIGH_PRIORITY_ID)
                .setSmallIcon(R.drawable.ic_newspaper)
                .setColor(getColor(R.color.clr_mainApp))
                .setSubText(num + " " + ((num == 1) ? getResources().getString(R.string.not_number_article_just_one) :
                        getResources().getString(R.string.not_number_articles)))
                .setContentTitle(art.getTitle()!= null ? HtmlCompat.fromHtml(art.getTitle(), HtmlCompat.FROM_HTML_MODE_LEGACY) : "")
                .setContentText(art.getResume()!= null ? HtmlCompat.fromHtml(art.getResume().length() < 240 ? art.getResume() : art.getResume().substring(0,241) + "...", HtmlCompat.FROM_HTML_MODE_LEGACY) : "")
                .setWhen(Utils.stringDateToLong(art.getUpdate_date()))
                .setShowWhen(true)
                .setContentIntent(pi);
        if(art.getThumbnail_data()!=null){
            notification = notification.setLargeIcon(ImageSerializer.base64StringToImg(art.getThumbnail_data()));
        }
        return notification;
    }

    private Notification.Builder createNotificationUnderO(int num, String date){
        Intent i = new Intent(this, MainActivity.class);

        PendingIntent pi = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);

        return new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.notUnder26_title))
                .setContentText(String.format(((num == 1) ? getResources().getString(R.string.not_number_article_just_one) :
                        getResources().getString(R.string.not_number_articles)),num))
                .setWhen(Utils.stringDateToLong(date))
                .setShowWhen(true)
                .setContentIntent(pi);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels(){
        NotificationChannel channelHigh = new NotificationChannel(HIGH_PRIORITY_ID,
                getResources().getString(R.string.notChannel_new_article),NotificationManager.IMPORTANCE_HIGH);
        channelHigh.setDescription(getResources().getString(R.string.notChannel_new_article_description));
        getNotificationManager().createNotificationChannel(channelHigh);
    }
}