package test.commerce.http.api.issue_token

import commerce.http.view.AccessTokenCarrier
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import test.commerce.AutoParameterizedTest
import test.commerce.http.api.AppTest
import test.commerce.http.api.Email
import test.commerce.http.api.issueToken
import test.commerce.http.api.signup

@AppTest
class Post_specs(@Autowired val client: TestRestTemplate) {

    @AutoParameterizedTest
    fun `존재하는 사용자에 대해 200 응답 코드를 반환한다`(
        email: Email,
        password: String
    ) {
        client.signup(email, password)
        val response: ResponseEntity<*> = client.issueToken(email, password)
        assertThat(response.statusCode.value()).isEqualTo(200)
    }

    @AutoParameterizedTest
    fun `존재하는 사용자에 대해 토큰을 발행한다`(email: Email, password: String) {
        client.signup(email, password)

        val response: ResponseEntity<AccessTokenCarrier> =
            client.issueToken(email, password)

        assertThat(response.body).isNotNull
        assertThat(response.body!!.accessToken).isNotNull
    }

    @AutoParameterizedTest
    fun `존재하지 않는 사용자에 대해 400 응답 코드를 반환한다`(
        email: Email,
        password: String
    ) {
        val response: ResponseEntity<*> = client.issueToken(email, password)
        assertThat(response.statusCode.value()).isEqualTo(400)
    }

    @AutoParameterizedTest
    fun `올바르지 않는 비밀번호에 대해 400 응답 코드를 반환한다`(
        email: Email,
        password: String,
        wrongPassword: String
    ) {
        client.signup(email, password)

        val response: ResponseEntity<*> =
            client.issueToken(email, wrongPassword)

        assertThat(response.statusCode.value()).isEqualTo(400)
    }
}
