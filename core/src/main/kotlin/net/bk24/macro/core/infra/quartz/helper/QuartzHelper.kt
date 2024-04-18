package net.bk24.macro.core.infra.quartz.helper

import org.quartz.*
import java.util.*

object QuartzHelper {
    fun <T : Job> createJobDetail(
        jobClass: Class<T>,
        jobName: String,
        groupName: String,
    ): JobDetail {
        return JobBuilder.newJob(jobClass)
            .withIdentity(jobName + "-${UUID.randomUUID()}", groupName)
            .build()
    }

    fun createTrigger(
        jobDetail: JobDetail,
        triggerName: String,
        groupName: String,
    ): SimpleTrigger {
        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity(triggerName + "-${UUID.randomUUID()}", groupName)
            .startNow()
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow(),
            )
            .build()
    }
}
