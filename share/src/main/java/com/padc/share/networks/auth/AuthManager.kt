package com.padc.share.networks.auth

interface AuthManager {
    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun register(email: String, password: String, userName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun updateProfile(   photoUrl : String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}