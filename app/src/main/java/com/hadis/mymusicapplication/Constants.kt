package com.hadis.mymusicapplication

import androidx.media3.common.Player


val allMusicList = mutableListOf<Music>()
val favoriteSongs = mutableListOf<Music>()
val mainList = mutableListOf<Music>()
var currentMusic : Music ?= null
val playerList = mutableListOf<Player>()