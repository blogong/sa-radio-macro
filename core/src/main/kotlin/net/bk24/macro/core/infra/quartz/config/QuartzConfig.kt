package net.bk24.macro.core.infra.quartz.config

import net.bk24.macro.core.infra.quartz.job.PostWriterJob
import org.quartz.JobBuilder.newJob
import org.quartz.JobDetail
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzConfig {
    @Bean
    fun commentWriterJobDetail(): JobDetail {
        return newJob(net.bk24.macro.core.infra.quartz.job.CommentWriterJob::class.java)
            .withIdentity("commentWriterJob")
            .storeDurably()
            .build()
    }

    @Bean
    fun postWriterJobDetail(): JobDetail {
        return newJob(PostWriterJob::class.java)
            .withIdentity("postWriterJob")
            .storeDurably()
            .build()
    }
}
