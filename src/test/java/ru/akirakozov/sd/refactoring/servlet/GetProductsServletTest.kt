package ru.akirakozov.sd.refactoring.servlet

import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.akirakozov.sd.refactoring.db.entity.Product

class GetProductsServletTest : AbstractServletTest() {
    init {
        initServerRouting(listOf(ServletRouting(GetProductsServlet::class.java, listOf("/get-products"))))
    }

    @Before
    fun startServer() {
        server.start()
    }

    @After
    fun stopServer() {
        server.stop()
    }

    @Test
    fun testEmpty() {
        testDB.initDB()

        testRequest(
            "http://localhost:8081/get-products",
            """
                |<html><body>
                |<h1>All items that we have: </h1>
                |</body></html>
                |
            """.trimMargin()
        )

        testDB.checkProducts(emptyList())
    }

    @Test
    fun testNonEmpty() {
        val products = listOf(
            Product("item1", 1),
            Product("item2", 2),
            Product("item3", 3)
        )
        testDB.initDB(products)

        testRequest(
            "http://localhost:8081/get-products",
            """
                |<html><body>
                |<h1>All items that we have: </h1>
                |item1	1</br>
                |item2	2</br>
                |item3	3</br>
                |</body></html>
                |
            """.trimMargin()
        )

        testDB.checkProducts(products)
    }
}