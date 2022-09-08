package com.example.zeroapp.summaryFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase

import com.example.zeroapp.databinding.FragmentSummaryBinding
import kotlinx.coroutines.delay
import timber.log.Timber

class SummaryFragment : Fragment() {

    companion object {
        fun newInstance() = SummaryFragment()
    }

    private lateinit var viewModel: SummaryViewModel
    private lateinit var bindind: FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentSummaryBinding.inflate(inflater, container, false)
        Timber.i("sum log: onCreateView")

        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao
        val factory = SummaryViewModelFactory(dayDatabaseDao)


        viewModel = ViewModelProvider(this, factory)[SummaryViewModel::class.java]
        viewModel.updateLastDay()
        return bindind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        Timber.i("fix! sum log: onViewCreated")

        bindind.fabAddButton.setOnClickListener {
            findNavController().navigate(SummaryFragmentDirections.actionSummaryFragmentToAddDayFragment())
            bindind.fabAddButton.hide()
        }

        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            if (it) {
                bindind.fabAddButton.hide()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Timber.i("fix! sum log: onResume")
        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            if (!it) bindind.fabAddButton.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("fix! sum log: onCreate")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("fix! sum log: onDestroyView")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("fix! sum log: onStart")
    }
}