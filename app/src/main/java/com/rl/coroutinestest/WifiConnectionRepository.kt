package com.rl.coroutinestest

interface WifiConnectionRepository {

    suspend fun initialise(): Boolean
    suspend fun sendInfoPart1(): String
}