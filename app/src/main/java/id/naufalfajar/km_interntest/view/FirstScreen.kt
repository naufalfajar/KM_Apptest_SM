package id.naufalfajar.km_interntest.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.naufalfajar.km_interntest.databinding.FragmentFirstScreenBinding

class FirstScreen : Fragment() {
    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPalindrome()
        moveToSecondScreen()
    }

    private fun checkPalindrome(){
        binding.apply {
            mbtnCheck.setOnClickListener {
                val text = etPalindrome.text.toString()

                when{
                    text.isEmpty() ->{
                        etPalindrome.error = "Teks tidak boleh kosong"
                    }
                    else ->{
                        if(isPalindrome(text))
                            showDialog("isPalindrome")
                        else
                            showDialog("not palindrome")
                    }
                }
            }
        }
    }

    private fun isPalindrome(s:String):Boolean {
        val str = s.filter { it.isLetterOrDigit() }.lowercase()
        return str == str.reversed()
    }

    private fun showDialog(result: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Palindrome Test")
        builder.setMessage(result)
        builder.show()
    }

    private fun moveToSecondScreen(){
        binding.mbtnNext.setOnClickListener {
            val name = binding.etName.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etName.error = "Teks tidak boleh kosong"
                }
                else -> {
                    findNavController().navigate(FirstScreenDirections.actionFirstScreenToSecondScreen(name))
                }
            }
        }
    }
}