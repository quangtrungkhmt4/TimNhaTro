package com.example.quang.timnhatro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private GoogleMap mMap;
    private Location myLocation;
    private Marker myMarker;
    private Circle myCircle;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        findID();
        initViews();

    }


    private void findID() {
        toolbar = findViewById(R.id.toolbarMap);
        drawerLayout = findViewById(R.id.drawerLayout);

    }

    @SuppressLint("MissingPermission")
    private void initViews(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,0,0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] permissionList = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : permissionList) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissionList, 0);
                    return;
                }
            }
        }
        initMap();
    }

    @SuppressLint("MissingPermission")
    private void initMap() {
        geocoder = new Geocoder(this);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LocationManager manager = (LocationManager)
                getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);

//        mMap.setOnMapClickListener(this);
//        mMap.setOnMapLongClickListener(this);
//        mMap.setOnMarkerClickListener(this);
//        mMap.setOnInfoWindowClickListener(this);

    }

    private Marker drawMarker(LatLng position, int drawable, String title, String snippet) {
        MarkerOptions options = new MarkerOptions();
        options.position(position);
        options.title(title);
        options.snippet(snippet);
        options.icon(BitmapDescriptorFactory.fromResource(drawable));

        return mMap.addMarker(options);
    }

    private Circle drawCircle(LatLng position){
        return mMap.addCircle(new CircleOptions()
                .center(position)
                .radius(100)
                .strokeWidth(0f)
                .fillColor(0x550000FF));
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (myMarker == null && myCircle == null) {
            CameraPosition cameraPosition = new CameraPosition(latLng, 17, 0, 0);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            myMarker = drawMarker(latLng, R.drawable.my_marker, "Vị trí của bạn", "");
            myCircle = drawCircle(latLng);


            } else {
                myMarker.setPosition(latLng);
                myCircle.setCenter(latLng);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


//    public void loadFragment(Fragment fragment) {
//        // load fragment
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove(manager.getFragments().get(manager.getFragments().size() - 1));
//            trans.commit();
//            manager.popBackStack();
//        }
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.panel, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}
