package test.commerce.front.api.issue_token

import commerce.front.api.view.AccessTokenCarrier
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import test.commerce.AutoParameterizedTest
import test.commerce.front.api.FrontApiTest
import test.commerce.front.api.Email
import test.commerce.front.api.signup

@FrontApiTest
class Post_specs(@Autowired val client: TestRestTemplate) {

    @AutoParameterizedTest
    fun `존재하는 사용자에 대해 200 응답 코드를 반환한다`(
        email: Email,
        password: String
    ) {
        client.signup(email, password)

        val response: ResponseEntity<*> = client.postForEntity(
            "/api/issue-token",
            mapOf(
                "email" to email.toString(),
                "password" to password
            ),
            AccessTokenCarrier::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(200)
    }

    @AutoParameterizedTest
    fun `존재하는 사용자에 대해 토큰을 발행한다`(email: Email, password: String) {
        client.signup(email, password)

        val response: ResponseEntity<AccessTokenCarrier> = client.postForEntity(
            "/api/issue-token",
            mapOf(
                "email" to email.toString(),
                "password" to password
            ),
            AccessTokenCarrier::class.java
        )

        assertThat(response.body).isNotNull
        assertThat(response.body!!.accessToken).isNotNull
    }

    @AutoParameterizedTest
    fun `존재하지 않는 사용자에 대해 400 응답 코드를 반환한다`(
        email: Email,
        password: String
    ) {
        val response: ResponseEntity<*> = client.postForEntity(
            "/api/issue-token",
            mapOf(
                "email" to email.toString(),
                "password" to password
            ),
            AccessTokenCarrier::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }

    @AutoParameterizedTest
    fun `올바르지 않는 비밀번호에 대해 400 응답 코드를 반환한다`(
        email: Email,
        password: String,
        wrongPassword: String
    ) {
        client.signup(email, password)

        val response: ResponseEntity<*> = client.postForEntity(
            "/api/issue-token",
            mapOf(
                "email" to email.toString(),
                "password" to wrongPassword
            ),
            AccessTokenCarrier::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }
}
