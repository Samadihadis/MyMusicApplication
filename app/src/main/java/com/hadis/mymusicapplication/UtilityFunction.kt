package com.hadis.mymusicapplication

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.first


fun getAllAudioFromDevice(context: Context) {
    allMusicList.clear()
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM,
        MediaStore.Audio.ArtistColumns.ARTIST, MediaStore.Audio.Media.ALBUM_ID
    )

    val cursor: Cursor? = context.contentResolver.query(
        uri,
        projection,
        null,
        null,
        null
    )
    if (cursor != null) {
        while (cursor.moveToNext()) {
            val path = cursor.getString(0)
            val title = cursor.getString(1)
            val album = cursor.getString(2)
            val artist = cursor.getString(3)
            val pathUri = Uri
                .parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(pathUri, cursor.getLong(4))
            allMusicList.add(Music(title, artist, album, path, albumArtUri, false))
        }
        cursor.close()
    }
}

fun playMusic(context: Context, filePath: String) {
    val player = ExoPlayer.Builder(context).build()
    val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse(filePath))
    player.addMediaItem(mediaItem)
    player.prepare()
    player.play()
    player.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_ENDED)
                player.release()
        }
    })
    playerList.add(player)

}

suspend fun writeToDataStore(context: Context, key: String, value: String) {
    val preferencesKey = stringPreferencesKey(key)
    context.datastore.edit {
        it[preferencesKey] = value
    }
}

suspend fun deleteFromDataStore(context: Context, key: String) {
    val preferencesKey = stringPreferencesKey(key)
    context.datastore.edit {
        it[preferencesKey] = ""
    }
}

suspend fun readFromDataStore(context: Context, key: String):String? {
    val preferencesKey = stringPreferencesKey(key)
    val preferences = context.datastore.data.first()
    return  preferences[preferencesKey]
}