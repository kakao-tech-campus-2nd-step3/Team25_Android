package com.example.team25.data.network.calladapter

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ResultCall<T : Any>(private val call: Call<T>) : Call<Result<T>> {
    override fun clone(): Call<Result<T>> = ResultCall(call.clone())

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException()
    }

    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = when (val code = response.code()) {
                    in 200..299 -> handleSuccess(response)
                    401 -> handleUnauthorized(code, response)
                    in 400..499 -> handleClientError(code, response)
                    in 500..599 -> handleServerError(code, response)
                    else -> handleUnknownCode(code, response)
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, error: Throwable) {
                val result = when (error) {
                    is IOException -> handleNetworkError(error)
                    else -> handleUnexpectedError(error)
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }
        })
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    /**
     * 에러 처리 로직(응답 있을 경우)
     * - handleSuccess: 성공적인 응답 처리
     * - handleUnauthorized: 인증되지 않은 요청 처리 및 로그 기록
     * - handleClientError: 클라이언트 에러[400,499] 처리 및 로그 기록
     * - handleServerError: 서버 에러[500,599] 처리 및 로그 기록
     * - handleUnknownCode: 예상하지 못한 응답 코드 처리 및 로그 기록
     *
     * 에러 처리 로직(응답 없을 경우)
     * - handleNetworkError: 네트워크 에러 처리 및 로그 기록
     * - handleUnexpectedError: 기타 에러 처리 및 로그 기록
     */

    private fun handleSuccess(response: Response<T>): Result<T> {
        Log.i(TAG, "Success")
        return Result.Success(response.body())
    }

    private fun handleUnauthorized(code: Int, response: Response<T>): Result<T> {
        Log.e(TAG, "Code: $code\nResponse: ${response.errorBody()?.string() ?: "Unauthorized"}")
        return Result.Failure(code, response.errorBody()?.string() ?: "Unauthorized")
    }

    private fun handleClientError(code: Int, response: Response<T>): Result<T> {
        Log.e(TAG, "Code: $code\nResponse: ${response.errorBody()?.string() ?: "Client error"}")
        return Result.Failure(code, response.errorBody()?.string() ?: "Client error")
    }

    private fun handleServerError(code: Int, response: Response<T>): Result<T> {
        Log.e(TAG, "Code: $code\nResponse: ${response.errorBody()?.string() ?: "Server error"}")
        return Result.Failure(code, response.errorBody()?.string() ?: "Server error")
    }

    private fun handleUnknownCode(code: Int, response: Response<T>): Result<T> {
        Log.e(TAG, "Code: $code\nResponse: ${response.errorBody()?.string() ?: "Unknown code: $code"}")
        return Result.Failure(code, response.errorBody()?.string() ?: "Unknown code: $code")
    }

    private fun handleNetworkError(error: IOException): Result<T> {
        Log.e(TAG, error.message.toString())
        return Result.NetworkError(error)
    }

    private fun handleUnexpectedError(error: Throwable?): Result<T> {
        Log.e(TAG, error?.message.toString())
        return Result.Unexpected(error)
    }

    companion object {
        const val TAG = "Result"
    }
}
