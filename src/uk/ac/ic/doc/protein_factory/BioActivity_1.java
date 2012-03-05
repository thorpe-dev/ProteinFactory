package uk.ac.ic.doc.protein_factory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 02/03/12
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class BioActivity_1 extends Activity {

    private static final String TAG = BioActivity_1.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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