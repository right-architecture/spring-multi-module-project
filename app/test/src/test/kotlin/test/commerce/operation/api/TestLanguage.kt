package test.commerce.operation.api

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

fun TestRestTemplate.registerOperator(
    username: String,
    password: String
): ResponseEntity<Void> {
    return postForEntity(
        "/api/operators/register",
        mapOf("username" to username, "password" to password),
        Void::class.java
    )
}
