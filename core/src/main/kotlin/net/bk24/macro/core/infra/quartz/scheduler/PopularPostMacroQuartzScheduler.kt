package net.bk24.macro.core.infra.quartz.scheduler

import net.bk24.macro.core.infra.quartz.helper.QuartzHelper.createJobDetail
import net.bk24.macro.core.infra.quartz.helper.QuartzHelper.createTrigger
import net.bk24.macro.core.infra.quartz.job.PostWriterJob
import org.quartz.Scheduler
import org.springframework.stereotype.Component

@Component
class PopularPostMacroQuartzScheduler(private val scheduler: Scheduler) :
    net.bk24.macro.core.domain.business.PopularPostMacroScheduler {
    override fun scheduleJobs() {
        val postWriteJob =
            createJobDetail(PostWriterJob::class.java, "PostWriterJob", "group1")
        val commentWriteJob =
            createJobDetail(net.bk24.macro.core.infra.quartz.job.CommentWriterJob::class.java, "CommentWriterJob", "group2")

        val postWriterJobTrigger = createTrigger(postWriteJob, "PostWriterJobTrigger", "group1")
        val commentWriterJobTrigger =
            createTrigger(commentWriteJob, "CommentWriterJobTrigger", "group2")

        scheduler.scheduleJob(postWriteJob, postWriterJobTrigger)
        scheduler.scheduleJob(commentWriteJob, commentWriterJobTrigger)
        println("Jobs scheduled to run immediately.")
    }
}
