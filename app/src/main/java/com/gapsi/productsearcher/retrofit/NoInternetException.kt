package com.gapsi.productsearcher.retrofit

import java.io.IOException

/**
 * @author Luis L.
 *         Description:
 *         created on 21/09/2019
 */
class NoInternetException : IOException() {
    override fun getLocalizedMessage(): String {
        return "No hay Internet"
    }

    override val message: String?
        get() = "No hay Internet"
}