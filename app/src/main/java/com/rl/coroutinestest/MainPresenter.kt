package com.rl.coroutinestest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainPresenter(
    private val wifiConnectionRepository: WifiConnectionRepository,
    uiContext: CoroutineContext,
) : CoroutineScope {

    private val tag = "MainPresenter"
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext = uiContext + job

    fun onAttach() {
        launch {
            debugWithThread("startJob called, $coroutineContext")

            if (wifiConnectionRepository.initialise()) {
                debugWithThread(tag, "Connected! $coroutineContext")

                val infoPart1 = wifiConnectionRepository.sendInfoPart1()

                debugWithThread(tag, "Received info from part1: $infoPart1, $coroutineContext")

//                do something with result, i.e. update screen
            }
        }
    }

    fun onDetach() {
        job.cancel()
    }
}