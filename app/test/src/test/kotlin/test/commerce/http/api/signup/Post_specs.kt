package test.commerce.http.api.signup

import commerce.http.Application
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import test.commerce.AutoParameterizedTest
import test.commerce.http.api.Email

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [Application::class]
)
class Post_specs(@Autowired val client: TestRestTemplate) {

    @AutoParameterizedTest
    fun `올바른 요청에 대해 200 응답 코드를 반환한다`(
        email: Email,
        password: String
    ) {
        val response: ResponseEntity<*> = client.postForEntity(
            "/api/signup",
            mapOf(
                "email" to email.toString(),
                "password" to password
            ),
            Void::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(200)
    }
}
