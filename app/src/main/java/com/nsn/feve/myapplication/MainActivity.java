package com.nsn.feve.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new CellListener(),PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_CELL_LOCATION);
        //FillMCCMNC();
        //FillCellLocation();
        //FillIMSIIMEI();
        //FillCellInfo();
        clickRefreshButton(null);
        //startTestingActivity();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //setContentView(R.layout.activity_main);
        //FillMCCMNC();
        //FillCellLocation();
        //FillIMSIIMEI();
        //FillCellInfo();
        clickRefreshButton(null);
        //startTestingActivity();
    }
    public void startTestingActivity() {

            Intent i = new Intent();
            i.setComponent(new ComponentName("com.android.settings", "com.android.settings.RadioInfo"));
            i.setAction(Intent.ACTION_MAIN);
            startActivity(i);

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
        int INVALID=0x7FFFFFFF;

        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation GsmCellLocationObject=(GsmCellLocation)tm.getCellLocation();

            int intNetworkType=0;
            int intCellID=GsmCellLocationObject.getCid();
            switch (tm.getNetworkType()) {
                case (TelephonyManager.NETWORK_TYPE_HSDPA):
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_HSPA):
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_HSPAP):
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_HSUPA):
                    intCellID = intCellID & 0xffff;
                    break;
                case (TelephonyManager.NETWORK_TYPE_UMTS):
                    intCellID = intCellID & 0xffff;
                    break;
            }

            if (intCellID!=INVALID){
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
            Log.e("ERROR",e.toString(),e);Log.e("ERROR",e.toString(),e);
        }
    }
    public void FillIMSIIMEI(){
        TextView IMEItext2 = (TextView) findViewById(R.id.IMEItext2);
        TextView IMSItext2 = (TextView) findViewById(R.id.IMSItext2);
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            IMEItext2.setText(tm.getDeviceId());
            IMSItext2.setText(tm.getSubscriberId());

            Log.i("IMSI",tm.getSubscriberId().toString());
        }
        catch(final Exception e){
            IMEItext2.setText("err");
            IMSItext2.setText("err");
            Log.e("ERROR",e.toString(),e);Log.e("ERROR",e.toString(),e);
        }
    }
    @TargetApi(17)
    public void FillCellInfo(){

        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            List<CellInfo> AllCellInfoList= tm.getAllCellInfo();
            if (AllCellInfoList.get(0) instanceof CellInfoLte){

            }
            else if (AllCellInfoList.get(0) instanceof CellInfoGsm) {

            }
            else if (AllCellInfoList.get(0) instanceof CellInfoWcdma) {

            }
            else {

            }

            Log.i("AllCellInfoList",tm.getAllCellInfo().toString());
        }
        catch(final Exception e){


            Log.e("ERROR",e.toString(),e);Log.e("ERROR",e.toString(),e);
        }
    }

    public void FillData() {
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String IMEINumber=tm.getDeviceId();
        String Line1Number=tm.getLine1Number();

        GsmCellLocation GsmCellLocationObject=(GsmCellLocation)tm.getCellLocation();

        String CellLocationString="";
        if(GsmCellLocationObject != null) {
            CellLocationString="\n=====================";
            CellLocationString="\nCellID=:"+GsmCellLocationObject.getCid();
            CellLocationString+=" | LAC:= "+GsmCellLocationObject.getLac();
            CellLocationString+=" | PSC:= "+GsmCellLocationObject.getPsc();
            CellLocationString+="\n=====================";
        }
        else{
            CellLocationString="\n=====================";
            CellLocationString+="\nNo Data";
            CellLocationString+="\n=====================";
        }

        String AllCellInfoString="";
        String DetailedAllCellInfoString="";

        if (Build.VERSION.SDK_INT>=17) {
            List<CellInfo> AllCellInfo= tm.getAllCellInfo();

            if (AllCellInfo != null) {

                for (int i = 0; i < AllCellInfo.size(); i++) {

                    if (AllCellInfo.get(i) instanceof CellInfoLte) {
                        CellInfoLte CellInfoLteObject = (CellInfoLte) AllCellInfo.get(i);
                        DetailedAllCellInfoString += "\nCellID: " + CellInfoLteObject.getCellIdentity().getCi();
                        DetailedAllCellInfoString += "\nMCC: " + CellInfoLteObject.getCellIdentity().getMcc();
                        DetailedAllCellInfoString += " | MNC: " + CellInfoLteObject.getCellIdentity().getMnc();
                        DetailedAllCellInfoString += " | PCI: " + CellInfoLteObject.getCellIdentity().getPci();
                        DetailedAllCellInfoString += " | TAC: " + CellInfoLteObject.getCellIdentity().getTac();
                        DetailedAllCellInfoString += "\nASU: " + CellInfoLteObject.getCellSignalStrength().getAsuLevel();
                        DetailedAllCellInfoString += "| dBm: " + CellInfoLteObject.getCellSignalStrength().getDbm();
                        DetailedAllCellInfoString += "\nTiming Advance: " + CellInfoLteObject.getCellSignalStrength().getTimingAdvance();
                        DetailedAllCellInfoString += "\n===================";
                    } else if (AllCellInfo.get(i) instanceof CellInfoWcdma) {
                        CellInfoWcdma CellInfoWcdmaObject = (CellInfoWcdma) AllCellInfo.get(i);
                        DetailedAllCellInfoString += "\nCellID: " + CellInfoWcdmaObject.getCellIdentity().getCid();
                        DetailedAllCellInfoString += "\nMCC: " + CellInfoWcdmaObject.getCellIdentity().getMcc();
                        DetailedAllCellInfoString += " | MNC: " + CellInfoWcdmaObject.getCellIdentity().getMnc();
                        DetailedAllCellInfoString += " | LAC: " + CellInfoWcdmaObject.getCellIdentity().getLac();
                        DetailedAllCellInfoString += " | PSC: " + CellInfoWcdmaObject.getCellIdentity().getPsc();
                        DetailedAllCellInfoString += "\nASU: " + CellInfoWcdmaObject.getCellSignalStrength().getAsuLevel();
                        DetailedAllCellInfoString += "| dBm: " + CellInfoWcdmaObject.getCellSignalStrength().getDbm();

                        DetailedAllCellInfoString += "\n===================";
                    } else if (AllCellInfo.get(i) instanceof CellInfoGsm) {
                        CellInfoGsm CellInfoGsmObject = (CellInfoGsm) AllCellInfo.get(i);
                        DetailedAllCellInfoString += "\nCellID: " + CellInfoGsmObject.getCellIdentity().getCid();
                        DetailedAllCellInfoString += "\nMCC: " + CellInfoGsmObject.getCellIdentity().getMcc();
                        DetailedAllCellInfoString += " | MNC: " + CellInfoGsmObject.getCellIdentity().getMnc();
                        DetailedAllCellInfoString += " | LAC: " + CellInfoGsmObject.getCellIdentity().getLac();
                        DetailedAllCellInfoString += " | PSC: " + CellInfoGsmObject.getCellIdentity().getPsc();
                        DetailedAllCellInfoString += "\nASU: " + CellInfoGsmObject.getCellSignalStrength().getAsuLevel();
                        DetailedAllCellInfoString += "| dBm: " + CellInfoGsmObject.getCellSignalStrength().getDbm();

                        DetailedAllCellInfoString += "\n======================";
                    } else {
                        DetailedAllCellInfoString += "\nNo Data in CellInfo";
                        DetailedAllCellInfoString += "\n======================";
                    }
                }
            }
        }
        else{
            DetailedAllCellInfoString += "\nNn Data in AllCellInfo";
            DetailedAllCellInfoString += "\n=======================================";

        }
        String NetworkOperator=tm.getNetworkOperator();
        String NetworkOperatorName=tm.getNetworkOperatorName();


        String WholeText="\nPhone Details:";
        WholeText+="\nIMEINumber: "+IMEINumber;
        WholeText+="\nPhoneNumber: "+Line1Number;
        WholeText+="\n===================";
        WholeText+="\nNetworkInfo: "+NetworkOperator+"  "+NetworkOperatorName;
        WholeText+="\n===================";
        WholeText+=CellLocationString;
        WholeText+=DetailedAllCellInfoString;
        textView1.setText(WholeText);
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
    public void clickRefreshButton(View view) {
        // Do something in response to button
        FillCellLocation();
        FillMCCMNC();
        FillIMSIIMEI();
        //FillCellInfo();
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
            Log.i("CellLocList", location.toString());
        }
        public void onSignalStrengthsChanged(SignalStrength signal){
            super.onSignalStrengthsChanged(signal);
            Log.w("CellSigStrength",signal.toString());
            try {
                TextView RSRPsignalVal = (TextView) findViewById(R.id.RSRPsignalVal);
                TextView RSRQsignalVal = (TextView) findViewById(R.id.RSRQsignalVal);
                TextView RSSNRsignalVal = (TextView) findViewById(R.id.RSSNRsignalVal);
                TextView RSSItextValue = (TextView) findViewById(R.id.RSSItextValue);
                //API reflections
                Method[] methods = signal.getClass().getMethods();
                //Log.w("methods",methods.toString());
                for (Method mthd : methods) {
                    if (mthd.getName().equals("getLteRsrp")) {
                        Object  sig=mthd.invoke(signal);
                        Log.i("LteRsrp",sig.toString());
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
                        Log.i("LteRsrq",sig.toString());
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
                        Log.i("LteRssnr",sig.toString());
                        RSSNRsignalVal.setText(sig.toString());
                        Field fi=signal.getClass().getField("INVALID");
                        if (sig.equals(fi.getInt(signal))){
                            RSSNRsignalVal.setText("-");
                            Log.w("INVALIDLteRsrq",sig.toString());
                        }

                    }
                    else {
                        //RSSNRsignalVal.setText("-");
                    }
                    if (signal.getGsmSignalStrength()!=99) {
                        RSSItextValue.setText(Integer.toString(2 * signal.getGsmSignalStrength() - 113));
                        Log.i("LteRssi",Integer.toString(2*signal.getGsmSignalStrength()-113));
                    }
                    else {
                        RSSItextValue.setText("-");
                    }

                }
            }
            catch (Exception e){
                Log.e("ERROR",e.toString(),e);Log.e("ERROR",e.toString(),e);
            }


        }
    }
}
