package net.bk24.macro.broker.application

import net.bk24.macro.broker.application.writer.CommentProvider
import net.bk24.macro.broker.application.writer.PostProvider
import org.springframework.stereotype.Component
import java.time.LocalDateTime

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
@Component
class ScheduledTasks(
    private val commentProvider: CommentProvider,
    private val postProvider: PostProvider,
) {

//    @PostConstruct
    fun execute() {
        if (LocalDateTime.now().minute == 29) {
            postProvider.execute()

            Thread.sleep(30 * 60 * 1000)
            Thread.sleep(20 * 1000)

            postProvider.execute()

            Thread.sleep(1 * 60 * 1000)
            Thread.sleep(20 * 1000)

            commentProvider.execute()

            Thread.sleep(29 * 60 * 1000)
            Thread.sleep(20 * 1000)

            postProvider.execute()

            Thread.sleep(1 * 60 * 1000)
            Thread.sleep(20 * 1000)

            commentProvider.execute()

            // ///

            postProvider.execute()

            Thread.sleep(1 * 60 * 1000)
            Thread.sleep(20 * 1000)

            commentProvider.execute()

            Thread.sleep(29 * 60 * 1000)
            Thread.sleep(20 * 1000)

            postProvider.execute()

            Thread.sleep(1 * 60 * 1000)
            Thread.sleep(20 * 1000)

            commentProvider.execute()

            // /

            postProvider.execute()

            Thread.sleep(1 * 60 * 1000)
            Thread.sleep(20 * 1000)

            commentProvider.execute()

            Thread.sleep(29 * 60 * 1000)
            Thread.sleep(20 * 1000)

            postProvider.execute()

            Thread.sleep(1 * 60 * 1000)
            Thread.sleep(20 * 1000)
            commentProvider.execute()
        }
    }
}
