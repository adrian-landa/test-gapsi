package com.gapsi.productsearcher.constants

object Web {

    const val LOG_API = "logAPI"
    const val TIMEOUT_MS: Long = 25000
    const val PAGING_ITEMS = 15

    val URL_BASE: String ="https://shoppapp.liverpool.com.mx"
    private const val API: String = "/appclienteservices/services"
    private const val VERSION: String = "/v3"
    const val EP_PLP = "/plp"


    const val URL_SERVICE_SEARCH_PRODUCTS: String = "$API$VERSION$EP_PLP"

    const val PARAM_FORCE: String = "force-plp"
    const val PARAM_SEARCH: String = "search-string"
    const val PARAM_PAGE: String = "page-number"
    const val PARAM_ITEMS: String = "number-of-items-per-page"


}