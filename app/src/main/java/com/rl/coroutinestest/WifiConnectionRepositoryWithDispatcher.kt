package com.rl.coroutinestest

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * Coroutine thread pool defined in CoroutineDispatcher.
 * They're cancelled when coroutine that started work here is cancelled.
 */
class WifiConnectionRepositoryWithDispatcher(
    private val tcpCommunicator: TcpCommunicator,
    private val dispatcher: CoroutineDispatcher,
) : WifiConnectionRepository {
    override suspend fun initialise(): Boolean {
        val data = "Connect"

        debugWithThread("initialise called, $coroutineContext")

        return withContext(dispatcher) {
            debugWithThread(
                tag = "WifiConnectionRepository",
                text = "initialise.withContext(ioDispatcher), handling work to tcpCommunicator, $coroutineContext"
            )

            val response = tcpCommunicator.sendData(data)

            debugWithThread(
                tag = "WifiConnectionRepository",
                text = "initialise, received response from tcpCommunicator, $coroutineContext"
            )

            response == "Success for $data"
        }
    }

    override suspend fun sendInfoPart1(): String {
        return withContext(dispatcher) {
            tcpCommunicator.sendData("sendInfoPart1, $coroutineContext")
        }
    }

    // other communication methods
}