package net.bk24.macro.core.application

import net.bk24.macro.core.application.timer.TimeProvider
import net.bk24.macro.core.application.writer.CommentProvider
import net.bk24.macro.core.application.writer.PostProvider
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.Date

/**
 * 01 : 29분 게시물 작성
 * ㅡㅡㅡㅡ루프ㅡㅡㅡㅡ
 * (1)01 : 59분 게시물 작성
 * (2)02 : 00분 댓글작성 { 첫번째 포스트 }
 * (3)02 : 29분 게시물 작성
 * (4)02 : 30분 댓글작성 { 두번째 포스트 }
 * --------------
 * (1)02 : 59분 게시물 작성
 * (2)03 : 00분 댓글작성 { 세번째 포스트 }
 * (3)03 : 29분 게시물 작성
 * (4)03 : 30분 댓글작성 { 네번째 포스트 }
 * 반복
 */
@Profile("live")
@Component
class FamousMaker(
    private val timeProvider: TimeProvider,
    private val commentProvider: CommentProvider,
    private val postProvider: PostProvider,
) {

    enum class Type {
        POST,
        COMMENT,
    }

    fun execute() {
        while (true) {
            commentProvider.execute()
            val runType = timeProvider.isTimeToRun(Date()) ?: continue

            when (runType) {
                Type.COMMENT -> commentProvider.execute()
                Type.POST -> postProvider.execute()
            }
            Thread.sleep(5000)
        }
    }
}
