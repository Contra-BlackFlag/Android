package com.example.noteappfirebase.auth.authviewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun login(email : String ,password : String, onResult : (Boolean, String?) -> Unit ){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, it.exception?.message)
                }
            }
    }

    fun signup(email : String ,password : String, onResult : (Boolean, String?) -> Unit ){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                 if (it.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, it.exception?.message)
                }
            }
    }

}