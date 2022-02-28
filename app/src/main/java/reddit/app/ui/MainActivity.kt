package reddit.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import reddit.app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    MainFragment.newInstance()
                )
                .commit()
        }

    }
}