package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.db.entity.Product
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class AddProductServlet : AbstractServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val name = request.getParameter("name")
        val price = request.getParameter("price").toInt()

        dbProvider.addProduct(Product(name, price))

        super.doGet(request, response)
        response.writer.println("OK")
    }
}