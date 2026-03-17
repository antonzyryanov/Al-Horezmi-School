package com.alhorezmi.school.data.storage

import android.media.MediaPlayer
import com.alhorezmi.school.AndroidContextHolder
import com.alhorezmi.school.R

private class AndroidMusicPlayerService : MusicPlayerService {
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrack: Int? = null

    override fun playMenuLoop() {
        playTrack(R.raw.menu_music)
    }

    override fun playLevelLoop() {
        playTrack(R.raw.level_music)
    }

    private fun playTrack(trackResId: Int) {
        if (mediaPlayer == null || currentTrack != trackResId) {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(AndroidContextHolder.appContext, trackResId).apply {
                isLooping = true
            }
            currentTrack = trackResId
        }
        mediaPlayer?.start()
    }

    override fun stop() {
        mediaPlayer?.pause()
        mediaPlayer?.seekTo(0)
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        currentTrack = null
    }
}

actual fun createMusicPlayerService(): MusicPlayerService = AndroidMusicPlayerService()
