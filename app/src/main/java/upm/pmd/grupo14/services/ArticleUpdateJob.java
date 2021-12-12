package upm.pmd.grupo14.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import upm.pmd.grupo14.notifications.NotificationHandler;
import upm.pmd.grupo14.tasks.NotificationThread;
import upm.pmd.grupo14.util.Utils;

public class ArticleUpdateJob extends JobService {
    public static String lastUpdate;
    public static String lastAccess;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Thread th = new Thread(new NotificationThread(new NotificationHandler(this), lastUpdate, lastAccess));
        th.start();
        try { th.join(); } catch (InterruptedException e) { }
        lastUpdate = Utils.getCurrentDate();
        UpdateScheduler.schedule(this);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
