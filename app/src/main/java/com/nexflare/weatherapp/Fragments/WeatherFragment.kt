package com.nexflare.weatherapp.Fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexflare.weatherapp.R
import kotlinx.android.synthetic.main.fragment_weather.*


/**
 * Created by nexflare on 04/10/17.
 */
class WeatherFragment : Fragment() {

    private  var viewFragment:View?=null
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intializeComponents()
        weatherVP.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0->{
                        currentTv.setTextColor(Color.BLACK)
                        dailyTv.setTextColor(resources.getColor(R.color.unSelected))
                        weatherVP.currentItem=0
                    }
                    1->{
                        currentTv.setTextColor(resources.getColor(R.color.unSelected))
                        dailyTv.setTextColor(Color.BLACK)
                        weatherVP.currentItem=1
                    }
                }
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewFragment = inflater?.inflate(R.layout.fragment_weather, container,false)
        return viewFragment
    }

    private fun intializeComponents() {
        val pageAdapter= PageAdapter(childFragmentManager)
        weatherVP.adapter=pageAdapter
        weatherVP.currentItem=0
        weatherVP.offscreenPageLimit=2

    }


    companion object {
        val instance: WeatherFragment
            get() = WeatherFragment()

    }

    class PageAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            when(position){
                0->
                    return CurrentWeatherFragment.instance

                1->
                    return DailyWeatherFragment.instance

                else->
                    return CurrentWeatherFragment.instance
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
