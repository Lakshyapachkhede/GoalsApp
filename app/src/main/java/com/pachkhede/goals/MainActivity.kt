package com.pachkhede.goals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout : TabLayout = findViewById(R.id.tabLayout)
        val viewPager : ViewPager2 = findViewById(R.id.viewPager)

        val fragments = listOf(
            TodayFragment(),
            HistoryFragment()
        )

        val fragmentTitles = listOf(
            "Today",
            "History"
        )


        val adapter = ViewPagerAdapter(this, fragments, fragmentTitles)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = fragmentTitles[position]
        }.attach()


    }

    private fun scheduleNotificationChannel() {
        TODO()
    }


    private fun createNotificationChannel() {
        val name = "Morning and Night Notification"
        val desc = "Notifies you daily in morning and night"
        TODO()
    }



}