package com.example.zeroapp.presentation.sign_in_methods

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentSignInMethodsBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.util.createSharedElementEnterTransition
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

//@AndroidEntryPoint
//class SignInMethodsFragment :
//    BaseBindingFragment<FragmentSignInMethodsBinding>(FragmentSignInMethodsBinding::inflate) {
//
//    private val viewModel by viewModels<SignInMethodsViewModel>()
//
//    @Inject
//    lateinit var signInClient: GoogleSignInClient
//
//    private val launcher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                viewModel.handleResult(task)
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        sharedElementEnterTransition = createSharedElementEnterTransition()
//
//        exitTransition = MaterialElevationScale(false).apply {
//            duration = resources.getInteger(R.integer.duration_normal).toLong()
//        }
//
//        reenterTransition = MaterialElevationScale(true).apply {
//            duration = resources.getInteger(R.integer.duration_normal).toLong()
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = this.inflater(inflater, container, false)
//        binding!!.buttonEmailMethod.setOnClickListener {
//            val transitionName = getString(R.string.transition_name_for_sign_in)
//            val extras = FragmentNavigatorExtras(binding!!.buttonEmailMethod to transitionName)
//            findNavController().navigate(
//                SignInMethodsFragmentDirections.actionSignInMethodsFragmentToLoginFragment(),
//                extras
//            )
//        }
//
//        binding!!.buttonGoogleMethod.setOnClickListener {
//            val intent = signInClient.signInIntent
//            launcher.launch(intent)
//        }
//
//        return binding!!.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        postponeEnterTransition()
//        view.doOnPreDraw {
//            startPostponedEnterTransition()
//        }
//
//        viewModel.signInState.observe(viewLifecycleOwner) { state ->
//            state?.let {
//                when (it) {
//                    is SignInMethodsState.UserAuthorized -> findNavController().navigateUp()
//                    is SignInMethodsState.Error -> showSnackBarByString(string = it.message)
//                }
//                viewModel.nullifyState()
//            }
//        }
//
//    }
//}