package antuere.how_are_you.presentation.base.ui_compose_components.animation

//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//fun <S> AnimatedInit(
//    state: S,
//    modifier: Modifier = Modifier,
//    enter: EnterTransition = materialFadeThroughIn(200),
//    exit: ExitTransition = materialFadeThroughOut(1),
//    content: @Composable () -> Unit,
//) {
//    var isVisible by remember(state) { mutableStateOf(false) }
//
//    AnimatedVisibility(
//        modifier = modifier,
//        visible = isVisible,
//        enter = enter,
//        exit = exit
//    ) {
//        content()
//    }
//
//    LaunchedEffect(state) {
//        delay(1)
//        isVisible = true
//    }
//}
