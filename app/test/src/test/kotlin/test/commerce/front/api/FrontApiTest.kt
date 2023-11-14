package test.commerce.front.api

import commerce.front.api.Application
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [Application::class]
)
annotation class FrontApiTest
