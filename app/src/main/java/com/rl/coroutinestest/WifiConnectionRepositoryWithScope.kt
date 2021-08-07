package com.rl.coroutinestest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.coroutineContext

/**
 * Coroutine threads pool defined in CoroutineScope.
 * They're cancelled when scope is cancelled.
 * (It'll continue work even if coroutine that called repo's methods is cancelled)
 */
@Suppress("RedundantAsync") // see: https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad
class WifiConnectionRepositoryWithScope(
    private val tcpCommunicator: TcpCommunicator,
    private val externalScope: CoroutineScope,
) : WifiConnectionRepository {
    override suspend fun initialise(): Boolean {
        val data = "Connect"
        debugWithThread("initialise called, $coroutineContext")

        return externalScope.async {
            debugWithThread(
                tag = "WifiConnectionRepository",
                text = "initialise.async, handling work to tcpCommunicator, $coroutineContext"
            )

            val result = tcpCommunicator.sendData(data)

            debugWithThread(
                tag = "WifiConnectionRepository",
                text = "initialise, received response from tcpCommunicator, $coroutineContext"
            )

            result == "Success for $data"
        }.await()
    }

    override suspend fun sendInfoPart1(): String {
        return externalScope.async {
            tcpCommunicator.sendData("sendInfoPart1, $coroutineContext")
        }.await()
    }
}
