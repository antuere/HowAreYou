package com.example.zeroapp.historyFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentHistoryBinding

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

        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao

        val factory = HistoryViewModelFactory(dayDatabaseDao)

        viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        val manager = GridLayoutManager(activity, 4)
        bindind.dayList.layoutManager = manager

        val adapter = DayAdapter()
        bindind.dayList.adapter = adapter
        viewModel.listDays.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


    }
}