# SpeedAlert
A Library for monitoring the movement speed using speed and plays an alert when exceeding the set speed.

## Getting Started

Step 1: Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency:

```
dependencies {
	        implementation 'com.github.Code95:C95-Speed-Alert:1.0.0'
	}
```


### Usage


```
public class MainActivity extends AppCompatActivity {

    //Location permission request code
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 101;

    //Declaring speed alert object to start tracking movement speed and play alerts
    private SpeedAlert mSpeedAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiating speed alert with context and max speed.
        mSpeedAlert = new SpeedAlert(this, 10);

        /*Setting alert mode and file, if not set a default alert will be played*/

        //Setting alert mode to play from local file
        mSpeedAlert.setAlertMode(AlertPlayer.Mode.DataFromLocalFile);
        //Setting file resource
        mSpeedAlert.setAlertResource(R.raw.alert);

        /*Setting alert mode to play from a url*/
        
        //mSpeedAlert.setAlertMode(AlertPlayer.Mode.DataFromUrl);
        //Setting url of the file
        //mSpeedAlert.setAlertUrl("http://www.freesfx.co.uk/rx2/mp3s/5/16927_1461333031.mp3");

        /*Setting screen mode (ModeOn/ModeOff) when on alert starts whenever the screen is on and only if the screen is on
        * / when off alert starts regardless the screen is on or off.*/

        //If not set, default screen mode is ModeOff
        mSpeedAlert.setScreenMode(SpeedAlert.ScreenMode.ModeOn);

        /*Speed alert is based on GPS, so the location permission must be added to manifest and requested at runtime*/

        //Checking if location permission is granted
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Start tracking movement speed by giving location & speed minimum update time and distance. (time is seconds, distance in meters)
            mSpeedAlert.startTracking(1, 1);
        } else {
            //If location permission is not granted request it.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION_CODE);
        }
    }

    //Called when user respond to requesting permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //If location permission is granted start tracking.
        if(requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Start tracking movement speed by giving location & speed minimum update time and distance. (time is seconds, distance in meters)
                mSpeedAlert.startTracking(1, 1);
            } else {
                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }
  }
```

## License
```
Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```

