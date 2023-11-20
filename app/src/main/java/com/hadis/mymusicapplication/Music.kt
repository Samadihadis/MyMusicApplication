package com.hadis.mymusicapplication

import android.net.Uri

data class Music(
    val title : String ,
    val singerName : String ,
    val albumName : String ,
    val filePath : String ,
    val coverArtUri : Uri
)
