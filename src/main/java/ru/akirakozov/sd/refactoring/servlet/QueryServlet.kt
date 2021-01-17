package ru.akirakozov.sd.refactoring.servlet

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class QueryServlet : AbstractServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        when (val command = request.getParameter(COMMAND)) {
            MAX -> dumpProducts(response, dbProvider.getMax(), "Items with max price: ")
            MIN -> dumpProducts(response, dbProvider.getMin(), "Items with min price: ")
            SUM -> dumpSummaryPrice(response, dbProvider.getSum())
            COUNT -> dumpNumberOfProducts(response, dbProvider.getCount())
            else -> response.writer.println("Unknown command: $command")
        }
        super.doGet(request, response)
    }

    companion object {
        private const val MAX = "max"
        private const val MIN = "min"
        private const val SUM = "sum"
        private const val COUNT = "count"
        private const val COMMAND = "command"
    }
}