package ru.akirakozov.sd.refactoring.servlet

import junit.framework.TestCase.assertEquals
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.util.StringUtil.CRLF
import ru.akirakozov.sd.refactoring.db.TestDB
import ru.akirakozov.sd.refactoring.db.testDBUrl
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

abstract class AbstractServletTest {
    open val server = Server(8081)
    open val testDB = TestDB(testDBUrl)

    fun initServerRouting(servletRoutingList: List<ServletRouting>) {
        val handler = ServletContextHandler(ServletContextHandler.SESSIONS)
        handler.contextPath = "/"
        for ((servletClass, routes) in servletRoutingList) {
            for (route in routes) {
                handler.addServlet(servletClass, route)
            }
        }
        server.handler = handler
    }

    fun testRequest(request: String, expectedResponse: String) {
        val actualResponse = BufferedReader(InputStreamReader(URL(request).openStream())).use {
            it.readText().replace(CRLF, "\n")
        }

        assertEquals(expectedResponse, actualResponse)
    }
}