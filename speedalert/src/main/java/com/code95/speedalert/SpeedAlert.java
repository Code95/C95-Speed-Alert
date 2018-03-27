package com.code95.speedalert;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


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

    /**
     * Constructor
     * @param context
     * @param maxSpeed The max speed at which the alert is played (default value = 30km/h)
     *
     */
    public SpeedAlert(Context context, double maxSpeed) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mContext = context;
        if(maxSpeed != 0) {
            mMaxSpeed = maxSpeed;
        }
    }

    /**
     * Method used to start getting location updates
     *
     * @param minUpdateTime Minimum time before getting new updated location.
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

    @Override
    public void onLocationChanged(Location location) {
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
    private void playAlert(Location location) {
        double speedInKmH = location.getSpeed() * 3.6;
        if (speedInKmH >= mMaxSpeed) {
            AlertPlayer.playAlert(mContext, mAlertUrl, mAlertResId, mAlertMode);
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

}
