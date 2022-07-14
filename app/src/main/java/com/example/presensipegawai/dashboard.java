package com.example.presensipegawai;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.presensipegawai.API.API;
import com.example.presensipegawai.API.APIUtility;
import com.example.presensipegawai.Models.Presensi;
import com.example.presensipegawai.Models.TokenAPI;
import com.example.presensipegawai.Services.GPSService;
import com.example.presensipegawai.Services.Utils;
import com.example.presensipegawai.SharePref.SharPref;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dashboard extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "resPMain";

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private static final double lat1 = -6.2402437;
    private static final double long1 = 106.6690663;

    private MyReceiver myReceiver;

    private GPSService mService = null;

    private boolean mBound = false;

    private CardView cardMasuk;
    private TextView textMasuk;
    private CardView cardPulang;
    private TextView textPulang;
    private CardView profile;

    private API apiService;
    private SharPref sharPref;
    private Double longtitude = 0.00;
    private Double latitude = 0.00;


    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            GPSService.LocalBinder binder = (GPSService.LocalBinder) service;
            mService = binder.getService();
            mService.requestLocationUpdates(); // also request it here
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            ServiceConnection.super.onBindingDied(name);
        }

        @Override
        public void onNullBinding(ComponentName name) {
            ServiceConnection.super.onNullBinding(name);
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new MyReceiver();

        if (Utils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }
        setContentView(R.layout.activity_dashboard);
        sharPref = new SharPref(this);
        apiService = APIUtility.getAPI();
        cardMasuk = findViewById(R.id.cardMasuk);
        cardPulang = findViewById(R.id.cardPulang);
        profile = findViewById(R.id.profile_card);
        //absen masuk
        cardMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+getLatitude());
                if(getLatitude().equals(0.00)){
                    Snackbar.make(
                            findViewById(R.id.activity_main),
                            "Lokasi tidak terdeteksi. Mohon coba lagi",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }else{
                    apiService.absenMasuk("Bearer "+sharPref.getTokenApi(), getLatitude(), getLongtitude()).enqueue(new Callback<Presensi>() {
                        @Override
                        public void onResponse(Call<Presensi> call, Response<Presensi> response) {
                            sharPref.setIdPresensi(response.body().getIdPresensi());
                            Log.d("reponse post", "onResponse: "+response.body().getIdPegawai());
                        }

                        @Override
                        public void onFailure(Call<Presensi> call, Throwable t) {
                            Log.d(TAG, "onFailure: "+t.toString());
                        }
                    });
                }
            }
        });

        //absen pulang
        cardPulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService.absenPulang("Bearer "+sharPref.getTokenApi(), sharPref.getIdPresensi()).enqueue(new Callback<Presensi>() {
                    @Override
                    public void onResponse(Call<Presensi> call, Response<Presensi> response) {

                    }

                    @Override
                    public void onFailure(Call<Presensi> call, Throwable t) {

                    }
                });
            }
        });

        //profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(dashboard.this, com.example.presensipegawai.profile.class);
                startActivity(dashboard);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        if (!checkPermissions()) {
            requestPermissions();
        } else if (mService != null) { // add null checker
            mService.requestLocationUpdates();
        }


        cardMasuk = (CardView) findViewById(R.id.cardMasuk);
        textMasuk = (TextView) findViewById(R.id.text_masuk);

        cardPulang = (CardView) findViewById(R.id.cardPulang);
        textPulang = (TextView) findViewById(R.id.text_pulang);

        bindService(new Intent(this, GPSService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(GPSService.ACTION_BROADCAST));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {

            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    /**
     * Returns the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(dashboard.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(dashboard.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                mService.requestLocationUpdates();
            } else {
                // Permission denied.
//                setButtonsState(false);
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        getApplicationContext().getPackageName(), null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    /**
     * Receiver for broadcasts sent by {@link GPSService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(GPSService.EXTRA_LOCATION);
            Log.d("location", Utils.getLocationText(location));
            double radius = getRadius(lat1, long1, location.getLatitude(), location.getLongitude());
            Log.d("location jarak", Double.toString(radius));

            if(location != null && radius < 10){
                cardMasuk.setEnabled(true);
                textMasuk.setText("Presensi Masuk");
            }else{
                cardMasuk.setEnabled(false);
                textMasuk.setText("Anda harus berada di radius 10 meter");
//                Toast.makeText(login.this, Utils.getLocationText(location),
////                        Toast.LENGTH_SHORT).show();
            }

            if (location != null) {
                setLatitude(location.getLatitude());
                setLongtitude(location.getLongitude());
//                Toast.makeText(login.this, Utils.getLocationText(location),
//                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Update the buttons state depending on whether location updates are being requested.
        if (s.equals(Utils.KEY_REQUESTING_LOCATION_UPDATES)) {
//            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES,
//                    false));
        }
    }


    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    private static double getRadius(double lat1, double lng1, double lat2, double lng2){
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lng1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);
        double distanceInMeters = loc1.distanceTo(loc2);
        return distanceInMeters;
    }
}
