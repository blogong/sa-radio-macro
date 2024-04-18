package net.bk24.macro.core.infra.quartz.job

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class PostWriterJob : Job {
    override fun execute(context: JobExecutionContext) {
        println("게시글 작성 시작")
    }
}
