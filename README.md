


# react-native-opay

### Author

*Recky Lam*</br>
If you have any feedback, questions, or concerns, please e-mail me via reckylam93@gmail.com

## Introduction

This React Native SDK makes it easy to integrate your applications & interact with the OPay.<br/>

To complete payment that you will need your Merchant information. Remember to keep it safe.</br>
Please visit [OPay's API Introduction](https://open.operapay.com/home/) for more .</br>
For [OPay's Documentation](https://documentation.opayweb.com/).

## Getting started

For the best experience, please use the latest version

`$ npm install react-native-opay --save`

### Mostly automatic installation

`$ react-native link react-native-opay`

## Usage
### Make a payment
```
import RNOpay from 'react-native-opay';

var orderData = {
  'reference' : 'Test_reference123456', // A unique id for your payment order
  'amount' : '100', // Amount need to pay in kobo,  1N = 100 kobo
  'pubKey' : 'OPAYPUB1234567890123',
  'merchantUserId' : 'USER_ID',
  'merchantUserName' : 'USER_NAME',
}

RNOpay.initTransaction(orderData) {};

```

### Response & Error Code
#### Response Object
```
{
  'code':'9000';  
  'message':'success';  
  'reference':'OPAYPUB1234567890123';  
  'amount':'100';
}
```
#### Error Code
```
SUCCESS(9000, "success"),
SERVER_ERROR(4000, "server error"),
USER_CANCEL(6001, "user cancel"),
NET_ERROR(6002, "net error"),
PARAM_ERROR(4001, "param error"),
REPEAT_REQUEST(5000, "repeat request"),
PROCESSING(8000, "processing");
```

### Verify

Verify a charge by calling OPay's [transaction/status](transaction/status)  with the  `reference`  mentioned before.</br>

## Known Issue

1. Uses-sdk:minSdkVersion 'xx' cannot be smaller than version 'xx' declared in library [:react-native-opay]
- Follow the 'Suggestions' in console.<br/>
-- Sync library minsdk version and project minsdk version.<br/>
-- Or use tools:overrideLibrary="com.reckylam.rnopay" to force usage (not recommend, may lead to runtime failures)

2. When  run 'yarn android' console output the following words:
- RNOpayPackage.java:26 @Override<br/>
- RNOpayManager.java:19<br/>
-- Please update to the latest version.

3. Could not find paysdk-debug
- Add following codes to your project build.gradle -> allprojects -> repositories
  ```Javascript
  allprojects {
      repositories {
      ... //your code
          flatDir {
              dirs project(':xxx').file('libs')
          }
      }
  }
  ```
## Contribution

For Technical Support<br/>
@huawo<br/>
@Li Peng<br/>