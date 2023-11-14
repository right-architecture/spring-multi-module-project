package test.commerce.front.api.me

import commerce.identity.view.UserView
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import test.commerce.AutoParameterizedTest
import test.commerce.front.api.FrontApiTest
import test.commerce.front.api.Email
import test.commerce.front.api.getMe
import test.commerce.front.api.issueToken
import test.commerce.front.api.signup

@FrontApiTest
class Get_specs(@Autowired val client: TestRestTemplate) {

    @AutoParameterizedTest
    fun `올바른 토큰에 대해 200 응답 코드를 반환한다`(
        email: Email,
        password: String
    ) {
        client.signup(email, password)
        val token: String = client.issueToken(email, password)

        val response: ResponseEntity<UserView> = client.getMe(token)

        assertThat(response.statusCode.value()).isEqualTo(200)
    }

    @AutoParameterizedTest
    fun `올바른 토큰에 대해 올바른 사용자 정보를 반환한다`(
        email: Email,
        password: String
    ) {
        client.signup(email, password)
        val token: String = client.issueToken(email, password)

        val response: ResponseEntity<UserView> = client.getMe(token)

        assertThat(response.body).isNotNull
        assertThat(response.body?.email).isEqualTo(email.toString())
    }
}
