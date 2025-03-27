package com.example.rickandmortyguide.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyguide.data.Result
import com.example.rickandmortyguide.utils.ResultWrapper
import com.example.rickandmortyguide.utils.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    val api: RickAbdMortyApi
) : ViewModel() {

    private var _characterList = MutableStateFlow<RickAndMortyViewState>(RickAndMortyViewState.Idle)
    val characterList = _characterList.asStateFlow()


    fun getCharacters() : Flow<PagingData<Result>>{
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false), pagingSourceFactory = {CharacterPagingSource(api)}
        ).flow.cachedIn(viewModelScope)
    }

    fun getCharacterDetails(id:Int){

        viewModelScope.launch {
            _characterList.value = RickAndMortyViewState.Loading(true)
            try {
                when(val response = safeApiCall { api.getCharacterDetails(id) }){

                    is ResultWrapper.Success -> {
                        _characterList.value = RickAndMortyViewState.Loading(false)
                         _characterList.value = RickAndMortyViewState.Success(response.value.body())
                    }
                    is ResultWrapper.GenericError -> {
                        _characterList.value = RickAndMortyViewState.Loading(false)
                        _characterList.value = RickAndMortyViewState.Error(response.error)
                    }

                    else -> {}
                }

            } catch (e:Exception){
                Log.e("ViewModel", e.message.toString())
            }
        }
    }





    sealed class RickAndMortyViewState() {
         object Idle : RickAndMortyViewState()
        data class Loading(val loading :Boolean) : RickAndMortyViewState()
        data class Success(val data: Result?) : RickAndMortyViewState()
        data class Error(val message: String) : RickAndMortyViewState()
    }
}