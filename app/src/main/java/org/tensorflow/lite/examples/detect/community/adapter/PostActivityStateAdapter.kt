package org.tensorflow.lite.examples.detect.community.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.tensorflow.lite.examples.detect.community.fragment.CareBoardListFragment
import org.tensorflow.lite.examples.detect.community.fragment.FreeBoardListFragment
import org.tensorflow.lite.examples.detect.community.fragment.ShowBoardListFragment
import org.tensorflow.lite.examples.detect.community.fragment.WalkBoardListFragment

class PostActivityStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        var returnFragment : Fragment? = null

        when (position) {
            0 -> returnFragment = FreeBoardListFragment()
            1 -> returnFragment = CareBoardListFragment()
            2 -> returnFragment = WalkBoardListFragment()
            3 -> returnFragment = ShowBoardListFragment()
        }
        return returnFragment!!
    }
}

