package com.example.zeroapp.presentation.detail

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.*
import com.example.zeroapp.databinding.FragmentDetailBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import com.example.zeroapp.util.createSharedElementEnterTransition
import com.google.android.material.appbar.MaterialToolbar
import timber.log.Timber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseBindingFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }


    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = createSharedElementEnterTransition()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogListener.collect(this)
        val activity = requireActivity() as MainActivity
        toolbar = activity.toolbar!!
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.isTitleCentered = false

        val menuHost: MenuHost = activity

        viewModel.currentDay.observe(viewLifecycleOwner) {
            it?.let {
                binding!!.apply {
                    dateText.text = it.currentDateString
                    descText.text = it.dayText
                    smileImage.setImageResource(it.imageId)
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

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                viewModel.currentDay.observe(viewLifecycleOwner) {
                    it?.let {
                        val favItem = menu.getItem(0)
                        if (it.isFavorite) {
                            favItem.setIcon(R.drawable.ic_baseline_favorite)
                            favItem.isChecked = true
                        } else {
                            favItem.setIcon(R.drawable.ic_baseline_favorite_border)
                            favItem.isChecked = false
                        }
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_item -> {
                        viewModel.onDeleteButtonClicked()
                        true
                    }
                    R.id.fav_item -> {
                        val anim =
                            AnimationUtils.loadAnimation(requireContext(), R.anim.scale_button)
                        activity.findViewById<View>(R.id.fav_item).startAnimation(anim)
                        viewModel.onFavoriteButtonClicked()

                        if (menuItem.isChecked) {
                            menuItem.setIcon(R.drawable.ic_baseline_favorite_border)
                            menuItem.isChecked = false
                        } else {
                            menuItem.setIcon(R.drawable.ic_baseline_favorite)
                            menuItem.isChecked = true
                        }
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
