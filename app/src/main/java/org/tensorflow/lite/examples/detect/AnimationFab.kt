package org.tensorflow.lite.examples.detect
import android.annotation.SuppressLint
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AnimationFab {
    companion object{
        val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener =
            object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onShown(fab: FloatingActionButton?) {
                    super.onShown(fab)
                }

                @SuppressLint("NewApi")
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
//                fab?.show()
                }
            }

    }
}