package com.example.noteappfirebase.note

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.noteappfirebase.model.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class NoteViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseDataBase = FirebaseDatabase.getInstance().reference
    private val storage = FirebaseStorage.getInstance()

    val notes = mutableListOf<Note>()

    init {
        listenToRealTimeNotes()
    }

    fun addNote(note: Note, imageUri: Uri?, onComplete : () -> Unit){
            if (imageUri != null){
                val ref = storage.reference.child("images/${UUID.randomUUID()}")
                ref.putFile(imageUri).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        val updateNote = note.copy(imageUrl = it.toString())
                        saveToFireStoreAndRealTime(updateNote,onComplete)

                    }
                }
            }
            else{
                saveToFireStoreAndRealTime(note,onComplete)
            }
    }

    private fun saveToFireStoreAndRealTime(note : Note, onComplete : () -> Unit){
        firestore.collection("notes").add(note).addOnSuccessListener {
            val pushNote =  note.copy(id = it.id)
            firebaseDataBase.child("notes").child(it.id).setValue(pushNote)
            onComplete()
        }
    }

    fun listenToRealTimeNotes(){
            firebaseDataBase.child("notes").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                            notes.clear()
                            for (child in snapshot.children){
                                    val note = child.getValue(Note::class.java)
                                    note?.let {
                                        notes.add(it)
                                    }
                            }
                     }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase","Error : ${error.message}")
                }
                }

            )
    }
}