package com.example.zeroapp.historyFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentHistoryBinding
import com.example.zeroapp.timberTag
import timber.log.Timber

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    private lateinit var bindind: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = FragmentHistoryBinding.inflate(inflater, container, false)
        return bindind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("my log viewCreated")

        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao

        val factory = HistoryViewModelFactory(dayDatabaseDao)

        viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        val manager = GridLayoutManager(activity, 3)
        bindind.dayList.layoutManager = manager

        val adapter = DayAdapter(DayListener { dayId ->
            viewModel.smileOnClick(dayId)
        })

        bindind.dayList.adapter = adapter

        viewModel.listDays.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.i("my log Submit list")
                adapter.submitList(it)
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(HistoryFragmentDirections.actionHistoryToDetailFragment(it))
                viewModel.navigateDone()
            }
        })

    }
}