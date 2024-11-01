package com.example.team25.data.network.calladapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapter<R : Any>(private val responseType: Type) : CallAdapter<R, Call<Result<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<Result<R>> = ResultCall(call)

    class Factory : CallAdapter.Factory() {
        override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
            if (Call::class.java != getRawType(returnType)) return null
            check(returnType is ParameterizedType)

            val responseType = getParameterUpperBound(0, returnType)
            if (getRawType(responseType) != Result::class.java) return null
            check(responseType is ParameterizedType)


            val successType = getParameterUpperBound(0, responseType)

            return ResultCallAdapter<Any>(successType)
        }
    }
}
