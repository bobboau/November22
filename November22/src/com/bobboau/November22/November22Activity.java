package com.bobboau.November22;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class November22Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Toast toast = Toast.makeText(this, "hold your finger down to add a disk\nhold down over an exsisting disk and drag to move", 3000);
        toast.show();
    }
}