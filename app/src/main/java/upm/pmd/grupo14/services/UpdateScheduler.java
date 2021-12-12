package upm.pmd.grupo14.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class UpdateScheduler {
    public static void schedule(Context ctx){
        ComponentName serviceComponent = new ComponentName(ctx,ArticleUpdateJob.class);
        JobInfo.Builder jobConf = new JobInfo.Builder(0, serviceComponent);
        jobConf.setMinimumLatency(3*60*1000);

        JobScheduler js = ctx.getSystemService(JobScheduler.class);
        js.schedule(jobConf.build());
    }
}
