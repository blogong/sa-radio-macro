package net.bk24.macro.infra.clients

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import net.bk24.macro.core.clients.CommentWriter
import net.bk24.macro.infra.clients.WebClientHelper.Companion.createFormData
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class CommentClient(
     private val webClient: WebClient,
     private val webClientHelper: WebClientHelper
) : CommentWriter {
     private suspend fun makeRequest(
          url: String,
          authCode: String
     ): String {
          val formData = createFormData(authCode)
          return webClient.post()
               .uri(url)
               .headers { headers -> headers.addAll(webClientHelper.setupHeaders()) }
               .body(formData)
               .retrieve()
               .awaitBody<String>()
     }

     override suspend fun execute(
          authCodes: List<String>,
          concurrency: Int
     ) {
          coroutineScope {
               val maxConcurrency = concurrency // 동시에 실행할 최대 작업 수
               val semaphore = Semaphore(maxConcurrency)

               // 각 요청에 대한 비동기 작업을 생성
               val deferredResponses =
                    authCodes.map { code ->
                         async(Dispatchers.IO) {
                              semaphore.withPermit {
                                   try {
                                        makeRequest("/Sns/PostCommentCreate.aspx", code)
                                   } catch (e: Exception) {
                                        "Request failed for authCode $code: ${e.message}"
                                   }
                              }
                         }
                    }

               // 모든 비동기 작업이 완료될 때까지 대기
               val results = deferredResponses.awaitAll()

               // 결과 처리
               results.forEach { result ->
                    println(result)
               }
          }
     }
}