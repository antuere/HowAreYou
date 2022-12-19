package com.example.zeroapp.presentation.cats



//@AndroidEntryPoint
//class CatsFragment : BaseBindingFragment<FragmentCatsBinding>(FragmentCatsBinding::inflate) {
//
//    companion object {
//        private const val CATS_URL_1 = "https://source.unsplash.com/random/?cutecats"
//        private const val CATS_URL_2 = "https://source.unsplash.com/random/?feline"
//        private const val CATS_URL_3 = "https://source.unsplash.com/random/?cat"
//        private const val CATS_URL_4 = "https://source.unsplash.com/random/?kitty"
//    }
//
//    private val catsMap: Map<String, ImageView> by lazy {
//        mapOf(
//            CATS_URL_1 to binding!!.imageCat1,
//            CATS_URL_2 to binding!!.imageCat2,
//            CATS_URL_3 to binding!!.imageCat3,
//            CATS_URL_4 to binding!!.imageCat4,
//        )
//    }
//
//    @Inject
//    lateinit var glideManager: RequestManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        sharedElementEnterTransition = createSharedElementEnterTransition()
//
//        reenterTransition = MaterialElevationScale(true).apply {
//            duration = resources.getInteger(R.integer.duration_normal).toLong()
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentCatsBinding.inflate(inflater, container, false)
//        getCatsImage()
//
//        return binding!!.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding!!.buttonGetCat.setOnClickListener {
//            getCatsImage()
//        }
//
//        postponeEnterTransition()
//        view.doOnPreDraw {
//            startPostponedEnterTransition()
//        }
//    }
//
//    private fun getCatsImage() {
//        catsMap.forEach { (url, view) ->
//            glideManager.load(url)
//                .transition(DrawableTransitionOptions.withCrossFade(250))
//                .into(view)
//        }
//    }
//}