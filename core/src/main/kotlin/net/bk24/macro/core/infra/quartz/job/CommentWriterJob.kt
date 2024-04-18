package net.bk24.macro.core.infra.quartz.job

import org.quartz.Job
import org.quartz.JobExecutionContext

class CommentWriterJob : Job {
    override fun execute(context: JobExecutionContext) {
        println("Executing Comment Job with Kotlin")
    }
}
