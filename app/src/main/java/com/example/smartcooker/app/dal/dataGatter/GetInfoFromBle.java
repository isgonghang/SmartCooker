package com.example.smartcooker.app.dal.dataGatter;

import android.util.Log;

import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;
import com.vise.log.ViseLog;

/**
 * Created by ke on 2018/4/26.
 */

public class GetInfoFromBle {

    /**
     * 扫描回调
     */
    private ScanCallback periodScanCallback = new ScanCallback(new IScanCallback() {
        @Override
        public void onDeviceFound(final BluetoothLeDevice bluetoothLeDevice) {
            Log.i("miao", "onsDeviceFound: " + bluetoothLeDevice.getName() + bluetoothLeDevice.getAddress());

        }

        @Override
        public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {
            ViseLog.i("scan finish " + bluetoothLeDeviceStore);
        }

        @Override
        public void onScanTimeout() {
            ViseLog.i("scan timeout");
        }

    });

    public void get() {

    }
}
