package upm.pmd.grupo14.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import upm.pmd.grupo14.notifications.NotificationHandler;
import upm.pmd.grupo14.tasks.NotificationThread;

/**
 * Job to update an article
 */
public class ArticleUpdateJob extends JobService {
    /**
     * Starts a thread with a notification thread which carries a notification handler
     * @param jobParameters Runtime parameters
     * @return Exit status
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Thread th = new Thread(new NotificationThread(this, new NotificationHandler(this)));
        th.start();
        try { th.join(); } catch (InterruptedException e) { }
        UpdateScheduler.schedule(this);
        return false;
    }

    /**
     * @param jobParameters Runtime parameters
     * @return Exit status
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
