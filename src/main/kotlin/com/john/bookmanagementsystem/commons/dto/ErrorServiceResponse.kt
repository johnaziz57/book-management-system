package com.john.bookmanagementsystem.commons.dto

import com.fasterxml.jackson.annotation.JsonCreator

// @JsonCreator helps Jakson (the library that spring use to parse JSON) to works
// in a more optimized way
data class ErrorServiceResponse @JsonCreator constructor(val message: String)