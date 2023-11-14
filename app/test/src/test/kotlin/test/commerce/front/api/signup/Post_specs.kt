package test.commerce.front.api.signup

import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import test.commerce.AutoParameterizedTest
import test.commerce.front.api.Email
import test.commerce.front.api.FrontApiTest

@FrontApiTest
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

    @AutoParameterizedTest
    fun `이메일이 설정되지 않으면 400 응답 코드를 반환한다`(password: String) {
        val response: ResponseEntity<*> = client.postForEntity(
            "/api/signup",
            mapOf("password" to password),
            Void::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }

    @AutoParameterizedTest
    fun `비밀번호가 설정되지 않으면 400 응답 코드를 반환한다`(email: Email) {
        val response: ResponseEntity<*> = client.postForEntity(
            "/api/signup",
            mapOf("email" to email.toString()),
            Void::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }
}
