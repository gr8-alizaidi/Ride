package com.alizaidi.aliabbasedvora

data class Ride(
    var id:Int=0,
    val station_path:ArrayList<Int>,
    var destination_station_code:Int=0,
    val origin_station_code:Int=0,
    val date:String="",
    var distance:String="",
    val map_url:String="",
    val state:String="",
    val city:String=""
) {
}