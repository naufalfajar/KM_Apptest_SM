package id.naufalfajar.km_interntest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import id.naufalfajar.km_interntest.databinding.FragmentSecondScreenBinding

class SecondScreen : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getBundle("bundleKey")
            binding.tvSelectedUser.text = result?.getString("username").toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setName()
        onBack()
        moveToThirdScreen()
    }

    private fun onBack(){
        binding.materialToolbar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun setName(){
        val name = SecondScreenArgs.fromBundle(arguments as Bundle).name
        binding.tvName.text = name
    }

    private fun moveToThirdScreen(){
        binding.mbtnChooseUser.setOnClickListener {
            findNavController().navigate(SecondScreenDirections.actionSecondScreenToThirdScreen())
        }
    }
}