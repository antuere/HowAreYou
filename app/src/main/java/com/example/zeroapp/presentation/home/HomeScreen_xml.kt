package com.example.zeroapp.presentation.home

//@AndroidEntryPoint
//class HomeScreen : ComponentActivity() {
//
//    private val viewModel by viewModels<HomeViewModel>()
//
//    private var fabButton: FloatingActionButton? = null
//
//    private val dialogListener: UIDialogListener by lazy {
//        UIDialogListener(requireContext(), viewModel)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        exitTransition = MaterialFadeThrough().apply {
//            duration = resources.getInteger(R.integer.duration_normal).toLong()
//        }
//
//        enterTransition = MaterialFadeThrough().apply {
//            duration = resources.getInteger(R.integer.duration_normal).toLong()
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = this.inflater(inflater, container, false)
//
//        viewModel.dayQuote.observe(viewLifecycleOwner) { quote ->
//            quote?.let {
//                binding!!.quotesText.text = it.text
//                val author = "${it.author} "
//                binding!!.quotesAuthor.text = author
//            }
//        }
//        return binding?.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        postponeEnterTransition()
//        view.doOnPreDraw {
//            startPostponedEnterTransition()
//        }
//
//        viewModel.wishText.observe(viewLifecycleOwner) {
//            it?.let { wishString ->
//                binding!!.wishText.text = wishString
//            }
//        }
//
//        viewModel.isShowSnackBar.observe(viewLifecycleOwner) {
//            if (it) {
//                showSnackBar(
//                    anchorView = binding!!.fabAddButton,
//                    stringResId = R.string.snack_bar_warning_negative,
//                    duration = 3000
//                )
//                viewModel.resetSnackBar()
//            }
//        }
//        fabButton = binding!!.fabAddButton
//        fabButton!!.setOnClickListener {
//
//            var transitionName = getString(R.string.transition_name_for_sum)
//            if (it.tag == getString(R.string.add)) {
//                val extrasAdd = FragmentNavigatorExtras(binding!!.fabAddButton to transitionName)
//                findNavController().navigate(
//                    SummaryFragmentDirections.actionSummaryFragmentToAddDayFragment(), extrasAdd
//                )
//            } else {
//                transitionName = getString(R.string.transition_name)
//                val extrasSmile = FragmentNavigatorExtras(binding!!.fabAddButton to transitionName)
//                findNavController().navigate(
//                    SummaryFragmentDirections.actionSummaryFragmentToDetailFragment(
//                        viewModel.lastDay.value!!.dayId
//                    ), extrasSmile
//                )
//            }
//        }
//
//
//        binding!!.favorites.setOnClickListener {
//            val transitionName = getString(R.string.transition_name_for_fav)
//            val extras = FragmentNavigatorExtras(binding!!.favorites to transitionName)
//            findNavController().navigate(
//                SummaryFragmentDirections.actionSummaryFragmentToFavoritesFragment(), extras
//            )
//        }
//
//        binding!!.cats.setOnClickListener {
//            val transitionName = getString(R.string.transition_name_for_cats)
//            val extras = FragmentNavigatorExtras(binding!!.cats to transitionName)
//            findNavController().navigate(
//                SummaryFragmentDirections.actionSummaryFragmentToCatsFragment(), extras
//            )
//        }
//
//        dialogListener.collect(this, withNeutralBtn = true)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        viewModel.fabButtonState.observe(viewLifecycleOwner) {
//            fabButton!!.tag = getString(it.tag)
//            fabButton!!.setImageResource(it.image)
//            if (it.tag == R.string.smile) {
//                binding!!.fabAddButton.transitionName = getString(it.transitionName)
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        viewModel.fabButtonState.observe(viewLifecycleOwner) {
//            if (it.tag == R.string.add) {
//                binding!!.fabAddButton.transitionName = getString(it.transitionName)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        fabButton = null
//        binding = null
//    }
//}
