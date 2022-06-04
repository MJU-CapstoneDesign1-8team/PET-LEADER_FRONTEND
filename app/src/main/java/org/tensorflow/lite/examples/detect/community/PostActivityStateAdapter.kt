package org.tensorflow.lite.examples.detect.community

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PostActivityStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        var returnFragment : Fragment? = null

        if(position == 0){
            returnFragment = CommunityFragmentList1()
        }else if(position == 1){
            returnFragment = CommunityFragmentList1()
        }else if(position == 2){
            returnFragment = CommunityFragmentList1()
        }else if(position == 3){
            returnFragment = CommunityFragmentList1()
        }
        return returnFragment!!
    }
}

