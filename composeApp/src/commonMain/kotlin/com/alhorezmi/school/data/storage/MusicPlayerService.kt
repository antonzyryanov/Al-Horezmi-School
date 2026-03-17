package com.alhorezmi.school.data.storage

interface MusicPlayerService {
    fun playMenuLoop()
    fun playLevelLoop()
    fun stop()
    fun release()
}

expect fun createMusicPlayerService(): MusicPlayerService
