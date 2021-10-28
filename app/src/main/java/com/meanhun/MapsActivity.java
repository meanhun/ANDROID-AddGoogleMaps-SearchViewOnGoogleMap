package com.meanhun;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meanhun.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addControls();
        addEvents();
    }

    private void addEvents() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                ArrayList<Address> list_address = null;
                if (!location.isEmpty()){
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        list_address = (ArrayList<Address>) geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    Address address = list_address.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,10);
                    mMap.animateCamera(update);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    private void addControls() {
        searchView = findViewById(R.id.reSearch);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng vietNam = new LatLng(10.964112, 106.856461);
        mMap.addMarker(new MarkerOptions().position(vietNam).title("Bien Hoa").snippet("Hwang mean hun"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vietNam));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(vietNam,10);
        mMap.animateCamera(update);
        LatLng vietNam2 = new LatLng(10.964112, 106.756461);
        mMap.addMarker(new MarkerOptions().position(vietNam2).title("Bien Hoa2").snippet("Hwang mean hun2"));
        LatLng vietNam3 = new LatLng(10.964112, 106.656465);
        mMap.addMarker(new MarkerOptions().position(vietNam3).title("Bien Hoa3").snippet("Hwang mean hun3"));
    }
}