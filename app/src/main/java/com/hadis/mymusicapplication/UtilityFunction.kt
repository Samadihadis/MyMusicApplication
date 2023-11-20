package com.hadis.mymusicapplication

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.media3.exoplayer.ExoPlayer


fun getAllAudioFromDevice(context: Context) {
    val uri : Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM,
        MediaStore.Audio.ArtistColumns.ARTIST , MediaStore.Audio.Media.ALBUM_ID
    )

    val cursor :Cursor?= context.contentResolver.query(
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
                .parse("context://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(pathUri , cursor.getLong(4))
            allMusicList.add(Music(title , artist , album , path , albumArtUri))
        }
        cursor.close()
    }
}

fun platMusic(context: Context){
val player = ExoPlayer.Builder(context).build()
}