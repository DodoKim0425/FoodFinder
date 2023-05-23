package com.ssafy.foodfind.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.ssafy.foodfind.R
import com.ssafy.foodfind.data.entity.TruckLocation
import java.util.*

object MapFragmentWrapper : Fragment(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var mapFragment: MapFragment
    lateinit var naverMap: NaverMap
    private lateinit var markerQueue: Queue<TruckLocation>
    private var isMapReady = false


    private fun processMarkerQueue() {
        while (!markerQueue.isEmpty()) {
            val location = markerQueue.poll()
            addMarker(location)
        }
    }

    fun addMarker(item: TruckLocation) {
        if (isMapReady) {
            val marker = Marker()
            marker.position = LatLng(item.latitude, item.longitude)
            marker.map = naverMap
            marker.onClickListener = this
            marker.tag = item.truckId
        } else {
            markerQueue.offer(item)
        }
    }

    fun setPosition(item: TruckLocation) {
        if (::naverMap.isInitialized) {
            val cameraPosition = CameraPosition(
                LatLng(item.latitude, item.longitude),
                14.0
            )
            naverMap.cameraPosition = cameraPosition
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        val fm = childFragmentManager
        mapFragment = fm.findFragmentById(R.id.mapFragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapFragment, it).commit()
            }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment.getMapAsync(this)
        markerQueue = LinkedList()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        isMapReady = true
        processMarkerQueue()
    }

    private lateinit var markerClickListener: ((Int) -> Unit)

    fun setMarkerClickListener(listener: (Int) -> Unit) {
        this.markerClickListener = listener
    }

    override fun onClick(p0: Overlay): Boolean {
        if (p0 is Marker) {
            val truckId = p0.tag as? Int
            if (truckId != null) {
                markerClickListener.invoke(truckId)
            }
        }
        return true
    }
}
