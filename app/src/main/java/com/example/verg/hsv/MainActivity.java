package com.example.verg.hsv;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import vis.custom.view.VHSV;
import vis.custom.view.VHSVKer;

public class MainActivity extends Activity {
    private VHSV mVhsv;
    private Button[] btns = new Button[3];
    /**
     * 图片标题名
     */
    private String[] titleName = new String[]{"Ren", "Hello", "Video",
            "Chess", "Lifebuoy", "Mail", "Ebay", "Facebook"};
    /**
     * 图片资源ID
     */
    private int[] imageRid = new int[]{R.mipmap.im0, R.mipmap.im1,
            R.mipmap.im2, R.mipmap.im3, R.mipmap.im4, R.mipmap.im5,
            R.mipmap.im6, R.mipmap.im7};
    private VHSV mVhsv3;
    private VHSV mVhsv7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVhsv = (VHSV) findViewById(R.id.vhsv);
        mVhsv3 = (VHSV) findViewById(R.id.vhsv3);
        mVhsv7 = (VHSV) findViewById(R.id.vhsv7);
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);

        mVhsv.setOnClickListener(new VHSVKer.OnClickListener() {
            @Override
            public void onClick(int selectedItem) {
                btns[2].setText(String.valueOf(selectedItem));
                btns[1].setText(titleName[selectedItem]);
            }
        });
        btns[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVhsv.setWhatINeed(titleName, imageRid, 5, 2000);
        mVhsv3.setWhatINeed(titleName, imageRid, 3, 1500);
        mVhsv7.setWhatINeed(titleName, imageRid, 7, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
