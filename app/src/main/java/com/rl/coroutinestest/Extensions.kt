package com.rl.coroutinestest

import android.util.Log

fun Any.debugWithThread(text: String) {
    debugWithThread(this.javaClass.simpleName, text)
}

fun Any.debugWithThread(tag: String, text: String) {
    Log.d(tag, "[${Thread.currentThread()}] $text")
}