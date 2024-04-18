package net.bk24.macro.infra.quartz.scheduler

import net.bk24.macro.core.business.PopularPostMacroScheduler
import net.bk24.macro.infra.quartz.helper.QuartzHelper.createJobDetail
import net.bk24.macro.infra.quartz.helper.QuartzHelper.createTrigger
import net.bk24.macro.infra.quartz.job.CommentWriterJob
import net.bk24.macro.infra.quartz.job.PostWriterJob
import org.quartz.Scheduler
import org.springframework.stereotype.Component

@Component
class PopularPostMacroQuartzScheduler(private val scheduler: Scheduler) :
     PopularPostMacroScheduler {
     override fun scheduleJobs() {
          val postWriteJob =
               createJobDetail(PostWriterJob::class.java, "PostWriterJob", "group1")
          val commentWriteJob =
               createJobDetail(CommentWriterJob::class.java, "CommentWriterJob", "group2")

          val postWriterJobTrigger = createTrigger(postWriteJob, "PostWriterJobTrigger", "group1")
          val commentWriterJobTrigger =
               createTrigger(commentWriteJob, "CommentWriterJobTrigger", "group2")

          scheduler.scheduleJob(postWriteJob, postWriterJobTrigger)
          scheduler.scheduleJob(commentWriteJob, commentWriterJobTrigger)
          println("Jobs scheduled to run immediately.")
     }
}