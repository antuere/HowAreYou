package com.example.zeroapp.detailFragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.zeroapp.*
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentDetailBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.MaterialContainerTransform
import timber.log.Timber


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.myNavHostFragment
            duration = 300L
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorOnPrimary))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity

        toolbar = activity.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.isTitleCentered = false

        val menuHost: MenuHost = activity

        val args: DetailFragmentArgs by navArgs()
        val dayId = args.dayId
        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao

        val factory = DetailViewModelFactory(dayDatabaseDao, dayId)
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]


        viewModel.currentDay.observe(viewLifecycleOwner) {
            it?.let {
                binding.apply {
                    dateText.text = it.currentDate
                    descText.text = it.dayText
                    smileImage.setImageResource(getSmileImage(it.imageId))
                }
            }
        }

        viewModel.navigateToHistory.observe(viewLifecycleOwner) {
            if (it) {
                this.findNavController().navigateUp()
                viewModel.navigateDone()
            }
        }

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                Timber.i("my log, we in the menuHost")
                menuInflater.inflate(R.menu.detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_item -> {
                        showMaterialDialog(
                            viewModel,
                            dayId,
                            this@DetailFragment.context,
                        )
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    override fun onStop() {
        super.onStop()
        toolbar.isTitleCentered = true
    }
}