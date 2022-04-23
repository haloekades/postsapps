package com.ekades.poststest.helper

data class Episode (
    val id: Long,
    val title: String,
    val duration: String,
    val thumbnailUrl: String,
    val description: String,
    val isFree: Boolean,
    var downloadStatus: DownloadStatus
)