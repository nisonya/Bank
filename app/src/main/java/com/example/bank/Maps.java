package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(this);

    }


    private GoogleMap map;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map= googleMap;
        map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(bank1, 14)
        );
        map.addMarker(
                new MarkerOptions().position(bank1).title("Банк").snippet("ул. Бассейная, 41")
        );
        map.addMarker(
                new MarkerOptions().position(bank2).title("Банк").snippet("ул. Бассейная, 17, лит. А")
        );
        map.addMarker(
                new MarkerOptions().position(bank3).title("Банк").snippet("пр-кт Московский, 189")
        );
        map.addMarker(
                new MarkerOptions().position(bank4).title("Банк").snippet("пр-кт, Московский пр-т., 212")
        );
        map.addMarker(
                new MarkerOptions().position(bank5).title("Банк").snippet("Ленинский пр-т., 160, лит. А")
        );
        map.addMarker(
                new MarkerOptions().position(bank6).title("Банк").snippet("ул. Бассейная, 17")
        );
    }
    private LatLng bank1= new LatLng(59.8646, 30.32204);
    private LatLng bank2= new LatLng(59.86401, 30.30632);
    private LatLng bank3= new LatLng(59.85423, 30.3207);
    private LatLng bank4= new LatLng(59.8513, 30.32663);
    private LatLng bank5= new LatLng(59.85268, 30.30138);
    private LatLng bank6= new LatLng(59.86397, 30.30628);
    public void findbank(View view) {
        if(map!=null){

        }
    }
    public void Main(View view) {
        this.finish();
    }
}