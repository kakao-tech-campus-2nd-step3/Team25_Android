package com.kakaotech.team25.di

import android.content.Context
import com.amazonaws.auth.CognitoCachingCredentialsProvider
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
    fun provideS3Client(
        @ApplicationContext context: Context
    ): AmazonS3Client {
        val credentialsProvider = CognitoCachingCredentialsProvider(
            context,
            BuildConfig.S3_COGNITO_ID,
            Regions.AP_NORTHEAST_2
        )

        return AmazonS3Client(credentialsProvider, Region.getRegion(Regions.AP_NORTHEAST_2))
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
