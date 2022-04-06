package com.alizaidi.aliabbasedvora.Repository

import android.util.Log
import com.alizaidi.aliabbasedvora.Ride
import com.alizaidi.aliabbasedvora.User
import com.alizaidi.aliabbasedvora.retrofit.instance
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Repo {
    var ride:ArrayList<Ride> = ArrayList()
     var user:User=User()
    var status=0

    suspend fun getRide() {
        val response =try {
            instance.mainInstance.getRide()
        }
        catch (e: IOException) {
            Log.e("TAG", "IOException, you might not have internet connection $e")
            status = -1;
            return
        } catch (e: HttpException) {
            status = 0;
            Log.e("TAG", "HttpException, unexpected response")
            return
        }
        if (response.isSuccessful && response.body() != null) {
                    ride=response.body()!! as ArrayList<Ride>
            status = 1;

        }
    }


    suspend fun getNearestRide(sc:Int) {
            val response =try {
                instance.mainInstance.getRide()
            }
            catch (e: IOException) {
                Log.e("TAG", "IOException, you might not have internet connection $e")
                status = -1;
                return
            } catch (e: HttpException) {
                status = 0;
                Log.e("TAG", "HttpException, unexpected response")
                return
            }
            if (response.isSuccessful && response.body() != null) {
                ride= ArrayList()
            for(i in response.body()!!)
            {
                if(i.origin_station_code==sc || i.station_path.contains(sc)) {
                   val r=i
                    r.distance= (i.destination_station_code-sc).toString()
                    ride.add(r)
                }
            }
                status = 1;

            }
            }

    suspend fun getUpcomingRide(sc: Int) {
        val response =try {
            instance.mainInstance.getRide()
        }
        catch (e: IOException) {
            Log.e("TAG", "IOException, you might not have internet connection $e")
            status = -1;
            return
        } catch (e: HttpException) {
            status = 0;
            Log.e("TAG", "HttpException, unexpected response")
            return
        }
        if (response.isSuccessful && response.body() != null) {
            ride= ArrayList()

            for(i in response.body()!!)
            {
//                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val date = sdf.parse(i.date.substring(0,i.date.indexOf(' ')));

                val now= Calendar.getInstance();
                val yourDate = Calendar.getInstance();
                yourDate.timeInMillis=date.time
                now.before(yourDate);
                if(now.timeInMillis - yourDate.timeInMillis>0)
                    { val r=i
                        r.distance= (i.destination_station_code-sc).toString()
                        ride.add(r)
                    }

            }
            }
            status = 1;

        }



    suspend fun getUser() {

            val response = try{
                instance.mainInstance.getUser()
            } catch (e: IOException) {
                Log.e("TAG", "IOException, you might not have internet connection $e")
                status = -1;
                return
            } catch (e: HttpException) {
                status = 0;
                Log.e("TAG", "HttpException, unexpected response")
                return
            }
        if (response.isSuccessful && response.body() != null) {
            status = 1;
            user= response.body()!!
            Log.e("RETROFIT_ERROR", user.toString())
            } else
            {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }

    suspend fun getPastRide(sc: Int) {
        val response =try {
            instance.mainInstance.getRide()
        }
        catch (e: IOException) {
            Log.e("TAG", "IOException, you might not have internet connection $e")
            status = -1;
            return
        } catch (e: HttpException) {
            status = 0;
            Log.e("TAG", "HttpException, unexpected response")
            return
        }
        if (response.isSuccessful && response.body() != null) {
            ride= ArrayList()

            for(i in response.body()!!)
            {
//                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val date = sdf.parse(i.date.substring(0,i.date.indexOf(' ')));

                val now= Calendar.getInstance();
                val yourDate = Calendar.getInstance();
                yourDate.timeInMillis=date.time
                now.before(yourDate);
                if(now.timeInMillis - yourDate.timeInMillis<0)
                { val r=i
                    r.distance= (i.destination_station_code-sc).toString()
                    ride.add(r)
                }

            }
        }
        status = 1;

    }
}