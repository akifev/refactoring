package ru.akirakozov.sd.refactoring.servlet

import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import ru.akirakozov.sd.refactoring.db.entity.Product
import java.io.IOException
import kotlin.test.assertEquals

class AddProductServletTest : AbstractServletTest() {
    init {
        initServerRouting(listOf(ServletRouting(AddProductServlet::class.java, listOf("/add-product"))))
    }

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
        testDB.initDB()
    }

    @Test
    fun testAddOneProduct() {
        testRequest("http://localhost:8081/add-product?name=item&price=1", "OK\n")

        val products = testDB.getProducts()
        val expectedProducts = listOf(Product("item", 1))

        assertEquals(expectedProducts, products)
    }

    @Test
    fun testAddFewProducts() {
        testRequest("http://localhost:8081/add-product?name=item1&price=1", "OK\n")
        testRequest("http://localhost:8081/add-product?name=item2&price=2", "OK\n")
        testRequest("http://localhost:8081/add-product?name=item3&price=3", "OK\n")

        testDB.checkProducts(
            listOf(
                Product("item1", 1),
                Product("item2", 2),
                Product("item3", 3)
            )
        )
    }

    @Test
    fun testAddFewProductsWithMatchedNames() {
        testRequest("http://localhost:8081/add-product?name=item1&price=1", "OK\n")
        testRequest("http://localhost:8081/add-product?name=item2&price=2", "OK\n")
        testRequest("http://localhost:8081/add-product?name=item1&price=3", "OK\n")
        testRequest("http://localhost:8081/add-product?name=item2&price=4", "OK\n")

        testDB.checkProducts(
            listOf(
                Product("item1", 1),
                Product("item2", 2),
                Product("item1", 3),
                Product("item2", 4)
            )
        )
    }

    @Test
    fun testWrongRequest() {
        assertThrows(IOException::class.java) {
            testRequest("http://localhost:8081/add-product?naame=item&priice=1", "OK\n")
        }

        testDB.checkProducts(emptyList())
    }
}