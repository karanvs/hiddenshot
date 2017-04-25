# hiddenshot
* hiddenshot is a simple library to take a screenshot programmatically on demand. 
* Screenshot is saved in the picture folder on the device. 
* Share a screenshot
* Take continous screenshots periodically.


## Getting Started

*  Add this to build.gradle
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

*  Add dependency for library
```
dependencies {
	        compile 'com.github.karanvs:hiddenshot:v1.1'
	}
```

## Usage

* Get the Screenshot as, 

```
Bitmap shot = HiddenShot.getInstance().buildShot(activity);
```

* Save the screenshot as, 

```
HiddenShot.getInstance().saveShot(MainActivity.this, shot, "view");

```
* Take and share screenshot as,

```
HiddenShot.getInstance().buildShotAndShare(MainActivity.this);

```

* Take continous shots,

```
HiddenShot.getInstance().buildContinousShot(MainActivity.this, timeInterval); //specify your value in timeinterval 

Stop continous shot in onDestroy()

@Override protected void onDestroy() {
    HiddenShot.getInstance().stopContinousShot(); //this can be called manually to stop shots at any time
    super.onDestroy();
  }
  
```

### License

This project is licensed under the Apache 2.0 License - see the [LICENSE.txt](LICENSE.txt) file for details

