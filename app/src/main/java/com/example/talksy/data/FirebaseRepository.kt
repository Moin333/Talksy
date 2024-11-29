package com.example.talksy.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream


class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference
    private val firestore = FirebaseFirestore.getInstance()

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            onResult(task.isSuccessful)
        }
    }

    fun register(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            onResult(task.isSuccessful)
        }
    }

    fun checkUserProfile(onResult: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            database.child("users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onResult(snapshot.exists())
                }
                override fun onCancelled(error: DatabaseError) {
                    onResult(false)
                }
            })
        } else {
            onResult(false)
        }
    }

    // Convert Bitmap to Base64 string
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // Convert Base64 string back to Bitmap
    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }



    fun saveUserProfile(username: String, bitmap: Bitmap, onResult: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        val profilePicBase64 = bitmapToBase64(bitmap)
        val user = User(uid, username, profilePicBase64)

        firestore.collection("users")
            .document(uid)
            .set(user)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun getUserProfile(onResult: (User?) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    onResult(user)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun sendMessage(chatId: String, message: Message) {
        database.child("chats").child(chatId).push().setValue(message)
    }
}