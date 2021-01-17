package ru.akirakozov.sd.refactoring.html

object TemplateHTML {
    @JvmStatic
    fun htmlBody(body: String) = """
        |<html><body>
        |$body
        |</body></html>
        |
    """.trimMargin()

    @JvmStatic
    fun h1(text: String) = "<h1>$text</h1>"

    @JvmStatic
    fun item(name: String, price: String) = "$name\t$price"

    @JvmStatic
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

    @JvmStatic
    fun summaryPricePage(price: String) = htmlBody("Summary price: \n$price")

    @JvmStatic
    fun numberOfProductsPage(count: String) = htmlBody("Number of products: \n$count")
}