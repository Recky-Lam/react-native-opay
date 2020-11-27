
import { NativeModules } from 'react-native';

const { RNOpay } = NativeModules;


const checkInit = (instance) => {
    if (!instance.opayInitialized) {
        throw new Error(`You should call init first, higher up your code like in your index file.\nRead more https://github.com/tolu360/react-native-paystack#3-usage`)
    }
}

class RNOpay {
    opayInitialized = false;

    initTransaction(chargeParams: { [x: string]: any }) {
        if (typeof chargeParams != 'object') {
            return Promise.reject(new Error("Method argument can only be a Javascript object"));
        }
        checkInit(this);

        return RNOpay.initTransaction(chargeParams);
    }
}


export default RNOpay;
