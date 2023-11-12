package test.commerce.http.api.me

import commerce.identity.view.UserView
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import test.commerce.AutoParameterizedTest
import test.commerce.http.api.AppTest
import test.commerce.http.api.Email
import test.commerce.http.api.getMe
import test.commerce.http.api.issueToken
import test.commerce.http.api.signup

@AppTest
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
}
