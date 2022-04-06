package com.alizaidi.aliabbasedvora.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alizaidi.aliabbasedvora.R
import com.alizaidi.aliabbasedvora.Repository.Repo
import com.alizaidi.aliabbasedvora.Ride
import com.alizaidi.aliabbasedvora.User
import com.alizaidi.aliabbasedvora.adapter.RideListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingRide(pr: ArrayList<Ride>) :Fragment(R.layout.fragment_page1) {
    private lateinit var mAdapter: RideListAdapter
//    val repo: Repo = Repo()
//    val sc=user.station_code
    val pr=pr
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView=view.findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = RideListAdapter()
//        fetcData()
        mAdapter.updateRide(pr)
        recyclerView.adapter = mAdapter
    }

//    private fun fetcData() {
//        var data=ArrayList<Ride>()
//        CoroutineScope(Dispatchers.IO).launch {
//            repo.getUpcomingRide(sc)
//            if (repo.status == 1) {
//                data=repo.ride
//                withContext(Dispatchers.Main) {
//                    mAdapter.updateRide(data)
//                }
//            }
//        }
//    }
}