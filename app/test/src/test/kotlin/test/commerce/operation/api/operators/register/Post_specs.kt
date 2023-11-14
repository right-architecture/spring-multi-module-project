package test.commerce.operation.api.operators.register

import commerce.front.api.view.AccessTokenCarrier
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import test.commerce.AutoParameterizedTest
import test.commerce.operation.api.OperationApiTest
import test.commerce.operation.api.registerOperator

@OperationApiTest
class Post_specs(@Autowired val client: TestRestTemplate) {

    @AutoParameterizedTest
    fun `올바른 요청에 대해 200 응답 코드를 반환한다`(
        username: String,
        password: String
    ) {
        val response = client.registerOperator(username, password)
        assertThat(response.statusCode.value()).isEqualTo(200)
    }

    @AutoParameterizedTest
    fun `올바른 요청에 대해 운영자 계정을 생성한다`(
        username: String,
        password: String
    ) {
        client.registerOperator(username, password)

        val response: ResponseEntity<AccessTokenCarrier> = client.postForEntity(
            "/api/issue-token",
            mapOf("username" to username, "password" to password),
            AccessTokenCarrier::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(200)
        assertThat(response.body).isNotNull
        assertThat(response.body!!.accessToken).isNotNull
    }

    @AutoParameterizedTest
    fun `사용자 이름이 지정되지 않으면 400 응답 코드를 반환한다`(
        password: String
    ) {
        val response = client.postForEntity(
            "/api/operators/register",
            mapOf("password" to password),
            Void::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }

    @AutoParameterizedTest
    fun `사용자 이름이 지정되지 않으면 운영자 계정을 생성하지 않는다`(
        username: String,
        password: String
    ) {
        client.postForEntity(
            "/api/operators/register",
            mapOf("password" to password),
            Void::class.java
        )

        val response: ResponseEntity<AccessTokenCarrier> = client.postForEntity(
            "/api/issue-token",
            mapOf("username" to username, "password" to password),
            AccessTokenCarrier::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }

    @AutoParameterizedTest
    fun `비밀번호가 지정되지 않으면 400 응답 코드를 반환한다`(
        username: String
    ) {
        val response = client.postForEntity(
            "/api/operators/register",
            mapOf("username" to username),
            Void::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }

    @AutoParameterizedTest
    fun `비밀번호가 지정되지 않으면 운영자 계정을 생성하지 않는다`(
        username: String,
        password: String
    ) {
        client.postForEntity(
            "/api/operators/register",
            mapOf("username" to username),
            Void::class.java
        )

        val response: ResponseEntity<AccessTokenCarrier> = client.postForEntity(
            "/api/issue-token",
            mapOf("username" to username, "password" to password),
            AccessTokenCarrier::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(400)
    }
}
