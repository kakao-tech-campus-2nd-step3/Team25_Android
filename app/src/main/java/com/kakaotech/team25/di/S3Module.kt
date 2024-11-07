package com.kakaotech.team25.di

import android.content.Context
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.kakaotech.team25.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object S3Module {

    @Provides
    @ViewModelScoped
    fun provideS3Client(): AmazonS3Client {
        val credentials: AWSCredentials = BasicAWSCredentials(
            BuildConfig.S3_ACCESS_KEY,
            BuildConfig.S3_SECRET_KEY
        )
        return AmazonS3Client(credentials, Region.getRegion(Regions.AP_NORTHEAST_2))
    }

    @Provides
    @ViewModelScoped
    fun provideTransferUtility(
        @ApplicationContext context: Context,
        s3Client: AmazonS3Client
    ): TransferUtility {
        return TransferUtility.builder()
            .context(context)
            .s3Client(s3Client)
            .build()
    }
}
