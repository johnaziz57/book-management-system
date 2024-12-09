package com.john.bookmanagementsystem.commons.dto


interface ResponseCreator<B, R : Response> {
    fun success(body: B): R

    fun failure(message: String, extra: List<String>): R
}