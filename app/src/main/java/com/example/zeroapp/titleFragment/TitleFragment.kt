package com.example.zeroapp.titleFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private lateinit var bindind: FragmentTitleBinding
    private lateinit var viewModel: TitleFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentTitleBinding.inflate(inflater, container, false)

        return bindind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao

        val viewModelFactory = TitleFragmentViewModelFactory(dayDatabaseDao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[TitleFragmentViewModel::class.java]

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

        viewModel.getLastDay()

        bindind.bYourPast.setOnClickListener {
            this.findNavController()
                .navigate(TitleFragmentDirections.actionTitleFragmentToHistory())
            viewModel.showToastReset()
        }

        viewModel.lastDay.observe(viewLifecycleOwner) {
            it?.let {
                bindind.bYourPast.visibility = View.VISIBLE
            }
        }

        viewModel.showToast.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    this.context,
                    "Today you already have write",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }


    private fun setSmileListeners(button: ImageButton) {
        button.setOnClickListener {
            val dayText = bindind.textDescribeDay.text.toString()
            val animation = AnimationUtils.loadAnimation(it.context, R.anim.scale_button)
            it.startAnimation(animation)
            viewModel.smileClicked(it.id, dayText)
            bindind.bYourPast.visibility = View.VISIBLE
        }
    }
}
