package com.example.rickandmortyguide.model

import retrofit2.http.GET
import retrofit2.http.Query


interface RickAbdMortyApi {

    @GET("/api")
    fun getCharacters(
        @Query("name") name: String,
        @Query("page") page: Int
    ): List<Character>
}