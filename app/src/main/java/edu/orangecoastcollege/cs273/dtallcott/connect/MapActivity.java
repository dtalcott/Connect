package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Student selectedStudent;
    private SupportMapFragment mSupportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        selectedStudent = getIntent().getParcelableExtra("SelectedStudent");

        mSupportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.studentLocationMapFragment);
        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng studentLocation = new LatLng(33.671028, -117.911305);
        mMap.addMarker(new MarkerOptions()
                .title(selectedStudent.getFullName())
                .icon(BitmapDescriptorFactory.fromAsset(selectedStudent.getImageName()))
                .position(studentLocation));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(studentLocation)
                .zoom(18.0f)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
    }
}
