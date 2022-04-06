package com.alizaidi.aliabbasedvora

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.alizaidi.aliabbasedvora.Repository.Repo
import com.alizaidi.aliabbasedvora.ui.NearestRide
import com.alizaidi.aliabbasedvora.ui.PastRide
import com.alizaidi.aliabbasedvora.ui.UpcomingRide
import com.alizaidi.aliabbasedvora.viewmodel.MainVM
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(){
    val repo= Repo()

    lateinit var user: User
    lateinit var uname:TextView
    lateinit var udp:ImageView
    lateinit var vm:MainVM
    lateinit var cityS:MaterialSpinner
    var place=HashMap<String,ArrayList<String>>()
    var stateList = ArrayList<String>()
    var City = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)
        vm = ViewModelProvider(this).get(MainVM::class.java)
        uname=findViewById(R.id.uname)
        udp=findViewById(R.id.userDp)
            CoroutineScope(Dispatchers.IO).launch {
                repo.getRide()
                if (repo.status == 1) {
                    vm.ride=repo.ride
                    withContext(Dispatchers.Main){
                        fetchUserDetail()
                        for(i in vm.ride) {
                            if (!place.containsKey(i.state)) {
                                place[i.state] = ArrayList()
                                place[i.state]!!.add(i.city)
                            }
                        }
                            Log.e("places",place.toString())
                            for (i in place.keys)
                                stateList.add(i)
                            val fltr=findViewById<TextView>(R.id.fltr)
                            fltr.setOnClickListener(){
                                showDialog("sss")

                        }
                    }
                }
            }


    }
    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_layout)
        cityS=dialog.findViewById<MaterialSpinner>(R.id.cityname)
        val spinner = dialog.findViewById<View>(R.id.stateName) as MaterialSpinner
        spinner.setItems(stateList)
        spinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
            cityS.isClickable=true
            City= ArrayList()
            for(i in place.get(stateList[position])!!)
            {
                City.add(i)
            }
            cityS.setItems(City)
        }

        val window: Window = dialog.window!!
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
        val wlp = window.attributes

        wlp.gravity = Gravity.END or Gravity.TOP
//        wlp.x = 10;   //x position
        wlp.y = 350;
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv() and WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL and WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        window.attributes = wlp
        dialog.show()

    }


    private fun fetchUserDetail() {
        CoroutineScope(Dispatchers.IO).launch {
             repo.getUser()
            if (repo.status == 1) {
                withContext(Dispatchers.Main){
                    user=repo.user
                    val nr=vm.getNearestRide(user.station_code)
                    val snr=nr.sortedWith(compareBy { it.distance })
                    val ur=vm.getUpcomingRide(user.station_code)
                    val pr=vm.getPastRide(user.station_code)
                    uname.text=user.name
                    Glide.with(applicationContext).load(user.url).into(udp)
                    val viewPager = findViewById<ViewPager>(R.id.viewpager)
                    val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

                    viewPagerAdapter.add(NearestRide(snr), "Nearest (${snr.size})")
                    viewPagerAdapter.add(UpcomingRide(ur), "Upcoming (${ur.size})")
                    viewPagerAdapter.add(PastRide(pr), "Past (${pr.size})")

                    viewPager.adapter = viewPagerAdapter

                    val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
                    tabLayout.setupWithViewPager(viewPager)
                }
            }
        }

    }

//
//    private fun fetchUserDetail() {
//
//        CoroutineScope(Dispatchers.IO).launch {
//            repo.getUser()
//            if (repo.status == 1) {
//                user=repo.user
//
//                withContext(Dispatchers.Main) {
//                    uname.text=user.name
//                    Glide.with(applicationContext).load(user.url).into(udp)
//                    val viewPager = findViewById<ViewPager>(R.id.viewpager)
//                    val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
//
//                    viewPagerAdapter.add(NearestRide(user), "Nearest 1")
//                    viewPagerAdapter.add(UpcomingRide(user), "Upcoming 2")
//                    viewPagerAdapter.add(PastRide(user), "Past 3")
//
//                    viewPager.adapter = viewPagerAdapter
//
//                    val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
//                    tabLayout.setupWithViewPager(viewPager)
//                }
//            }
//        }
//    }
}