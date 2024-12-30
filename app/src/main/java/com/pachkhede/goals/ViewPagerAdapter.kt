package com.pachkhede.goals

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (
    fragmentActivity : FragmentActivity,
    private val fragments : List<Fragment>,
    private val fragmentsTitle : List<String>
) : FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}