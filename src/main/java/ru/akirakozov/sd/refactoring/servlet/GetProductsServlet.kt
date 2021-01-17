package ru.akirakozov.sd.refactoring.servlet

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class GetProductsServlet : AbstractServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        dumpProducts(response, dbProvider.getProducts(), "All items that we have: ")
        super.doGet(request, response)
    }
}