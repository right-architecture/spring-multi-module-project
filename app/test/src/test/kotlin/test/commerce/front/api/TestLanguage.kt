package test.commerce.front.api

import commerce.front.api.view.AccessTokenCarrier
import commerce.identity.view.UserView
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import java.net.URI

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
): String {
    val response: ResponseEntity<AccessTokenCarrier> = this.postForEntity(
        "/api/issue-token",
        mapOf(
            "email" to email?.toString(),
            "password" to password
        ),
        AccessTokenCarrier::class.java
    )
    return response.body!!.accessToken
}

fun TestRestTemplate.getMe(
    token: String
): ResponseEntity<UserView> {
    val request: RequestEntity<Void> = RequestEntity
        .get(URI("/api/me"))
        .headers { it.setBearerAuth(token) }
        .build()
    return this.exchange(request, UserView::class.java)
}
