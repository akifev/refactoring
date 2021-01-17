package ru.akirakozov.sd.refactoring.html

object TemplateHTML {
    fun htmlBody(body: String) = """
        |<html><body>
        |$body
        |</body></html>
        |
    """.trimMargin()

    fun h1(text: String) = "<h1>$text</h1>"

    fun item(name: String, price: String) = "$name\t$price"

    fun itemsPage(header: String, items: List<String>): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(header)

        if (items.isNotEmpty()) {
            stringBuilder.appendLine()
            for (item in items) {
                stringBuilder.appendLine("$item</br>")
            }
            return htmlBody(stringBuilder.dropLast(1).toString())
        }

        return htmlBody(stringBuilder.toString())
    }

    fun summaryPricePage(price: String) = htmlBody("Summary price: \n$price")

    fun numberOfProductsPage(count: String) = htmlBody("Number of products: \n$count")
}