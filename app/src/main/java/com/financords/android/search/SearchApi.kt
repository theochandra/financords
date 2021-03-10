package com.financords.android.search

interface SearchApi {

    suspend fun performSearch(query: String): List<String>

}