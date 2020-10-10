package com.azhar.weatherapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.weatherapp.R
import com.azhar.weatherapp.adapter.NextDayAdapter
import com.azhar.weatherapp.model.ModelNextDay
import com.azhar.weatherapp.networking.ApiEndpoint
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_next_day.view.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Azhar Rivaldi on 07-10-2020
 */

class FragmentNextDays : BottomSheetDialogFragment(), LocationListener {

    var lat: Double? = null
    var lng: Double? = null
    var nextDayAdapter: NextDayAdapter? = null
    var rvListWeather: ShimmerRecyclerView? = null
    var fabClose: FloatingActionButton? = null
    var modelNextDays: MutableList<ModelNextDay> = ArrayList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_next_day, container, false)

        nextDayAdapter = NextDayAdapter(activity!!, modelNextDays)
        rvListWeather = rootView.rvListWeather
        rvListWeather?.setLayoutManager(LinearLayoutManager(activity))
        rvListWeather?.setHasFixedSize(true)
        rvListWeather?.setAdapter(nextDayAdapter)
        rvListWeather?.showShimmerAdapter()

        fabClose = rootView.findViewById(R.id.fabClose)
        fabClose?.setOnClickListener({
            dismiss()
        })

        //method get LatLong
        getLatLong()

        return rootView
    }

    @SuppressLint("MissingPermission")
    private fun getLatLong () {
            val locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val criteria = Criteria()
            val provider = locationManager.getBestProvider(criteria, true)
            val location = locationManager.getLastKnownLocation(provider)
            if (location != null) {
                onLocationChanged(location)
            } else {
                locationManager.requestLocationUpdates(provider, 20000, 0f, this)
            }
        }

    override fun onLocationChanged(location: Location) {
        lng = location.longitude
        lat = location.latitude
        Handler().postDelayed({
            //method get Data Weather
            getListWeather()
        }, 3000)
    }

    private fun getListWeather() {
            AndroidNetworking.get(ApiEndpoint.BASEURL + ApiEndpoint.Daily + "lat=" + lat + "&lon=" + lng + ApiEndpoint.UnitsAppidDaily)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            try {
                                val jsonArray = response.getJSONArray("list")
                                for (i in 0 until jsonArray.length()) {
                                    val dataApi = ModelNextDay()
                                    val objectList = jsonArray.getJSONObject(i)
                                    val jsonObjectOne = objectList.getJSONObject("temp")
                                    val jsonArrayOne = objectList.getJSONArray("weather")
                                    val jsonObjectTwo = jsonArrayOne.getJSONObject(0)
                                    val longDate = objectList.optLong("dt")
                                    val formatDate = SimpleDateFormat("d MMM yy")
                                    val readableDate = formatDate.format(Date(longDate * 1000))
                                    val longDay = objectList.optLong("dt")
                                    val format = SimpleDateFormat("EEEE")
                                    val readableDay = format.format(Date(longDay * 1000))

                                    dataApi.nameDate = readableDate
                                    dataApi.nameDay = readableDay
                                    dataApi.descWeather = jsonObjectTwo.getString("description")
                                    dataApi.tempMin = jsonObjectOne.getDouble("min")
                                    dataApi.tempMax = jsonObjectOne.getDouble("max")
                                    modelNextDays.add(dataApi)
                                }
                                nextDayAdapter?.notifyDataSetChanged()
                                rvListWeather?.hideShimmerAdapter()
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Toast.makeText(activity, "Gagal menampilkan data!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onError(anError: ANError) {
                            Toast.makeText(activity, "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show()
                        }
                    })
        }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
    override fun onProviderEnabled(s: String) {}
    override fun onProviderDisabled(s: String) {}

    companion object {
        fun newInstance(string: String?): FragmentNextDays {
            val fragmentNextDays = FragmentNextDays()
            val args = Bundle()
            args.putString("string", string)
            fragmentNextDays.arguments = args
            return fragmentNextDays
        }
    }
}