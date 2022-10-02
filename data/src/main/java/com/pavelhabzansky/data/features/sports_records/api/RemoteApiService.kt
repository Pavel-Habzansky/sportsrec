package com.pavelhabzansky.data.features.sports_records.api

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RemoteApiService {

    @GET("{uid}/records.json")
    fun getRecords(@Path("uid") uid: String): Deferred<Response<Map<String, SportsRecordDto>>>

    @PUT("{uid}/records/{new_record_key}.json")
    fun putRecord(
        @Path("uid") uid: String,
        @Path("new_record_key") key: String,
        @Body body: SportsRecordDto
    ): Deferred<Response<Any>>
}