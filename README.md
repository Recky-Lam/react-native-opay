
# react-native-opay

### Author

*Recky Lam*</br>
If you have any feedback, questions, or concerns, please e-mail me via reckylam93@gmail.com

## Getting started

For the best experience, please use the latest version

`$ npm install react-native-opay --save`

### Mostly automatic installation

`$ react-native link react-native-opay`

### Manual installation

#### iOS

Not Supported Yet

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reckylam.rnopay.RNOpayPackage;` to the imports at the top of the file
  - Add `new RNOpayPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-opay'
  	project(':react-native-opay').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-opay/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-opay')
  	```


## Usage
```javascript
import RNOpay from 'react-native-opay';

RNOpay.initTransaction(String reference, String amount, String pubKey, String merchantUserId, String merchantUserName) {};

```

## Contribution

For Technical Support<br/>
@huawo<br/>
@Li Peng<br/>