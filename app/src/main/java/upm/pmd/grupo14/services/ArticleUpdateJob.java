package upm.pmd.grupo14.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import upm.pmd.grupo14.tasks.NotificationThread;
import upm.pmd.grupo14.util.Utils;

public class ArticleUpdateJob extends JobService {
    private static String lastUpdate = Utils.getCurrentDate();
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Thread th = new Thread(new NotificationThread());
        th.start();
        try { th.join(); } catch (InterruptedException e) { }
        UpdateScheduler.schedule(this);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
