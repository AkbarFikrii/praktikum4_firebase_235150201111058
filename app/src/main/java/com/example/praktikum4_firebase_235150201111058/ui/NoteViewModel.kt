package com.example.praktikum4_firebase_235150201111058.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.praktikum4_firebase_235150201111058.data.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class NoteViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    fun fetchNotes() {
        db.collection("notes").addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) return@addSnapshotListener
            val list = snapshot.documents.mapNotNull {
                it.toObject<Note>()?.copy(id = it.id)
            }
            _notes.value = list
        }
    }

    fun addNote(note: Note) {
        val noteWithTimestamp = note.copy(timestamp = System.currentTimeMillis())
        db.collection("notes")
            .add(noteWithTimestamp)
            .addOnSuccessListener { documentRef ->
                // optional: update ID di dokumen
                documentRef.update("id", documentRef.id)
            }
    }

    fun updateNote(noteId: String, note: Note) {
        val noteWithNewTimestamp = note.copy(timestamp = System.currentTimeMillis())
        db.collection("notes")
            .document(noteId)
            .set(noteWithNewTimestamp)
    }

    fun deleteNote(noteId: String) {
        db.collection("notes").document(noteId).delete()
    }
}