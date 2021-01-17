package ru.akirakozov.sd.refactoring.db

import ru.akirakozov.sd.refactoring.db.entity.Product
import kotlin.test.assertEquals

class TestDB(url: String) : DBProvider(url) {
    fun checkProducts(expectedProducts: List<Product>) {
        assertEquals(getProducts(), expectedProducts)
    }
}