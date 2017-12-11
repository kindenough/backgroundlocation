package com.amap.androidobackgroundlocation;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    MapView mMapView = null;
    private MarkerOptions markerOption;
    private AMap aMap;
    private LatLng latlng = new LatLng(24.481128, 118.185798);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // 获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        // 在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
            //addMarkersToMap();// 往地图上添加marker
        }

        UiSettings mUiSettings;//定义一个UiSettings对象
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setCompassEnabled(true);

        //aMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }
    private void addMarkersToMap() {
        // TODO Auto-generated method stub
        markerOption = new MarkerOptions()
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latlng).draggable(true);
        aMap.addMarker(markerOption);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
    }

    @Override
    public void onClick(View view) {

    }
}