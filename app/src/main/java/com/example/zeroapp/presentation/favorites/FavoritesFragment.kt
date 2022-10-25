package com.example.zeroapp.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionManager
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentFavoritesBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import com.example.zeroapp.presentation.favorites.adapter.FavoritesAdapter
import com.example.zeroapp.util.createSharedElementEnterTransition
import com.example.zeroapp.util.setToolbarIcon
import com.google.android.material.transition.MaterialFade
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseBindingFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel by viewModels<FavoritesViewModel>()

    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = createSharedElementEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        val manager = GridLayoutManager(activity, 2)
        binding!!.favoritesList.layoutManager = manager

        setToolbarIcon(R.drawable.ic_back)

        val adapter = FavoritesAdapter(viewModel.dayClickListener)
        binding!!.favoritesList.adapter = adapter

        viewModel.listDays.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    val materialFade = MaterialFade().apply {
                        duration = 250L
                    }
                    TransitionManager.beginDelayedTransition(container!!, materialFade)

                    binding!!.favoritesHint.visibility = View.VISIBLE
                } else {
                    binding!!.favoritesHint.visibility = View.GONE
                }
                adapter.submitList(it)
            }
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogListener.collect(viewLifecycleOwner)
        viewModel.navigateToDetailState.observe(viewLifecycleOwner) {
            it?.let { state ->
                if (state.navigateToDetail) {
                    findNavController()
                        .navigate(
                            FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(
                                state.dayId!!
                            ),
                            state.extras!!
                        )
                    viewModel.doneNavigateToDetail()
                }
            }
        }

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

    }
}