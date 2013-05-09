package org.dyndns.zubietaroberto;

import org.dyndns.zubietaroberto.tacticalactivity.TacticalActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ByteWarsBetaActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.findViewById(org.dyndns.zubietaroberto.R.id.StartButton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ByteWarsBetaActivity.this.startActivity(new Intent(ByteWarsBetaActivity.this, TacticalActivity.class));
				//ByteWarsBetaActivity.this.finish();
			}
        	
        });
    }
}