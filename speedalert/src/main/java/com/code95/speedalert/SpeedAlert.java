package com.code95.speedalert;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;


/**
 * Speed Alert class monitors movement speed and
 * plays an alert when exceeding set speed.
 *
 * @Author Yara Abdelhakim
 * @Version 1.0.0
 * @Since 18/3/2018
 */

public class SpeedAlert implements LocationListener {

    private LocationManager mLocationManager;
    private Context mContext;
    private double mMaxSpeed = 30;
    private int mAlertResId;
    private String mAlertUrl;
    private AlertPlayer.Mode mAlertMode;
    private ScreenMode mScreenMode;
    private PowerManager mPowerManager;
    private SharedPrefDataSource mSharedPrefRepository;
    private boolean mIsplayed = false;
    private Long mTimeDiffBetweenAlerts;

    public enum ScreenMode {
        ModeOn,
        ModeOff
    }

    /**
     * Constructor
     *
     * @param context
     * @param maxSpeed The max speed at which the alert is played (default value = 30km/h)
     */
    public SpeedAlert(Context context, double maxSpeed) {
        mSharedPrefRepository = new SharedPrefRepository(context);
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        mContext = context;
        if (maxSpeed != 0) {
            mMaxSpeed = maxSpeed;
        }
    }

    /**
     * Constructor
     *
     * @param context
     * @param maxSpeed The max speed at which the alert is played (default value = 30km/h)
     */
    public SpeedAlert(Context context, double maxSpeed, Long timeDiffBetweenAlerts) {
        this.mTimeDiffBetweenAlerts = timeDiffBetweenAlerts;
        mSharedPrefRepository = new SharedPrefRepository(context);
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        mContext = context;
        if (maxSpeed != 0) {
            mMaxSpeed = maxSpeed;
        }
    }

    /**
     * Method used to start getting location updates
     *
     * @param minUpdateTime     Minimum time before getting new updated location.
     * @param minUpdateDistance Minimum distance before getting new updated location.
     */
    public void startTracking(int minUpdateTime, int minUpdateDistance) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minUpdateTime, minUpdateDistance, this);
        }
    }

    /**
     * Method used to stop getting location updates
     */
    public void stopTracking() {
        mLocationManager.removeUpdates(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(mContext,location.getSpeed()*3.6+"", Toast.LENGTH_SHORT).show();
//        Log.d("Current Speed" , location.getSpeed()*3.6+"");
        playAlert(location);
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

    /**
     * Method used to get speed from location, convert it to km/h then play the alert
     * if speed = maxSpeed.
     *
     * @param location used to get speed.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void playAlert(Location location) {
        Long currentDate = System.currentTimeMillis();
        double speedInKmH = location.getSpeed() * 33.6;
        if (speedInKmH >= mMaxSpeed
                && (currentDate - mSharedPrefRepository.getLastAlertDate())
                >= (mTimeDiffBetweenAlerts)) {
            if (!mIsplayed) {
                mSharedPrefRepository.setLastAlertDate(currentDate);
                if (mScreenMode == ScreenMode.ModeOn) {
                    if (mPowerManager.isInteractive()) {
                        AlertPlayer.playAlert(mContext, mAlertUrl, mAlertResId, mAlertMode);
                    }
                } else {
                    AlertPlayer.playAlert(mContext, mAlertUrl, mAlertResId, mAlertMode);
                }
                mIsplayed = true;
            }
        } else {
            mIsplayed = false;
        }
    }

    public void setAlertResource(int voiceNoteResId) {
        this.mAlertResId = voiceNoteResId;
    }

    public void setAlertUrl(String voiceNoteUrl) {
        this.mAlertUrl = voiceNoteUrl;
    }

    public void setAlertMode(AlertPlayer.Mode mode) {
        this.mAlertMode = mode;
    }

    public void setScreenMode(ScreenMode screenMode) {
        this.mScreenMode = screenMode;
    }

}
