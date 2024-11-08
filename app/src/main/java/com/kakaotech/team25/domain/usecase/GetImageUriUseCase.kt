package com.kakaotech.team25.domain.usecase

import android.net.Uri
import com.kakaotech.team25.domain.repository.S3Repository
import javax.inject.Inject

class GetImageUriUseCase @Inject constructor(
    private val s3Repository: S3Repository
) {
    suspend operator fun invoke(s3url: String): Uri? {
        return try {
            s3Repository.downloadImageFromS3(s3url)
        } catch (e: Exception) {
            null
        }
    }
}
