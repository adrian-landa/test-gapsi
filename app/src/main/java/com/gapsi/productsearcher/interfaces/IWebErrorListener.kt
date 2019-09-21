package com.gapsi.productsearcher.interfaces

import com.gapsi.productsearcher.enums.ExceptionType

interface IWebErrorListener {
    fun handleException(type: ExceptionType, code: Int?, message: String?)
}