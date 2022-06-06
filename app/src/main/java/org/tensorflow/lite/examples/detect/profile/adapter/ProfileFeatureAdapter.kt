package org.tensorflow.lite.examples.detect.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.tensorflow.lite.examples.detect.profile.fragment.ProfileDetectFragment
import org.tensorflow.lite.examples.detect.profile.fragment.ProfilePostFragment

class ProfileFeatureAdapter (fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf(ProfilePostFragment(), ProfileDetectFragment())

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}