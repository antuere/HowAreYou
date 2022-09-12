package com.example.zeroapp.addDayFragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.example.zeroapp.util.getSmileImage
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentAddDayBinding
import com.example.zeroapp.util.themeColor
import com.google.android.material.transition.MaterialContainerTransform


class AddDayFragment : Fragment() {

    private lateinit var binding: FragmentAddDayBinding
    private lateinit var viewModel: AddDayFragmentViewModel
    private lateinit var smileButtons: List<ImageButton>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.myNavHostFragment
            duration = resources.getInteger(R.integer.duration_normal).toLong()
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

        val activity = requireActivity() as MainActivity
        val toolbar = activity.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)

        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao

        val viewModelFactory = AddDayFragmentViewModelFactory(dayDatabaseDao)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddDayFragmentViewModel::class.java]

        smileButtons = listOf(
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
            val imageId = getSmileImage(it.id)

            viewModel.smileClicked(imageId, dayText)
            binding.textDescribeDay.text?.clear()

            findNavController().navigateUp()
        }
    }

}
