package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.db.DBProvider
import ru.akirakozov.sd.refactoring.db.entity.Product
import ru.akirakozov.sd.refactoring.db.testDBUrl
import ru.akirakozov.sd.refactoring.html.TemplateHTML.h1
import ru.akirakozov.sd.refactoring.html.TemplateHTML.item
import ru.akirakozov.sd.refactoring.html.TemplateHTML.itemsPage
import ru.akirakozov.sd.refactoring.html.TemplateHTML.numberOfProductsPage
import ru.akirakozov.sd.refactoring.html.TemplateHTML.summaryPricePage
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author volhovm
 */
abstract class AbstractServlet : HttpServlet() {
    val dbProvider = DBProvider(testDBUrl)

    fun dumpProducts(response: HttpServletResponse, products: List<Product>, title: String) {
        response.writer.write(itemsPage(h1(title), products.map { item(it.name, it.price.toString()) }))
    }

    fun dumpSummaryPrice(response: HttpServletResponse, summaryPrice: Int) {
        response.writer.write(summaryPricePage(summaryPrice.toString()))
    }

    fun dumpNumberOfProducts(response: HttpServletResponse, numberOfProducts: Int) {
        response.writer.write(numberOfProductsPage(numberOfProducts.toString()))
    }

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html";
        response.status = HttpServletResponse.SC_OK;
    }
}