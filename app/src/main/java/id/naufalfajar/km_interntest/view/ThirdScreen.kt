package id.naufalfajar.km_interntest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.naufalfajar.km_interntest.adapter.ThirdScreenAdapter
import id.naufalfajar.km_interntest.databinding.FragmentThirdScreenBinding
import id.naufalfajar.km_interntest.model.Data
import id.naufalfajar.km_interntest.repository.UsersRepository
import id.naufalfajar.km_interntest.service.ApiClient
import id.naufalfajar.km_interntest.service.ApiService
import id.naufalfajar.km_interntest.utils.Status
import id.naufalfajar.km_interntest.utils.viewModelsFactory
import id.naufalfajar.km_interntest.viewmodel.ThirdScreenViewModel

class ThirdScreen : Fragment() {
    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: ThirdScreenAdapter

    private val apiService: ApiService by lazy { ApiClient.instance }
    private val usersRepository: UsersRepository by lazy { UsersRepository(apiService)}
    private val thirdScreenViewModel: ThirdScreenViewModel by viewModelsFactory { ThirdScreenViewModel(usersRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBack()
        initRecyclerView()
        pullToRefresh()
        observeData()
    }

    private fun onBack(){
        binding.materialToolbar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        userAdapter = ThirdScreenAdapter{ data: Data ->
            val name = data.firstName + " " + data.lastName
            Toast.makeText(requireContext(), "Selected User Name Changed", Toast.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putString("username", name)
            setFragmentResult("requestKey", bundleOf("bundleKey" to bundle))
        }
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }

    private fun observeData(){
        thirdScreenViewModel.getUsers(1,12).observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvUser.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.rvUser.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    userAdapter.updateData(it.data)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pullToRefresh(){
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                observeData()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}