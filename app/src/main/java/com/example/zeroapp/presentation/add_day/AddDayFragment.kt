package com.example.zeroapp.presentation.add_day

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentAddDayBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.util.SmileProvider
import com.example.zeroapp.util.createSharedElementEnterTransition
import com.example.zeroapp.util.setToolbarIcon
import com.example.zeroapp.util.startOnClickAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDayFragment : BaseBindingFragment<FragmentAddDayBinding>(FragmentAddDayBinding::inflate) {

    private val viewModel by viewModels<AddDayFragmentViewModel>()
    private val smileButtons: List<ImageButton> by lazy {
        listOf(
            binding!!.bHappySmile,
            binding!!.bSad,
            binding!!.bSmileLow,
            binding!!.bNone,
            binding!!.bVeryHappy
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = createSharedElementEnterTransition()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarIcon(R.drawable.ic_back)

        smileButtons.forEach(::setSmileListeners)
    }

    private fun setSmileListeners(button: ImageButton) {
        button.setOnClickListener {
            startOnClickAnimation(it)
            val dayText = binding?.textDescribeDay?.text.toString()
            val imageName = SmileProvider.getSmileNameFromBtnId(it.id)

            viewModel.onClickSmile(imageName, dayText)
            binding?.textDescribeDay?.text?.clear()

            findNavController().navigateUp()
        }
    }

}
