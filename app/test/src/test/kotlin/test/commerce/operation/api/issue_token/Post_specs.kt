package test.commerce.operation.api.issue_token

import commerce.operation.api.view.AccessTokenCarrier
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import test.commerce.AutoParameterizedTest
import test.commerce.operation.api.OperationApiTest
import test.commerce.operation.api.registerOperator

@OperationApiTest
class Post_specs(@Autowired val client: TestRestTemplate) {

    @AutoParameterizedTest
    fun `잘못된 비밀번호에 대해 400 응답 코드를 반환한다`(
        username: String,
        password: String,
        wrongPassword: String
    ) {
        client.registerOperator(username, password)

        val response = client.postForEntity(
            "/api/issue-token",
            mapOf("username" to username, "password" to wrongPassword),
            AccessTokenCarrier::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }
}
