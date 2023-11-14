package test.commerce.operation.api

import commerce.operation.api.OperationApiApp
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [OperationApiApp::class]
)
annotation class OperationApiTest
