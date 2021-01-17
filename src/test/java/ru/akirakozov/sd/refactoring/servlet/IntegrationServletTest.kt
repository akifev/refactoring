package ru.akirakozov.sd.refactoring.servlet

import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.akirakozov.sd.refactoring.db.entity.Product

class IntegrationServletTest : AbstractServletTest() {
    init {
        initServerRouting(
            listOf(
                ServletRouting(AddProductServlet::class.java, listOf("/add-product")),
                ServletRouting(GetProductsServlet::class.java, listOf("/get-products")),
                ServletRouting(QueryServlet::class.java, listOf("/query"))
            )
        )
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
    fun integrationTest() {
        val products = mutableListOf(
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

        products.add(Product("item4", 4))
        testRequest("http://localhost:8081/add-product?name=item4&price=4", "OK\n")
        testDB.checkProducts(products)

        testRequest(
            "http://localhost:8081/get-products",
            """
                |<html><body>
                |<h1>All items that we have: </h1>
                |item1	1</br>
                |item2	2</br>
                |item3	3</br>
                |item4	4</br>
                |</body></html>
                |
            """.trimMargin()
        )
        testDB.checkProducts(products)

        testRequest(
            "http://localhost:8081/query?command=max",
            """
                |<html><body>
                |<h1>Items with max price: </h1>
                |item4	4</br>
                |</body></html>
                |
            """.trimMargin()
        )
        testDB.checkProducts(products)

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

        testRequest(
            "http://localhost:8081/query?command=sum",
            """
                |<html><body>
                |Summary price: 
                |10
                |</body></html>
                |
            """.trimMargin()
        )
        testDB.checkProducts(products)

        testRequest(
            "http://localhost:8081/query?command=count",
            """
                |<html><body>
                |Number of products: 
                |4
                |</body></html>
                |
            """.trimMargin()
        )
        testDB.checkProducts(products)
    }
}