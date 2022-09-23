package com.ekades.fcmpushnotification.ui.huaweimap

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ekades.fcmpushnotification.R
import com.huawei.hms.location.*
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker


class HuaweiMapActivity() : AppCompatActivity(), OnMapReadyCallback {
    private var hmap: HuaweiMap? = null
    private var mMapView: MapView? = null
    private val mMarker: Marker? = null
    private val latLngList: List<LatLng>? = null
    private var mLocationCallback: LocationCallback? = null
    private var mLocationRequest: LocationRequest? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var settingsClient: SettingsClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mMapView = findViewById(R.id.mapView)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView?.run {
            if (savedInstanceState != null) {
                mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
            }
            onCreate(mapViewBundle)
            getMapAsync(this@HuaweiMapActivity)
        }
    }

    private fun init() {
        setContentView(R.layout.huawei_map_activity)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        toolbar.setTitle("Huawei Map")
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        settingsClient = LocationServices.getSettingsClient(this)
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 10000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (null == mLocationCallback) {
            mLocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    if (locationResult != null) {
                        val locations: List<Location> = locationResult.locations
                        if (!locations.isEmpty()) {
                            for (location: Location in locations) {
                                Log.i(
                                    TAG,
                                    "onLocationResult location[Longitude,Latitude,Accuracy]:" + location.getLongitude()
                                        .toString() + "," + location.getLatitude()
                                        .toString() + "," + location.getAccuracy()
                                )
                            }
                        }
                    }
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                    if (locationAvailability != null) {
                        val flag = locationAvailability.isLocationAvailable
                        Log.i(TAG, TAG + flag)
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i(TAG, "sdk < 28 Q")
            if ((ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED)
            ) {
                val strings = arrayOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(this, strings, 1)
            }
        } else {
            if ((ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED
                        ) && (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED
                        ) && (ActivityCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                ) !== PackageManager.PERMISSION_GRANTED)
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                )
                ActivityCompat.requestPermissions(this, strings, 2)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mMapView!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        mMapView!!.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onMapReady(map: HuaweiMap) {
        hmap = map
        hmap!!.isMyLocationEnabled = true
        hmap!!.isTrafficEnabled = true
        hmap!!.uiSettings.isRotateGesturesEnabled = true
        hmap!!.uiSettings.isCompassEnabled = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if ((grantResults.size > 1) && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        ) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                Log.i(TAG, "onRequestPermissionsResult: apply LOCATION PERMISSION successful")
            } else {
                Log.i(TAG, "onRequestPermissionsResult: apply LOCATION PERMISSSION  failed")
            }
        }
        if (requestCode == 2) {
            if ((grantResults.size > 2) && (grantResults[2] == PackageManager.PERMISSION_GRANTED
                        ) && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        ) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                Log.i(
                    TAG,
                    "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful"
                )
            } else {
                Log.i(TAG, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION  failed")
            }
        }
    }

    companion object {
        val TAG = "HuaweiMapActivity"
        private val MAPVIEW_BUNDLE_KEY =
            "CgB6e3x9y1L9ALnUEQjcG/+euAR9NgNwR3iOeEX8ZUrRRfGugRSeGlKtlSj5mh7XKH8HzBpWfkLP6R3OPhgT2vFc"
    }
}