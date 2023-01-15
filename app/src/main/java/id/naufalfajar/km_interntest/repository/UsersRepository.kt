package id.naufalfajar.km_interntest.repository

import id.naufalfajar.km_interntest.service.ApiService

class UsersRepository(private val apiService: ApiService) {
    suspend fun getUsers(page: Int, per_page: Int)
        = apiService.getUsers(page, per_page)
}