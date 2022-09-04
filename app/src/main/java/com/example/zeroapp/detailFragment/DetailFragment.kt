package com.example.zeroapp.detailFragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentDetailBinding
import com.example.zeroapp.getSmileImage
import com.example.zeroapp.showAlertDialog


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        val args: DetailFragmentArgs by navArgs()
        val dayId = args.dayId
        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao

        val detailFactory = DetailViewModelFactory(dayDatabaseDao, dayId)
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

        detailViewModel.navigateToHistory.observe(viewLifecycleOwner) {
            if (it) {
                this.findNavController()
                    .navigate(DetailFragmentDirections.actionDetailFragmentToHistory())
                detailViewModel.navigateDone()
            }
        }

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_item -> {
                        showAlertDialog(detailViewModel, dayId, this@DetailFragment.context)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
