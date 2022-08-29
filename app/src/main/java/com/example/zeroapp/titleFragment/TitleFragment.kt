package com.example.zeroapp.titleFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentTitleBinding
import com.example.zeroapp.historyFragment.HistoryFragment


class TitleFragment : Fragment() {

    private lateinit var bindind: FragmentTitleBinding
    private lateinit var viewModel: TitleFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = FragmentTitleBinding.inflate(inflater, container, false)


        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao


        val dayDesc: String = bindind.textDescribeDay.text.toString()

        val viewModelFactory = TitleFragmentViewModelFactory(dayDatabaseDao, dayDesc)
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
            val fragment = HistoryFragment()
            parentFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, fragment)
                .commit()

        }


        return bindind.root
    }


    private fun setSmileListeners(button: ImageButton) {
        button.setOnClickListener {
            viewModel.smileClicked(it.id)
            bindind.bYourPast.visibility = View.VISIBLE
        }
    }


}