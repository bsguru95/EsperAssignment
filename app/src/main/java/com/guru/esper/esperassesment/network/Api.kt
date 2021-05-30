package com.guru.esper.esperassesment.network

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("/mhrpatel12/esper-assignment/db")
    fun getMobileData(): Observable<Response<CommonRespone>>
}