package ru.akirakozov.sd.refactoring

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import ru.akirakozov.sd.refactoring.db.DBProvider
import ru.akirakozov.sd.refactoring.db.testDBUrl
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet
import ru.akirakozov.sd.refactoring.servlet.QueryServlet
import java.sql.DriverManager

/**
 * @author akirakozov
 */
fun main(args: Array<String>) {
    val dbProvider = DBProvider(testDBUrl)
    dbProvider.initDB()

    val server = Server(8081)
    val context = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.contextPath = "/"
    server.handler = context
    context.addServlet(ServletHolder(AddProductServlet()), "/add-product")
    context.addServlet(ServletHolder(GetProductsServlet()), "/get-products")
    context.addServlet(ServletHolder(QueryServlet()), "/query")
    server.start()
    server.join()
}