package com.example.todolist.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.room.database.Graph
import com.example.todolist.data.room.models.OTPCode
import com.example.todolist.data.room.models.User
import com.example.todolist.data.room.repositories.OTPCodeRepository
import com.example.todolist.data.room.repositories.UserRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthViewModel (
    private val userRepository: UserRepository = Graph.userRepository,
    private val otpCodeRepository: OTPCodeRepository = Graph.otpCodeRepository,

    ): ViewModel(){
    var state by mutableStateOf(AuthState())
        private set
    init {
        //getUsers()
    }
    private fun getUsers(){
        viewModelScope.launch {
            userRepository.userList.collectLatest {
                state = state.copy(
                    usersList = it
                )
            }
        }
    }
    suspend fun getUserByEmail(email: String){
        viewModelScope.launch {
            userRepository.getUserByEmail(email = email).collectLatest {
                state = state.copy(
                    usersList = it
                )
            }
        }
    }
    fun sendOTPCode(otpCode: OTPCode){
        viewModelScope.launch {
            otpCodeRepository.insert(otpCode)
        }
    }
    fun register(user: User){
        viewModelScope.launch {
            userRepository.register(user = user)
        }
    }
    fun checkOtpCode(email:String, code: String){
        viewModelScope.launch {
            otpCodeRepository.checkOtpCode(email = email, code = code).collectLatest {
                state = state.copy(
                    otpCodes = it
                )
            }
        }
    }
    fun activateUserByEmail(email: String){
        viewModelScope.launch {
            userRepository.activateUserByEmail(email = email)
        }
    }
}

data class AuthState (
    val usersList: List<User> = emptyList(),
    val otpCodes: List<OTPCode> = emptyList(),
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
)