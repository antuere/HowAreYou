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
import com.example.zeroapp.util.SmileProvider
import com.example.zeroapp.util.createSharedElementEnterTransition
import com.example.zeroapp.util.mainActivity
import com.example.zeroapp.util.setToolbarIcon
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseBindingFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()

    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }

    private val toolbar: MaterialToolbar by lazy {
        mainActivity!!.toolbar!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = createSharedElementEnterTransition()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarIcon(R.drawable.ic_back)
        dialogListener.collect(this)
        toolbar.isTitleCentered = false

        viewModel.currentDay.observe(viewLifecycleOwner) {
            it?.let {
                binding!!.apply {
                    dateText.text = it.dateString
                    descText.text = it.dayText
                    val resId = SmileProvider.getSmileImageByName(it.imageName)
                    smileImage.setImageResource(resId)
                }
            }
        }

        viewModel.navigateToHistory.observe(viewLifecycleOwner) {
            if (it) {
                this.findNavController().navigateUp()
                viewModel.navigateDone()
            }
        }

        buildMenu()
    }

    private fun buildMenu() {
        val menuHost: MenuHost = mainActivity!!
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
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
                        viewModel.onClickDeleteButton()
                        true
                    }
                    R.id.fav_item -> {
                        val anim =
                            AnimationUtils.loadAnimation(requireContext(), R.anim.scale_button)
                        mainActivity!!.findViewById<View>(R.id.fav_item).startAnimation(anim)
                        viewModel.onClickFavoriteButton()

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
