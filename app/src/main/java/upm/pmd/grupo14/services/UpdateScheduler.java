package upm.pmd.grupo14.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

/**
 * Updater for job scheduler
 */
public class UpdateScheduler {
    /**
     * Sets a timer for the update of the article downloads
     * @param ctx Enviroment of the scheduler
     */
    public static void schedule(Context ctx){
        ComponentName serviceComponent = new ComponentName(ctx,ArticleUpdateJob.class);
        JobInfo.Builder jobConf = new JobInfo.Builder(0, serviceComponent);
        jobConf.setMinimumLatency(1*60*1000);

        JobScheduler js = ctx.getSystemService(JobScheduler.class);
        js.schedule(jobConf.build());
    }
}
