package com.example.zeroapp.addDayFragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentAddDayBinding
import com.example.zeroapp.themeColor
import com.google.android.material.transition.MaterialContainerTransform


class AddDayFragment : Fragment() {

    private lateinit var binding: FragmentAddDayBinding
    private lateinit var viewModel: AddDayFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.myNavHostFragment
            duration = 450L
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorOnPrimary))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddDayBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        enterTransition = MaterialContainerTransform().apply {
//            startView = requireActivity().findViewById(R.id.fab_add_button)
//            endView = binding.root
//            duration = 300L
//            scrimColor = Color.TRANSPARENT
//            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorOnPrimary))
//        }

//        returnTransition = Slide().apply {
//            duration = 300L
//            addTarget(binding.root)
//        }

        val activity = requireActivity() as MainActivity
        val toolbar = activity.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)

        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao

        val viewModelFactory = AddDayFragmentViewModelFactory(dayDatabaseDao)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddDayFragmentViewModel::class.java]

        val smileButtons = listOf(
            binding.bHappySmile,
            binding.bSad,
            binding.bSmileLow,
            binding.bNone,
            binding.bVeryHappy
        )

        smileButtons.forEach {
            setSmileListeners(it)
        }

    }

    private fun setSmileListeners(button: ImageButton) {
        button.setOnClickListener {
            val dayText = binding.textDescribeDay.text.toString()
            val animation = AnimationUtils.loadAnimation(it.context, R.anim.scale_button)
            it.startAnimation(animation)
            viewModel.smileClicked(it.id, dayText)
            binding.textDescribeDay.text?.clear()


            findNavController().navigateUp()
        }
    }

}
