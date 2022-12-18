package com.example.zeroapp.presentation.detail

//@AndroidEntryPoint
//class DetailFragment : BaseBindingFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
//
//    private val viewModel by viewModels<DetailViewModel>()
//
//    private val dialogListener: UIDialogListener by lazy {
//        UIDialogListener(requireContext(), viewModel)
//    }
//
//    private var toolbar: MaterialToolbar? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        sharedElementEnterTransition = createSharedElementEnterTransition()
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        dialogListener.collect(this)
//        toolbar = mainActivity!!.toolbar!!
//
//        toolbar!!.isTitleCentered = false
//
//        viewModel.selectedDay.observe(viewLifecycleOwner) {
//            it?.let {
//                binding!!.apply {
//                    dateText.text = it.dateString
//                    descText.text = it.dayText
//                    smileImage.setImageResource(it.imageResId)
//                }
//            }
//        }
//
//        viewModel.navigateToHistory.observe(viewLifecycleOwner) {
//            if (it) {
//                this.findNavController().navigateUp()
//                viewModel.navigateDone()
//            }
//        }
//
//        buildMenu()
//    }
//
//    private fun buildMenu() {
//        val menuHost: MenuHost = mainActivity!!
//        menuHost.addMenuProvider(object : MenuProvider {
//
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.detail_menu, menu)
//            }
//
//            override fun onPrepareMenu(menu: Menu) {
//                super.onPrepareMenu(menu)
//                viewModel.selectedDay.observe(viewLifecycleOwner) {
//                    it?.let {
//                        val favItem = menu.getItem(0)
//                        if (it.isFavorite) {
//                            favItem.setIcon(R.drawable.ic_baseline_favorite)
//                            favItem.isChecked = true
//                        } else {
//                            favItem.setIcon(R.drawable.ic_baseline_favorite_border)
//                            favItem.isChecked = false
//                        }
//                    }
//                }
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                return when (menuItem.itemId) {
//                    R.id.delete_item -> {
//                        viewModel.onClickDeleteButton()
//                        true
//                    }
//                    R.id.fav_item -> {
//                        val anim =
//                            AnimationUtils.loadAnimation(requireContext(), R.anim.scale_button)
//                        mainActivity!!.findViewById<View>(R.id.fav_item).startAnimation(anim)
//                        viewModel.onClickFavoriteButton()
//
//                        if (menuItem.isChecked) {
//                            menuItem.setIcon(R.drawable.ic_baseline_favorite_border)
//                            menuItem.isChecked = false
//                        } else {
//                            menuItem.setIcon(R.drawable.ic_baseline_favorite)
//                            menuItem.isChecked = true
//                        }
//                        true
//                    }
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner, Lifecycle.State.STARTED)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        toolbar!!.isTitleCentered = true
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        toolbar = null
//    }
//
//}
