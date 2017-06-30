package com.example.xunhu.xunchat.View.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;

/**
 * Created by xunhu on 6/20/2017.
 */


@SuppressLint("ValidFragment")
public class LocationListDialog extends DialogFragment {
    @BindView(R.id.tv_searching_location)
    TextView tvSearching;
    @BindView(R.id.lv_locations)
    ListView lvLocations;
    Unbinder unbinder;
    View view;
    LocationManager locationManager;
    LocationListener locationListener;
    double lat=0;
    double lon=0;
    private static int ACCESS_LOCATION = 0;
    private List<String> list = new ArrayList<>();
    private ArrayAdapter arrayAdapter;
    LocationDialogInterface comm;
    String type = "";
    @SuppressLint("ValidFragment")
    public LocationListDialog(String type){
        this.type=type;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.list_locations_layout,null);
        unbinder = ButterKnife.bind(this,view);
        arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
        retrieveLocations();
        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeLocationListener(locationListener);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm= (LocationDialogInterface) activity;
    }

    @OnItemClick({R.id.lv_locations})
    public void respond(AdapterView<?> parent, View view, int position, long id){
        if (type.equals("edit")){
            comm.setEditLocation("edit region",list.get(position));
        }else {
            comm.setSelectedLocation(list.get(position));
        }

        onDestroyView();
    }

    private void retrieveLocations(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location.getLatitude()!=0.0 && location.getLongitude()!=0.0){
                    try {
                        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses = null;
                        addresses = gcd.getFromLocation(location.getLatitude(),  location.getLongitude(), 10);
                        if (addresses.size()>0){
                            for (int i=0;i<addresses.size();i++){
                                list.add(addresses.get(i).getAddressLine(0).toString());
                            }
                            lvLocations.setAdapter(arrayAdapter);
                            tvSearching.setText("Select your prefered location");
                        }else {
                            tvSearching.setText("Fail to load your location");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    removeLocationListener(this);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,0,locationListener);
            }else {

                requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_LOCATION);
            }
        }else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,0,locationListener);
        }
    }
    public void removeLocationListener(LocationListener locationListener){
        if (locationManager!=null){
            locationManager.removeUpdates(locationListener);
            locationManager=null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==ACCESS_LOCATION){
            if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity().getApplicationContext(),"we need access to your location",Toast.LENGTH_SHORT).show();
            }else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,0,locationListener);
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeLocationListener(locationListener);
    }
    public interface LocationDialogInterface{
        void setSelectedLocation(String location);
        void setEditLocation(String title,String content);
    }

}
