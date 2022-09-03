package com.example.zeroapp.titleFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentTitleBinding
import com.example.zeroapp.historyFragment.HistoryFragment
import com.example.zeroapp.timberTag
import timber.log.Timber


class TitleFragment : Fragment() {

    private lateinit var bindind: FragmentTitleBinding
    private lateinit var viewModel: TitleFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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


        bindind.bYourPast.setOnClickListener {
            this.findNavController()
                .navigate(TitleFragmentDirections.actionTitleFragmentToHistory())
        }

        viewModel.lastDay.observe(viewLifecycleOwner, Observer {
            it?.let {
                bindind.bYourPast.visibility = View.VISIBLE
            }
        })

    }


    private fun setSmileListeners(button: ImageButton) {
        button.setOnClickListener {
            val dayText = bindind.textDescribeDay.text.toString()
            viewModel.smileClicked(it.id, dayText)
            bindind.bYourPast.visibility = View.VISIBLE
        }
    }
}
