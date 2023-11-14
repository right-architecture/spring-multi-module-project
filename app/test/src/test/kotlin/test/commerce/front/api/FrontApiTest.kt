package test.commerce.front.api

import commerce.front.api.FrontApiApp
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [FrontApiApp::class]
)
annotation class FrontApiTest
