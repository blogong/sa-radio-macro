
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

object GetNext5PostNo {

    fun execute(postIds: List<String>): List<List<Int>> {
        val restTemplate = RestTemplate()

        val objectMapper = ObjectMapper().registerKotlinModule()

        return postIds.map { postId ->
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_FORM_URLENCODED
                accept = listOf(MediaType.ALL)
                add("Cookie", "ASP.NET_SessionId=huxwskhkhqobcansnll3g5e4")
                add("Connection", "keep-alive")
                add("Accept-Encoding", "gzip, deflate, br")
                add("Content-Length", "83")
                add("User-Agent", "SuddenRadio/4.2.4 (iPhone; iOS 17.4.1; Scale/3.00)")
                add("Host", "saradioapi.nexon.com")
                add("Accept-Language", "ko-KR;q=1, en-KR;q=0.9")
            }

            val formParams = LinkedMultiValueMap<String, String>().apply {
                add("currentPage", "1")
                add("pageSize", "5") // Change pageSize to get 5 posts
                add("select_type", "user")
                add("tag", "")
                add("user_nexon_sn", postId)
                add("user_type", "")
            }

            val request = HttpEntity(formParams, headers)

            val response = restTemplate.exchange(
                "https://saradioapi.nexon.com/Sns/PostList.aspx",
                HttpMethod.POST,
                request,
                String::class.java,
            ).body ?: return@map emptyList()

            val rootNode = objectMapper.readTree(response)
            val dataset = rootNode.path("Dataset")

            val postNoList = mutableListOf<Int>()
            if (dataset.isArray) {
                var postNo = 0
                for (i in 0 until dataset.size()) {
                    if (i == 0) {
                        postNo = dataset[i].path("post_no").asInt()
                        postNoList.add(postNo)
                    } else {
                        postNo += 1
                        postNoList.add(postNo)
                    }
                }
            }
            postNoList
        }
    }
}

fun main() {
    val postIds = listOf("1292229646", "1234567890", "9876543210", "5432167890", "0987654321")
    val postNoLists = GetNext5PostNo.execute(postIds)
    postNoLists.forEachIndexed { index, postNoList ->
        println("Post ID: ${postIds[index]}, Post Numbers: $postNoList")
    }
}
