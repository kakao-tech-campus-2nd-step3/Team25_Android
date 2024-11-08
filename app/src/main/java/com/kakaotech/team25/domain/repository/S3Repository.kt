package com.kakaotech.team25.domain.repository

import android.net.Uri

interface S3Repository {
    suspend fun downloadImageFromS3(s3Url: String): Uri?
}
