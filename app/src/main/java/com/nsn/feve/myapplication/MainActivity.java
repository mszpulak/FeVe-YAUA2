package com.nsn.feve.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;


public class MainActivity extends Activity {
    Thread cThread=null;
    ServerSocket serverSocket=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new CellListener(),PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_CELL_LOCATION);
        //FillMCCMNC();
        //FillCellLocation();
        FillIMSIIMEI();
        getIpInterfaceAdress();
        //Thread cThread = new Thread(new ServerThread());
        //cThread.start();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //setContentView(R.layout.activity_main);
        //FillMCCMNC();
        //FillCellLocation();
        //FillIMSIIMEI();
        //FillCellInfo();
        //clickRefreshButton(null);

    }
    //protected void onStop(){
        //try{
        //    if( !serverSocket.isClosed()){
        //        serverSocket.close();
        //        //cThread.interrupt();
        //    }
       // }
       // catch (Exception e){
       //     Log.e("onStopException",e.toString());
        //
        //}
        //super.onStop();

    //}
    public void startTestingActivity() {
        try {
            Intent i = new Intent();
            i.setComponent(new ComponentName("com.android.settings", "com.android.settings.RadioInfo"));
            i.setAction(Intent.ACTION_MAIN);
            startActivity(i);

        } catch (Exception e) {
            Log.e("ERROR", e.toString(), e);



        }

    }

    public void FillMCCMNC(){
        TextView MCCtext2 = (TextView) findViewById(R.id.MCCtext2);
        TextView MNCtext2 = (TextView) findViewById(R.id.MNCtext2);
        TextView operator2 = (TextView) findViewById(R.id.operator2);
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            String MCCMNC = tm.getNetworkOperator();
            if (MCCMNC.isEmpty()) {
                MCCtext2.setText("-");
                MNCtext2.setText("-");
            }
            else {
                MCCtext2.setText(MCCMNC.substring(0, 3));
                MNCtext2.setText(MCCMNC.substring(3));
                }
            operator2.setText(tm.getNetworkOperatorName());
            Log.i("MCC", MCCMNC);
            Log.i("operator",tm.getNetworkOperatorName());
        }
        catch(final Exception e){
            MCCtext2.setText("err");
            MNCtext2.setText("err");
            operator2.setText("err");
            Log.e("ERROR",e.toString(),e);
        }
    }
    public void FillCellLocation(){
        TextView CellID = (TextView) findViewById(R.id.CellIDtext2);
        TextView LAC = (TextView) findViewById(R.id.LACtext2);
        TextView PSC = (TextView) findViewById(R.id.PSCtext2);
        TextView Technology = (TextView) findViewById(R.id.Technology2);
        int INVALID=0x7FFFFFFF;

        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation GsmCellLocationObject=(GsmCellLocation)tm.getCellLocation();

            int intNetworkType=0;
            int intCellID=GsmCellLocationObject.getCid();
            switch (tm.getNetworkType()) {
                case (TelephonyManager.NETWORK_TYPE_HSDPA):
                    Technology.setText("HSDPA");
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_HSPA):
                    Technology.setText("HSPA");
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_HSPAP):
                    Technology.setText("HSPAP");
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_HSUPA):
                    Technology.setText("HSUPA");
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_UMTS):
                    Technology.setText("UMTS");
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_LTE):
                    Technology.setText("LTE");
                    break;
                case (TelephonyManager.NETWORK_TYPE_UNKNOWN):
                    Technology.setText("UNKNOWN");
                    break;
                case (TelephonyManager.NETWORK_TYPE_EDGE):
                    Technology.setText("EDGE");
                    break;
            }

            if (intCellID!=INVALID){
                Log.i("intCellID ",Integer.toString(intCellID));
                CellID.setText(Integer.toString(intCellID));
            }
            else{
                CellID.setText("-");
            }
            if (GsmCellLocationObject.getLac()!=INVALID) {
                LAC.setText(Integer.toString(GsmCellLocationObject.getLac()));
            }
            else {
                LAC.setText("-");
            }
            if (GsmCellLocationObject.getPsc()!=INVALID & GsmCellLocationObject.getPsc()!=-1) {
                PSC.setText(Integer.toString(GsmCellLocationObject.getPsc()));
            }
            else {
                PSC.setText("-");
            }
            Log.i("GsmCellLocation",GsmCellLocationObject.toString());
        }
        catch(final Exception e){
            CellID.setText("err");
            LAC.setText("err");
            PSC.setText("err");
            Log.e("ERROR",e.toString(),e);
        }
    }
    public void FillIMSIIMEI(){
        TextView IMEItext2 = (TextView) findViewById(R.id.IMEItext2);
        TextView IMSItext2 = (TextView) findViewById(R.id.IMSItext2);
        TextView PhNumber2 = (TextView) findViewById(R.id.PhNumbertext2);
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            IMEItext2.setText(tm.getDeviceId());
            IMSItext2.setText(tm.getSubscriberId());
            PhNumber2.setText(tm.getLine1Number());

            Log.i("IMSI",tm.getSubscriberId().toString());
            Log.i("PhNumber2",tm.getLine1Number().toString());
        }
        catch(final Exception e){
            IMEItext2.setText("err");
            IMSItext2.setText("err");
            PhNumber2.setText("err");
            Log.e("ERROR",e.toString(),e);Log.e("ERROR",e.toString(),e);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void clickButton(View view) {
        // Do something in response to button
        startTestingActivity();
    }
    public void clickToggleButton(View view) {
        // Do something in response to button
        try{

            Runtime rt = Runtime.getRuntime();
            Log.i("exec","executing su");
            String[] str={"su","-c","settings", "put", "global", "airplane_mode_on", "1"};
            Process process = rt.exec(str);
            Thread.sleep(1000);
            String[] str2={"su","-c","am", "broadcast", "-a", "android.intent.action.AIRPLANE_MODE", "--ez", "state", "true"};
            process = rt.exec(str2);
            Thread.sleep(1000);
            String[] str3={"su","-c","settings", "put", "global", "airplane_mode_on", "0"};
            process = rt.exec(str3);
            Thread.sleep(1000);
            String[] str4={"su","-c","am", "broadcast", "-a", "android.intent.action.AIRPLANE_MODE", "--ez", "state", "false"};
            process = rt.exec(str4);

            Log.i("exec","done su");

         }
       catch (Exception e){
           Log.e("ERROR",e.toString());
       }

    }

    public void clickSamsungServiceMenu(View view) {
        // Do something in response to button
        try{

            Runtime rt = Runtime.getRuntime();
            Log.i("exec","executing su");
            String[] str2={"su","-c","am", "start", "-a", "android.intent.action.MAIN","-n","com.sec.android.RilServiceModeApp/com.sec.android.RilServiceModeApp.ServiceModeApp", "-e", "keyString", "27663368378"};
            Process process = rt.exec(str2);
            process = rt.exec(str2);
            Log.i("exec","done su");

        }
        catch (Exception e){
            Log.e("ERROR",e.toString());
        }

    }

    public void clickSamsungR99Menu(View view) {
        // Do something in response to button
        try{

            Runtime rt = Runtime.getRuntime();
            Log.i("exec","executing su");
            String[] str2={"su","-c","am", "start", "-a", "android.intent.action.MAIN","-n","com.sec.android.RilServiceModeApp/com.sec.android.RilServiceModeApp.ServiceModeApp", "-e", "keyString", "301279"};
            Process process = rt.exec(str2);
            process = rt.exec(str2);
            Log.i("exec","done su");

        }
        catch (Exception e){
            Log.e("ERROR",e.toString());
        }

    }
    public void clickSamsungWCDMALockFreqMenu(View view) {
        // Do something in response to button
        try{

            Runtime rt = Runtime.getRuntime();
            Log.i("exec","executing su");
            String[] str2={"su","-c","am", "start", "-a", "android.intent.action.MAIN","-n","com.sec.android.RilServiceModeApp/com.sec.android.RilServiceModeApp.ServiceModeApp", "-e", "keyString", "2886"};
            Process process = rt.exec(str2);
            process = rt.exec(str2);
            Log.i("exec","done su");

        }
        catch (Exception e){
            Log.e("ERROR",e.toString());
        }

    }
    public void clickSamsungLockFreqMenu(View view) {
        // Do something in response to button
        try{

            Runtime rt = Runtime.getRuntime();
            Log.i("exec","executing su");
            String[] str2={"su","-c","am", "start", "-a", "android.intent.action.MAIN","-n","com.sec.android.RilServiceModeApp/com.sec.android.RilServiceModeApp.ServiceModeApp", "-e", "keyString", "37375625"};
            Process process = rt.exec(str2);
            process = rt.exec(str2);
            Log.i("exec","done su");

        }
        catch (Exception e){
            Log.e("ERROR",e.toString());
        }

    }

    public void clickSamsungRFBandMenu(View view) {
        // Do something in response to button
        try{

            Runtime rt = Runtime.getRuntime();
            Log.i("exec","executing su");
            String[] str2={"su","-c","am", "start", "-a", "android.intent.action.MAIN","-n","com.sec.android.RilServiceModeApp/com.sec.android.RilServiceModeApp.ServiceModeApp", "-e", "keyString", "2263"};
            Process process = rt.exec(str2);
            process = rt.exec(str2);
            Log.i("exec","done su");

        }
        catch (Exception e){
            Log.e("ERROR",e.toString());
        }

    }

    public void clickCallAR(View view) {
        // Do something in response to button
        String number = "0800903300999";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void getIpInterfaceAdress(){
       try {
           TextView IpIfaddress = (TextView) findViewById(R.id.IP2);
           IpIfaddress.setText("-");

           NetworkInterface en2 = NetworkInterface.getByName("rmnet0");
           for (Enumeration<InetAddress> enumIpAddr = en2.getInetAddresses(); enumIpAddr.hasMoreElements();) {
               InetAddress inetAddress2 = enumIpAddr.nextElement();
               if(inetAddress2 instanceof Inet4Address) {
                   Log.i("rmnet0-ip: ", inetAddress2.toString());
                   IpIfaddress.setText(inetAddress2.toString());
               }

           }

        }
        catch (Exception e) {

            TextView IpIfaddress = (TextView) findViewById(R.id.IP2);
            IpIfaddress.setText("-");
            Log.e("ERR:","exception in ipddress");
            Log.e("error",e.toString());

        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class CellListener extends PhoneStateListener {
        public void onCellLocationChanged(CellLocation location) {
            super.onCellLocationChanged(location);
            FillCellLocation();
            FillMCCMNC();
            getIpInterfaceAdress();

            Log.i("CellLocList", location.toString());
        }
        public void onSignalStrengthsChanged(SignalStrength signal){
            super.onSignalStrengthsChanged(signal);
            Log.i("CellSigStrength", signal.toString());
            try {
                TextView RSRPsignalVal = (TextView) findViewById(R.id.RSRPsignalVal);
                TextView RSRQsignalVal = (TextView) findViewById(R.id.RSRQsignalVal);
                TextView RSSNRsignalVal = (TextView) findViewById(R.id.RSSNRsignalVal);
                TextView RSSItextValue = (TextView) findViewById(R.id.RSSItextValue);
                //API reflections
                Method[] methods = signal.getClass().getMethods();
                Log.w("methods",methods.toString());
                for (Method mthd : methods) {
                    if (mthd.getName().equals("getLteRsrp")) {
                        Object  sig=mthd.invoke(signal);
                        Log.d("LteRsrp",sig.toString());
                        RSRPsignalVal.setText(sig.toString());
                        Field fi=signal.getClass().getField("INVALID");
                        if (sig.equals(fi.getInt(signal))){
                            RSRPsignalVal.setText("-");
                            Log.w("INVALIDLteRsrp",sig.toString());
                        }
                    }
                    else {
                        //RSRPsignalVal.setText("-");
                    }
                    if (mthd.getName().equals("getLteRsrq")) {
                        Object  sig=mthd.invoke(signal);
                        Log.d("LteRsrq",sig.toString());
                        RSRQsignalVal.setText(sig.toString());
                        Field fi=signal.getClass().getField("INVALID");
                        if (sig.equals(fi.getInt(signal))){
                            RSRQsignalVal.setText("-");
                            Log.w("INVALIDLteRsrq",sig.toString());
                        }

                    }
                    else{
                        //RSRQsignalVal.setText("-");
                    }
                    if (mthd.getName().equals("getLteRssnr")) {
                        Object  sig=mthd.invoke(signal);
                        Log.d("LteRssnr",sig.toString());
                        RSSNRsignalVal.setText(sig.toString());
                        Field fi=signal.getClass().getField("INVALID");
                        if (sig.equals(fi.getInt(signal))){
                            RSSNRsignalVal.setText("-");
                            Log.w("INVALIDLteRssnr",sig.toString());
                        }

                    }
                    else {
                        //RSSNRsignalVal.setText("-");
                    }
                    if (signal.getGsmSignalStrength()!=99) {
                        RSSItextValue.setText(Integer.toString(2 * signal.getGsmSignalStrength() - 113));
                        Log.d("LteRssi",Integer.toString(signal.getGsmSignalStrength()));
                    }
                    else {
                        RSSItextValue.setText("-");
                    }

                }
            }
            catch (Exception e){
                Log.e("ERROR",e.toString(),e);
            }


        }
    }
    public class ServerThread implements Runnable {
        @Override
        public void run() {
            serverSocket =null;
            try {
                NetworkInterface netWlan0= NetworkInterface.getByName("wlan0");
                InetAddress inetAddress=null;
                for (Enumeration<InetAddress> enumIpAddr = netWlan0.getInetAddresses();enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (inetAddress.getAddress().length == 4) {
                        break;
                    }
                }

                Log.w("wlan0", inetAddress.toString());

                serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress((Inet4Address)inetAddress,1235));

                Log.w("getInetAddress", serverSocket.getInetAddress().toString());
                while(true) {
                    Socket client=null;
                    try {
                        client = serverSocket.accept();
                        Log.w("ServerThread", "Connected");

                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String line = null;
                        while ((line = in.readLine()) != null & !client.isClosed()) {
                            Log.w("ServerActivity", line);

                        }

                    } finally {
                        try {
                            if(! client.isClosed()){
                                client.close();
                                Log.i("SocketActivity", "client.close");
                            }
                        }
                        catch (Exception e){
                            Log.e("SocketActivityException", e.toString());
                        }
                        //Log.w("ServerActivity", "serverSocket.close");
                    }
                }
            }

            catch (Exception e){
                Log.e("RunThread",e.toString());

            }
            finally{
                try {
                    serverSocket.close();
                }
                catch (Exception e){
                    Log.e("serverSocketException", e.toString());
                }
            }


        }
    }
}
