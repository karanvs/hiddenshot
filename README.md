# hiddenshot
hiddenshot is a simple library to take a screenshot programmtically on demand. 
Screenshot is saved in the picture folder on the device. You may also share a screenshot programmatically. 


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
	        compile 'com.github.karanvs:hiddenshot:v1.0'
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
## Under Develoment

* Continous shots.

### License

This project is licensed under the Apache 2.0 License - see the [LICENSE.txt](LICENSE.txt) file for details

