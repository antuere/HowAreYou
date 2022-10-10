package com.example.zeroapp.presentation.detail

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.*
import com.example.zeroapp.databinding.FragmentDetailBinding
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import com.example.zeroapp.util.createSharedElementEnterTransition
import com.google.android.material.appbar.MaterialToolbar
import timber.log.Timber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel by viewModels<DetailViewModel>()

    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }


    private lateinit var binding: FragmentDetailBinding
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = createSharedElementEnterTransition()
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

        dialogListener.collect(this)
        val activity = requireActivity() as MainActivity
        toolbar = activity.toolbar!!
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.isTitleCentered = false

        val menuHost: MenuHost = activity

        viewModel.currentDay.observe(viewLifecycleOwner) {
            it?.let {
                binding.apply {
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

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_item -> {
                        viewModel.onDeleteButtonClicked()
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
