package com.example.zeroapp.addDayFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentAddDayBinding


class AddDayFragment : Fragment() {

    private lateinit var bindind: FragmentAddDayBinding
    private lateinit var viewModel: AddDayFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentAddDayBinding.inflate(inflater, container, false)

        return bindind.root
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

        val smileButtons = listOf(
            bindind.bHappySmile,
            bindind.bSad,
            bindind.bSmileLow,
            bindind.bNone,
            bindind.bVeryHappy
        )

        smileButtons.forEach {
            setSmileListeners(it)
        }

    }

    private fun setSmileListeners(button: ImageButton) {
        button.setOnClickListener {
            val dayText = bindind.textDescribeDay.text.toString()
            val animation = AnimationUtils.loadAnimation(it.context, R.anim.scale_button)
            it.startAnimation(animation)
            viewModel.smileClicked(it.id, dayText)
            bindind.textDescribeDay.text?.clear()
            findNavController().navigate(AddDayFragmentDirections.actionAddDayFragmentToSummaryFragment())
        }
    }

}
