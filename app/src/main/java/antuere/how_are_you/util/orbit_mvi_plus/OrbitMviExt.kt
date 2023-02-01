package antuere.how_are_you.util.orbit_mvi_plus

import kotlinx.coroutines.runBlocking
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitDsl
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.annotation.OrbitInternal
import org.orbitmvi.orbit.idling.withIdling
import org.orbitmvi.orbit.syntax.simple.SimpleContext


//TODO УДАЛИТЬ ПОТОМ
//@OptIn(OrbitInternal::class)
//@OrbitDsl
//fun <S : Any, SE : Any> ContainerHost<S, SE>.updateState(
//    reducer: SimpleContext<S>.() -> S,
//): Unit =
//    runBlocking {
//        container.orbit {
//            withIdling(true) {
//                this.apply {
//                    reduce { reducerState ->
//                        SimpleContext(reducerState).reducer()
//                    }
//                }
//            }
//        }
//    }
//
//@OptIn(OrbitInternal::class, OrbitExperimental::class)
//@OrbitDsl
//fun <S : Any, SE : Any> ContainerHost<S, SE>.updateStateBlocking(
//    reducer: SimpleContext<S>.() -> S,
//): Unit =
//    runBlocking {
//        container.inlineOrbit {
//            withIdling(true) {
//                this.apply {
//                    reduce { reducerState ->
//                        SimpleContext(reducerState).reducer()
//                    }
//                }
//            }
//        }
//    }
//
//@OptIn(OrbitInternal::class)
//@OrbitDsl
//fun <S : Any, SE : Any> ContainerHost<S, SE>.sideEffect(
//    sideEffect: SE,
//): Unit =
//    runBlocking {
//        container.orbit {
//            withIdling(true) {
//                this.postSideEffect(sideEffect)
//            }
//        }
//    }