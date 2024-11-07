package com.kakaotech.team25.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.kakaotech.team25.domain.repository.S3Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DefaultS3Repository @Inject constructor(
    private val transferUtility: TransferUtility,
    @ApplicationContext private val context: Context
) : S3Repository {
    override suspend fun downloadImageFromS3(s3Url: String): Uri? {
        return suspendCancellableCoroutine { continuation ->
            try {
                val fileName = s3Url.substringAfterLast("/")
                val tempFile = File(context.cacheDir, fileName)

                val downloadObserver = transferUtility.download(
                    "manager-app-storage",
                    "images/profile/$fileName",
                    tempFile
                )

                downloadObserver.setTransferListener(object : TransferListener {
                    override fun onStateChanged(id: Int, state: TransferState?) {
                        if (state == TransferState.COMPLETED) {
                            Log.d("S3 Download", "Downloaded file path: ${tempFile.absolutePath}")
                            val fileUri = Uri.fromFile(tempFile)
                            continuation.resume(fileUri)
                        }
                    }

                    override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                        Log.d("S3 Download", "Download progress: $bytesCurrent/$bytesTotal")
                    }

                    override fun onError(id: Int, ex: Exception?) {
                        Log.e("S3 Download", "Download error: ${ex?.message}")
                        continuation.resumeWithException(ex ?: Exception("Unknown error"))
                    }
                })

            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }
}
