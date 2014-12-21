package com.nsn.feve.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTestingActivity();
        FillData();
    }

    public void startTestingActivity() {
        Intent i = new Intent();
        i.setComponent(new ComponentName("com.android.settings", "com.android.settings.RadioInfo"));
        i.setAction(Intent.ACTION_MAIN);
        startActivity(i);
    }

    public void FillData() {
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String info="\nPhone Details:";
        String IMEINumber=tm.getDeviceId();
        String Line1Number=tm.getLine1Number();

        String WholeText=info;
        WholeText+="\nIMEINumber: "+IMEINumber;
        WholeText+="\nPhoneNumber: "+Line1Number;


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
}
