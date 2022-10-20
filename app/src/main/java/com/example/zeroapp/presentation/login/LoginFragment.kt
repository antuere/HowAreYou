package com.example.zeroapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.databinding.FragmentLoginBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseBindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = this.inflater(inflater, container, false)

        binding!!.apply {
            buttonSignIn.setOnClickListener {
                val email = emailText.text.toString()
                val password = passwordText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { signInTask ->
                            if (signInTask.isSuccessful) {
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSummaryFragment())
                            }
                        }.addOnFailureListener {
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { signUpTask ->
                                    if (signUpTask.isSuccessful) {
                                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSummaryFragment())
                                    }
                                }
                        }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please fill in the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(firebaseAuth.currentUser != null){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSummaryFragment())
        }
    }
}