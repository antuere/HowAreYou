package com.example.zeroapp.titleFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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

        viewModel.showToastReset()

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

        viewModel.updateLastDay()

        viewModel.showToast.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    this.context,
                    "Today you already have write",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.showWish.observe(viewLifecycleOwner) {
            if (it) {
                showWish(smileButtons, true)
            } else showWish(smileButtons, false)
        }

    }


    private fun setSmileListeners(button: ImageButton) {
        button.setOnClickListener {
            val dayText = bindind.textDescribeDay.text.toString()
            val animation = AnimationUtils.loadAnimation(it.context, R.anim.scale_button)
            it.startAnimation(animation)
            viewModel.smileClicked(it.id, dayText)

            bindind.textDescribeDay.text?.clear()
        }
    }


    private fun showWish(buttons: List<ImageButton>, show: Boolean) {
        bindind.apply {
            buttons.forEach {
                it.visibility = if (show) View.GONE else View.VISIBLE
            }
            textDescribeLayout.visibility = if (show) View.GONE else View.VISIBLE
            howAreYou.visibility = if (show) View.GONE else View.VISIBLE
            wishForUser.visibility = if (show) View.VISIBLE else View.GONE

        }
    }
}
