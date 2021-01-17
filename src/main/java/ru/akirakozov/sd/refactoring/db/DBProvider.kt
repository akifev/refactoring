package ru.akirakozov.sd.refactoring.db

import ru.akirakozov.sd.refactoring.db.entity.Product
import java.sql.DriverManager
import java.sql.Statement

open class DBProvider(private val url: String) {
    fun <T> withStatement(action: (Statement) -> T): T {
        DriverManager.getConnection(url).use { c ->
            c.createStatement().use { s ->
                return action(s)
            }
        }
    }

    fun initDB(products: List<Product> = emptyList()) {
        this.withStatement { statement ->
            statement.executeUpdate(
                """
                |CREATE TABLE IF NOT EXISTS Product (
                |    Id    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                |    Name  TEXT                              NOT NULL,
                |    Price INT                               NOT NULL
                |)
                """.trimMargin()
            )
        }

        this.withStatement { statement ->
            statement.executeUpdate("DELETE FROM Product")
        }

        for (product in products) {
            addProduct(product)
        }
    }

    fun addProduct(product: Product) = withStatement {
        it.executeUpdate("INSERT INTO Product (Name, Price) VALUES (\"${product.name}\", ${product.price})")
    }

    fun getProducts() = getProductsByQuery("SELECT Name, Price FROM Product")

    fun getMax() = getProductsByQuery("SELECT Name, Price FROM Product ORDER BY Price DESC LIMIT 1")

    fun getMin() = getProductsByQuery("SELECT Name, Price FROM Product ORDER BY Price LIMIT 1")

    private fun getProductsByQuery(sql: String) = withStatement { statement ->
        val resultSet = statement.executeQuery(sql)

        val products = mutableListOf<Product>()
        while (resultSet.next()) {
            val name = resultSet.getString("Name")
            val price = resultSet.getInt("Price")

            products += Product(name, price)
        }

        products
    }

    fun getSum() = withStatement {
        val resultSet = it.executeQuery("SELECT SUM(Price) FROM Product")

        if (resultSet.next()) resultSet.getInt(1) else 0
    }

    fun getCount() = withStatement { statement ->
        val resultSet = statement.executeQuery("SELECT COUNT(*) FROM PRODUCT")

        if (resultSet.next()) resultSet.getInt(1) else 0
    }
}