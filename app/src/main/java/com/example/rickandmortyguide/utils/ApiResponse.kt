package com.example.rickandmortyguide.utils

data class SimpleApiResponse<D>(val info: info, val data:D)

data class info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)