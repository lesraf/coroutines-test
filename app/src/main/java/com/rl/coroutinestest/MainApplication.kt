package com.rl.coroutinestest

import android.app.Application
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class MainApplication : Application() {

    private lateinit var applicationScope: CoroutineScope
    private lateinit var tcpCommunicator: TcpCommunicator
    lateinit var wifiConnectionRepositoryScope: WifiConnectionRepositoryWithScope
    lateinit var wifiConnectionRepositoryIoDispatchers: WifiConnectionRepositoryWithDispatcher
    lateinit var wifiConnectionRepositorySingleThread: WifiConnectionRepositoryWithDispatcher


    override fun onCreate() {
        super.onCreate()

        // No DI library used for simplification
        createDependencyGraph()
    }

    private fun createDependencyGraph() {
          applicationScope = CoroutineScope(
            SupervisorJob() +
                    Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        )

          tcpCommunicator = TcpCommunicator()

        // Repo that runs coroutines in scope defined in applicationScope (1 thread)
         wifiConnectionRepositoryScope = WifiConnectionRepositoryWithScope(
            tcpCommunicator = tcpCommunicator,
            externalScope = applicationScope,
        )

        // Repo that runs coroutines on threads pool defined in Dispatchers.IO
         wifiConnectionRepositoryIoDispatchers = WifiConnectionRepositoryWithDispatcher(
            tcpCommunicator = tcpCommunicator,
            dispatcher = Dispatchers.IO,
        )

        // Repo that runs coroutines on 1 thread
         wifiConnectionRepositorySingleThread = WifiConnectionRepositoryWithDispatcher(
            tcpCommunicator = tcpCommunicator,
            dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher(),
        )
    }
}