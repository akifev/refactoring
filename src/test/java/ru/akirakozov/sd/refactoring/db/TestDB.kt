package ru.akirakozov.sd.refactoring.db

import ru.akirakozov.sd.refactoring.db.entity.Product
import kotlin.test.assertEquals

class TestDB(url: String) : DBProvider(url) {
    fun initDB(products: List<Product> = emptyList()) {
        this.withStatement { statement ->
            statement.executeUpdate(
                """
                |CREATE TABLE IF NOT EXISTS Product (
                |    Id    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                |    Name  TEXT                              NOT NULL,
                |    Price INT                               NOT NULL
                |);
                |
                """.trimMargin()
            )
        }
        this.withStatement { statement ->
            statement.executeUpdate(
                """
                |DELETE FROM Product;
                |
                """.trimMargin()
            )
        }
        for (product in products) {
            this.withStatement { statement ->
                statement.executeUpdate(
                    """
                    |INSERT INTO Product (Name, Price) VALUES 
                    |    ("${product.name}", ${product.price});
                    |
                    """.trimMargin()
                )
            }
        }
    }

    fun getProducts(): List<Product> {
        return this.withStatement { statement ->
            val resultSet = statement.executeQuery(
                """
                |SELECT Name, Price FROM Product;
                |
                """.trimMargin()
            )

            val products = mutableListOf<Product>()
            while (resultSet.next()) {
                val name = resultSet.getString("Name")
                val price = resultSet.getInt("Price")

                products += Product(name, price)
            }

            products
        }
    }

    fun checkProducts(expectedProducts: List<Product>) {
        assertEquals(getProducts(), expectedProducts)
    }
}