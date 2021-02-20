package com.course.feelsound;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothDataService extends Service {
    final int handlerState = 0;                        //used to identify handler message
    Handler bluetoothIn;
    private BluetoothAdapter btAdapter = null;

    private ConnectingThread mConnectingThread;
    private ConnectedThread mConnectedThread;

    private boolean stopThread;
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static final String MAC_ADDRESS = "00:1A:7D:DA:71:13";

    private StringBuilder recDataString = new StringBuilder();
    public static final int MSG_REGISTER_CLIENT = 1;
    public static final int MSG_SEND_TO_SERVICE = 3;
    private Messenger mClient = null;

    NotificationManager Notifi_M;
    Notification Notifi;
    String s_name;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TEST", "SERVICE CREATED");
        stopThread = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TEST", "SERVICE STARTED");
        bluetoothIn = new Handler() {

            public void handleMessage(android.os.Message msg) {
                Log.d("TEST", "handleMessage");
                if (msg.what == handlerState) {                                     //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);

                    Log.d("TEST", recDataString.toString());
                    // Do stuff here with your data, like adding it to the database
                }
                recDataString.delete(0, recDataString.length());                    //clear all string data

                Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
        };


        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bluetoothIn.removeCallbacksAndMessages(null);
        stopThread = true;
        if (mConnectedThread != null) {
            mConnectedThread.closeStreams();
        }
        if (mConnectingThread != null) {
            mConnectingThread.closeSocket();
        }
        Log.d("TEST", "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private final Messenger mMessenger = new Messenger(new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MSG_SEND_TO_SERVICE:
                    mClient = msg.replyTo;
                    String data = msg.obj.toString();
                    mConnectedThread.write(data);
                    Log.i("TEST", "Service got the message : "  + data);
            }

            return false;
        }
    }));

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if (btAdapter == null) {
            Log.d("TEST", "BLUETOOTH NOT SUPPORTED BY DEVICE, STOPPING SERVICE");
            stopSelf();
        } else {
            if (btAdapter.isEnabled()) {
                Log.d("TEST", "BT ENABLED! BT ADDRESS : " + btAdapter.getAddress() + " , BT NAME : " + btAdapter.getName());
                try {
                    BluetoothDevice device = btAdapter.getRemoteDevice(MAC_ADDRESS);
                    Log.d("TEST", "ATTEMPTING TO CONNECT TO REMOTE DEVICE : " + MAC_ADDRESS);
                    mConnectingThread = new ConnectingThread(device);
                    mConnectingThread.start();
                } catch (IllegalArgumentException e) {
                    Log.d("TEST", "PROBLEM WITH MAC ADDRESS : " + e.toString());
                    Log.d("TEST", "ILLEGAL MAC ADDRESS, STOPPING SERVICE");
                    stopSelf();
                }
            } else {
                Log.d("TEST", "BLUETOOTH NOT ON, STOPPING SERVICE");
                stopSelf();
            }
        }
    }

    // New Class for Connecting Thread
    private class ConnectingThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectingThread(BluetoothDevice device) {
            Log.d("TEST", "IN CONNECTING THREAD");
            mmDevice = device;
            BluetoothSocket temp = null;
            Log.d("TEST", "MAC ADDRESS : " + MAC_ADDRESS);
            Log.d("TEST", "BT UUID : " + BTMODULEUUID);
            try {
                temp = mmDevice.createRfcommSocketToServiceRecord(BTMODULEUUID);
                Log.d("TEST", "SOCKET CREATED : " + temp.toString());
            } catch (IOException e) {
                Log.d("TEST", "SOCKET CREATION FAILED :" + e.toString());
                Log.d("TEST", "SOCKET CREATION FAILED, STOPPING SERVICE");
                stopSelf();
            }
            mmSocket = temp;
        }

        @Override
        public void run() {
            super.run();
            Log.d("TEST", "IN CONNECTING THREAD RUN");
            // Establish the Bluetooth socket connection.
            // Cancelling discovery as it may slow down connection
            btAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
                Log.d("TEST", "BT SOCKET CONNECTED");
                mConnectedThread = new ConnectedThread(mmSocket);
                mConnectedThread.start();
                Log.d("TEST", "CONNECTED THREAD STARTED");
                //I send a character when resuming.beginning transmission to check device is connected
                //If it is not an exception will be thrown in the write method and finish() will be called
                mConnectedThread.write("x");
            } catch (IOException e) {
                try {
                    Log.d("TEST", "SOCKET CONNECTION FAILED : " + e.toString());
                    Log.d("TEST", "SOCKET CONNECTION FAILED, STOPPING SERVICE");
                    mmSocket.close();
                    stopSelf();
                } catch (IOException e2) {
                    Log.d("TEST", "SOCKET CLOSING FAILED :" + e2.toString());
                    Log.d("TEST", "SOCKET CLOSING FAILED, STOPPING SERVICE");
                    stopSelf();
                    //insert code to deal with this
                }
            } catch (IllegalStateException e) {
                Log.d("TEST", "CONNECTED THREAD START FAILED : " + e.toString());
                Log.d("TEST", "CONNECTED THREAD START FAILED, STOPPING SERVICE");
                stopSelf();
            }
        }

        public void closeSocket() {
            try {
                //Don't leave Bluetooth sockets open when leaving activity
                mmSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
                Log.d("TEST", e2.toString());
                Log.d("TEST", "SOCKET CLOSING FAILED, STOPPING SERVICE");
                stopSelf();
            }
        }
    }

    // New Class for Connected Thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            Log.d("TEST", "IN CONNECTED THREAD");
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.d("TEST", e.toString());
                Log.d("TEST", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                stopSelf();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.d("TEST", "IN CONNECTED THREAD RUN");
            byte[] buffer = new byte[256];
            int bytes;
            final DBHelper dbHelper = new DBHelper(getApplicationContext(), "feelsound.db", null, 1);

            // Keep looping to listen for received messages
            while (true && !stopThread) {
                try {
                    // Data GET From Raspberry Pi
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    try{
                        Log.d("TEST", "CONNECTED THREAD " + readMessage);
                        Log.i("come from rasp",readMessage +"");
                        int s_type = Integer.parseInt(readMessage.substring(0,1));
                        String time = readMessage.substring(1,20);
                        s_name = readMessage.substring(22);
                        Log.d("come and edit", s_type + "|" + time + "|" + String.valueOf(s_type));
                        dbHelper.insert_record(s_type, s_name, time);
                        Toast.makeText(BluetoothDataService.this, s_name + " 소리가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Log.i("Text"," ");
                    }

                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    Log.d("TEST", e.toString());
                    Log.d("TEST", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                    stopSelf();
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Log.d("TEST", "UNABLE TO READ/WRITE " + e.toString());
                Log.d("TEST", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                stopSelf();
            }
        }



        public void closeStreams() {
            try {
                //Don't leave Bluetooth sockets open when leaving activity
                mmInStream.close();
                mmOutStream.close();
            } catch (IOException e2) {
                //insert code to deal with this
                Log.d("TEST", e2.toString());
                Log.d("TEST", "STREAM CLOSING FAILED, STOPPING SERVICE");
                stopSelf();
            }
        }

        class myServiceHandler extends Handler{
            @Override
            public void handleMessage(android.os.Message msg){
                Intent intent = new Intent(BluetoothDataService.this, IntroActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(BluetoothDataService.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                if(s_name == "Baby Crying")
                {
                    Notifi = new Notification.Builder(getApplicationContext())
                            .setContentTitle("소리 알림")
                            .setContentText("아기가 울고 있어요!!")
                            .setContentIntent(pendingIntent)
                            .build();
                    Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                    Notifi.flags = Notification.FLAG_AUTO_CANCEL;

                    Notifi_M.notify(777, Notifi);

                }

                if(s_name == "Washing machine finish")
                {
                    Notifi = new Notification.Builder(getApplicationContext())
                            .setContentTitle("소리 알림")
                            .setContentText("세탁기가 종료되었어요!!!")
                            .setContentIntent(pendingIntent)
                            .build();
                    Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                    Notifi.flags = Notification.FLAG_AUTO_CANCEL;

                    Notifi_M.notify(777, Notifi);

                }

                if(s_name == "Subway Coming")
                {
                    Notifi = new Notification.Builder(getApplicationContext())
                            .setContentTitle("소리 알림")
                            .setContentText("지하철이 오고 있어요!!!")
                            .setContentIntent(pendingIntent)
                            .build();
                    Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                    Notifi.flags = Notification.FLAG_AUTO_CANCEL;

                    Notifi_M.notify(777, Notifi);

                }

                if(s_name == "School Bell")
                {
                    Notifi = new Notification.Builder(getApplicationContext())
                            .setContentTitle("소리 알림")
                            .setContentText("학교 종이 울리고 있어요!!!")
                            .setContentIntent(pendingIntent)
                            .build();
                    Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                    Notifi.flags = Notification.FLAG_AUTO_CANCEL;

                    Notifi_M.notify(777, Notifi);

                }

            }
        }

    }
}