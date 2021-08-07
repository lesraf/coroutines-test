package com.rl.coroutinestest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(
            wifiConnectionRepository = (application as MainApplication).wifiConnectionRepositorySingleThread,
            uiContext = Dispatchers.Main
        )

        presenter.onAttach()

        findViewById<Button>(R.id.fakeOnAttachButton).setOnClickListener {
            presenter.onAttach()
        }
        findViewById<Button>(R.id.fakeOnDetachButton).setOnClickListener {
            presenter.onDetach()
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}