package com.rl.coroutinestest

class TcpCommunicator {
    fun sendData(data: String): String {
        debugWithThread(text = "sendData called with $data, starting work")
        Thread.sleep(3000)
        debugWithThread(text = "sendData called with $data, work DONE!")
        return "Success for $data"
    }
}