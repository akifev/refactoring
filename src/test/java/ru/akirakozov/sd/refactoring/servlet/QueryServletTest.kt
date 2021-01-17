package ru.akirakozov.sd.refactoring.servlet

import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.akirakozov.sd.refactoring.db.entity.Product

class QueryServletTest : AbstractServletTest() {
    init {
        initServerRouting(listOf(ServletRouting(QueryServlet::class.java, listOf("/query"))))
    }

    private val products = listOf(
        Product("item1", 1),
        Product("item2", 2),
        Product("item3", 3)
    )

    @Before
    fun startServer() {
        server.start()
    }

    @After
    fun stopServer() {
        server.stop()
    }

    @Before
    fun initDB() {
        testDB.initDB(products)
    }

    @Test
    fun testMax() {
        testRequest(
            "http://localhost:8081/query?command=max",
            """
                |<html><body>
                |<h1>Items with max price: </h1>
                |item3	3</br>
                |</body></html>
                |
            """.trimMargin()
        )

        testDB.checkProducts(products)
    }

    @Test
    fun testMin() {
        testRequest(
            "http://localhost:8081/query?command=min",
            """
                |<html><body>
                |<h1>Items with min price: </h1>
                |item1	1</br>
                |</body></html>
                |
            """.trimMargin()
        )

        testDB.checkProducts(products)
    }

    @Test
    fun testSum() {
        testRequest(
            "http://localhost:8081/query?command=sum",
            """
                |<html><body>
                |Summary price: 
                |6
                |</body></html>
                |
            """.trimMargin()
        )

        testDB.checkProducts(products)
    }

    @Test
    fun testCount() {
        testRequest(
            "http://localhost:8081/query?command=count",
            """
                |<html><body>
                |Number of products: 
                |3
                |</body></html>
                |
            """.trimMargin()
        )

        testDB.checkProducts(products)
    }
}