package test.commerce.http.api

import commerce.http.view.AccessTokenCarrier
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

fun TestRestTemplate.signup(
    email: Email?,
    password: String?
): ResponseEntity<*> {
    return this.postForEntity(
        "/api/signup",
        mapOf(
            "email" to email?.toString(),
            "password" to password
        ),
        Void::class.java
    )
}

fun TestRestTemplate.issueToken(
    email: Email?,
    password: String?
): ResponseEntity<AccessTokenCarrier> {
    return this.postForEntity(
        "/api/issue-token",
        mapOf(
            "email" to email?.toString(),
            "password" to password
        ),
        AccessTokenCarrier::class.java
    )
}
