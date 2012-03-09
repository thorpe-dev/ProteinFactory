package uk.ac.ic.doc.protein_factory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ProteinFactoryActivity extends Activity {

    private static final String TAG = ProteinFactoryActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MainGamePanel(this));
        Log.d(TAG,"View Added");
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG,"Destroying");
        super.onDestroy();
    }

    @Override
    protected void onStop()
    {
        Log.d(TAG,"Stopping");
        super.onStop();
    }
}