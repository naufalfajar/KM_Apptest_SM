package id.naufalfajar.km_interntest.service

import id.naufalfajar.km_interntest.model.Users
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Users
}