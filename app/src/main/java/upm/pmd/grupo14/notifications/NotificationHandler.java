package upm.pmd.grupo14.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.annotation.RequiresApi;

import upm.pmd.grupo14.MainActivity;
import upm.pmd.grupo14.R;
import upm.pmd.grupo14.models.article.Article;
import upm.pmd.grupo14.util.Utils;

public class NotificationHandler extends ContextWrapper {

    NotificationManager notificationManager;

    //Low channel
    private static final String LOW_PRIORITY_ID = "lowPriorityChannel";

    //High channel
    private static final String HIGH_PRIORITY_ID = "highPriorityChannel";


    public NotificationHandler(Context base) {
        super(base);
        createChannels();
    }

    public NotificationManager getNotificationManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    //TODO icono app y pending intent
    public Notification.Builder createNotification(int num, Article art){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return createNotificationUnderO(num, art.getUpdate_date());
        Notification.Builder notification = new Notification.Builder(this, HIGH_PRIORITY_ID)
                .setSmallIcon(R.drawable.ic_newspaper)
                .addPerson(new Person.Builder().setName(num + getResources().getString(R.string.not_number_articles)).build())
                .setContentTitle(art.getTitle())
                .setContentText(art.getResume())
                .setWhen(Utils.stringDatetoLong(art.getUpdate_date()))
                .setShowWhen(true);
        if(art.getImage().getImg()!=null){
            notification = notification.setLargeIcon(art.getImage().getImg());
        }
        return notification;
    }

    private Notification.Builder createNotificationUnderO(int num, String date){
        return new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.notUnder26_title))
                .setContentText(String.format(getResources().getString(R.string.notUnder26_text),num))
                .setWhen(Utils.stringDatetoLong(date))
                .setShowWhen(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels(){
        NotificationChannel channelLow = new NotificationChannel(LOW_PRIORITY_ID,
                "xD",NotificationManager.IMPORTANCE_LOW);
        NotificationChannel channelHigh = new NotificationChannel(HIGH_PRIORITY_ID,
                getResources().getString(R.string.notChannel_new_article),NotificationManager.IMPORTANCE_HIGH);
        channelHigh.setDescription(getResources().getString(R.string.notChannel_new_article_description));

        getNotificationManager().createNotificationChannel(channelLow);
        getNotificationManager().createNotificationChannel(channelHigh);
    }
}