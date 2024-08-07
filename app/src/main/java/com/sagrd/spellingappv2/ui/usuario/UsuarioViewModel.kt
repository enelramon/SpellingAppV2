package com.sagrd.spellingappv2.ui.usuario

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagrd.spellingappv2.data.repository.UsuarioRepository
import com.sagrd.spellingappv2.model.SpellListState
import com.sagrd.spellingappv2.model.Usuario
import com.sagrd.spellingappv2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    val usuarioRepository: UsuarioRepository
) : ViewModel() {
    var nombres by mutableStateOf("")
    var edad by mutableStateOf("")
    var user by mutableStateOf(Usuario(0, "", 0))

    private var _state = mutableStateOf(SpellListState())
    val state: State<SpellListState> = _state

    init{
        usuarioRepository.getListStream().onEach {
                result ->
            when (result){
                is Resource.Loading<*> -> {
                    _state.value = SpellListState(isLoading = true)
                }
                is Resource.Success<*> -> {
                    _state.value = SpellListState(usuarios = result.data as List<Usuario> ?: emptyList())
                }
                is Resource.Error<*> -> {
                    _state.value = SpellListState(error = result.message ?: "Error desconocido")
                }
                is Resource.Count<*> -> {
                    _state.value = SpellListState(existeUsuarios = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    var usuario = usuarioRepository.getListStream()
        private set

    fun GetCantidad() : Int{
        var cantidad : Int = 0
        viewModelScope.launch {
            cantidad += usuarioRepository.getListStream().count()
        }
        return cantidad
    }
    fun Guardar(){
        viewModelScope.launch {
            usuarioRepository.upsert(
                Usuario(
                    usuarioId = 0,
                    nombres = nombres,
                    edad = edad.toInt()
                )
            )
        }
    }

    fun find(id : Int): Usuario{

        viewModelScope.launch {
            usuarioRepository.find(id).collect { response ->
                user = response
            }
        }
        return user
    }
}
