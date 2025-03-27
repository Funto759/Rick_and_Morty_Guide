package com.example.rickandmortyguide.model

import com.example.rickandmortyguide.data.Result
import com.example.rickandmortyguide.data.RickAndMorty
import com.example.rickandmortyguide.utils.SimpleApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RickAbdMortyApi {

    @GET("/api/character/")
   suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<RickAndMorty>

   @GET("/api/character/")
   suspend fun getCharacterDetails(
        @Query("id") id: Int
    ): Response<Result>

}