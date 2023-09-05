package com.example.filmust.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.filmust.workdata.PageAdapter
import com.example.filmust.workdata.PageData
import com.example.filmust.R
import com.example.filmust.databinding.FragmentIntroBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle


class IntroFragment : Fragment(R.layout.fragment_intro) {
    var binding: FragmentIntroBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIntroBinding.bind(view)

        val data = listOf(
            PageData(
                image = R.drawable.baseline_local_movies_24,
                title = getString(R.string.intro_first_title),
                subtitle = getString(R.string.intro_first_subtitle)
            ),
            PageData(
                image = R.drawable.baseline_favorite_24,
                title = getString(R.string.intro_second_title),
                subtitle = getString(R.string.intro_second_subtitle)
            ),
            PageData(
                image = R.drawable.baseline_person_24,
                title = getString(R.string.intro_third_title),
                subtitle = getString(R.string.intro_third_subtitle)
            )
        )

        val adapter = PageAdapter(data)
        val viewPager = binding!!.viewPager
        viewPager.adapter = adapter

        val indicatorView = binding!!.indicatorView
        indicatorView.apply {
            setSliderColor(R.color.primary, R.color.primary)
            setSliderWidth(
                resources.getDimension(R.dimen.default_6dp),
                resources.getDimension(R.dimen.default_16dp)
            )

            setSliderHeight(resources.getDimension(R.dimen.default_6dp))
            setSlideMode(IndicatorSlideMode.SCALE)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setPageSize(viewPager.adapter!!.itemCount)
            notifyDataChanged()
        }


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorView.onPageSelected(position)
                val btn = binding!!.buttonNext
                when (position) {
                    0, 1 -> btn.setText(R.string.next)
                    2 -> btn.setText(R.string.start)
                }
            }
        })

        binding!!.buttonNext
            .setOnClickListener {
                var position = viewPager.currentItem
                when (position) {
                    0, 1 -> viewPager.setCurrentItem(++position)
                    2 -> {
                        this.activity?.findViewById<BottomNavigationView>(R.id.bnv_main)?.visibility = View.VISIBLE
                        findNavController().navigate(R.id.action_introFragment_to_signInFragment)
                    }
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}