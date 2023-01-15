package id.naufalfajar.km_interntest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.naufalfajar.km_interntest.repository.UsersRepository
import id.naufalfajar.km_interntest.utils.Resource
import kotlinx.coroutines.Dispatchers

class ThirdScreenViewModel(private val usersRepository: UsersRepository): ViewModel() {

    fun getUsers(page: Int, per_page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = usersRepository.getUsers(page, per_page)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}