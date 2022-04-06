package com.alizaidi.aliabbasedvora.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alizaidi.aliabbasedvora.Repository.Repo
import com.alizaidi.aliabbasedvora.Ride
import com.alizaidi.aliabbasedvora.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainVM:ViewModel() {
     lateinit var ride:ArrayList<Ride>
private  var repo: Repo=Repo()



    fun getUser(): User {
        var user= User()
        CoroutineScope(Dispatchers.IO).launch {
            repo.getUser()
            if (repo.status == 1) {
                user=repo.user
            }
        }
        return user
    }


    fun getNearestRide(sc: Int): ArrayList<Ride> {
        var nr=ArrayList<Ride>()
        for(i in ride)
                {
                    if(i.origin_station_code==sc || i.station_path.contains(sc)) {
                        val r=i
                        r.distance= (i.destination_station_code-sc).toString()
                        nr.add(r)
                    }
                }


        return nr
    }

    fun getUpcomingRide(sc:Int): ArrayList<Ride> {
        var ur=ArrayList<Ride>()
                for(i in ride)
                {
//                val sdf = SimpleDateFormat("yyyy-MM-dd")
//                    val sdf = SimpleDateFormat("MM/dd/yyyy")
//                    val date = sdf.parse(i.date.substring(0,i.date.indexOf(' ')));
                    val day= i.date.substring(0,2).toInt()
                    val mon=i.date.substring(3,5).toInt()
                    val year=i.date.substring(6,10).toInt()
                    var hour=i.date.substring(11,13).toInt()
                    val min=i.date.substring(14,16).toInt()
                    val re=i.date.substring(17)
                    if(re=="PM")
                        hour+=12;
                    val now= Calendar.getInstance();
                    val yourDate = Calendar.getInstance();
                    yourDate.set(year,mon,day,hour,min)
                    Log.e("now date",now.toString())
                    if(now.timeInMillis - yourDate.timeInMillis<0)
                    { val r=i
                        r.distance= (i.destination_station_code-sc).toString()
                        ur.add(r)
                    }

                }


        return ur
    }

    fun getPastRide(sc:Int): ArrayList<Ride> {
        var pr=ArrayList<Ride>()
                for(i in ride)
                {
               val day= i.date.substring(0,2).toInt()
                    val mon=i.date.substring(3,5).toInt()
                    val year=i.date.substring(6,10).toInt()
                    var hour=i.date.substring(11,13).toInt()
                    val min=i.date.substring(14,16).toInt()
                    val re=i.date.substring(17)
                    if(re=="PM")
                        hour+=12;
                    val now= Calendar.getInstance();
                    val yourDate = Calendar.getInstance();
                    yourDate.set(year,mon,day,hour,min)
                    Log.e("now date","${now.timeInMillis - yourDate.timeInMillis}")
                    if(now.timeInMillis - yourDate.timeInMillis>0)
                    { val r=i
                        r.distance= (i.destination_station_code-sc).toString()
                        pr.add(r)
                    }

                }

        return pr
    }


}