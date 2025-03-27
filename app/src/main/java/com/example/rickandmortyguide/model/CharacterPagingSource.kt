package com.example.rickandmortyguide.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyguide.data.Result
import com.example.rickandmortyguide.utils.safeApiCall
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(private val api: RickAbdMortyApi):PagingSource<Int, Result>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: 1

        return try {
           val response = api.getCharacters(position)

            if (response.isSuccessful) {
                val body = response.body()
                LoadResult.Page(
                    data = body?.results ?: emptyList(),
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (body?.info?.next != null) position + 1 else null
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        }catch (e:IOException){
            LoadResult.Error(e)
        }catch (e:HttpException){
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
       return state.anchorPosition?.let { anchorPosition ->
           state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
               ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
       }
    }

    data class NoDataException(val errorMessage: String) : Exception(errorMessage)
}