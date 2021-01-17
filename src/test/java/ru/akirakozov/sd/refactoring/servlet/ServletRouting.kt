package ru.akirakozov.sd.refactoring.servlet

import javax.servlet.Servlet

data class ServletRouting(
    val servletClass: Class<out Servlet>,
    val routes: List<String>
)
