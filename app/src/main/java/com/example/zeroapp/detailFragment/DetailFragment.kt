package com.example.zeroapp.detailFragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentDetailBinding
import com.example.zeroapp.getSmileImage


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DetailFragmentArgs by navArgs()
        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao

        val detailFactory = DetailViewModelFactory(dayDatabaseDao, args.dayId)
        val detailViewModel = ViewModelProvider(this, detailFactory)[DetailViewModel::class.java]

        detailViewModel.currentDay.observe(viewLifecycleOwner) {
            it?.let {
                binding.apply {
                    dateText.text = it.currentDate
                    descText.text = it.dayText
                    smileImage.setImageResource(getSmileImage(it.imageId))
                }
            }
        }
    }
}
