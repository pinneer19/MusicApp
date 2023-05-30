package com.example.musicapp.data.playlist

import android.util.Log
import com.example.musicapp.model.UserPlaylist
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait

interface PlaylistRepository {
    fun createPlaylist(userPlaylist: UserPlaylist): Task<DocumentReference>
    suspend fun getPlaylists(): List<UserPlaylist>
    fun deletePlaylist(documentId: String): Task<Void>
    fun editPlaylist(userPlaylist: UserPlaylist): Task<Void>
    suspend fun getPlaylist(documentId: String): UserPlaylist
}

class FirestorePlaylistRepository(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : PlaylistRepository {


    override fun createPlaylist(userPlaylist: UserPlaylist): Task<DocumentReference> {
        val collectionRef = firestore.collection("users")
        val documentRef = collectionRef.document(firebaseAuth.uid!!)

        val playlistsCollectionRef = documentRef.collection("playlists")
        return playlistsCollectionRef.add(userPlaylist)
    }


    override suspend fun getPlaylists(): List<UserPlaylist> {
        val collectionRef = firestore.collection("users")
        val documentRef = collectionRef.document(firebaseAuth.uid!!)

        val playlistsCollectionRef = documentRef.collection("playlists")
        val playlistsQuerySnapshot = playlistsCollectionRef.get().await()

        val playlistsList = mutableListOf<UserPlaylist>()
        for (documentSnapshot in playlistsQuerySnapshot.documents) {
            Log.i("PLAYLISTS", documentSnapshot.data.toString())
            val playlist = documentSnapshot.toObject(UserPlaylist::class.java)
            Log.i("PLAYLISTS", "OK")

            playlist?.let {
                it.documentId = documentSnapshot.id
                playlistsList.add(it)
            }
        }

        return playlistsList
    }

    override fun deletePlaylist(documentId: String): Task<Void> {
        val collectionRef = firestore.collection("users")
        val documentRef = collectionRef.document(firebaseAuth.uid!!)

        val playlistsCollectionRef = documentRef.collection("playlists")
        return playlistsCollectionRef.document(documentId).delete()
    }


    override fun editPlaylist(userPlaylist: UserPlaylist): Task<Void> {
        val collectionRef = firestore.collection("users")
        val documentRef = collectionRef.document(firebaseAuth.uid!!)

        val playlistsCollectionRef = documentRef.collection("playlists")
        return playlistsCollectionRef.document(userPlaylist.documentId).set(userPlaylist)
    }

    override suspend fun getPlaylist(documentId: String): UserPlaylist {
        val collectionRef = firestore.collection("users")
        val documentRef = collectionRef.document(firebaseAuth.uid!!)

        val playlistsCollectionRef = documentRef.collection("playlists")
        val playlist = playlistsCollectionRef.document(documentId).get().await()

        return playlist.toObject(UserPlaylist::class.java)!!.copy(documentId = playlist.id)
    }
}

