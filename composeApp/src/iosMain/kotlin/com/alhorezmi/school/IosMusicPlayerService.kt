package com.alhorezmi.school.data.storage

private class IosMusicPlayerService : MusicPlayerService {
    override fun playMenuLoop() = Unit
    override fun playLevelLoop() = Unit
    override fun stop() = Unit
    override fun release() = Unit
}

actual fun createMusicPlayerService(): MusicPlayerService = IosMusicPlayerService()
