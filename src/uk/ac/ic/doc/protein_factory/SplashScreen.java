package uk.ac.ic.doc.protein_factory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
    }

    public void newGame(View v)
    {
        Intent i = new Intent(SplashScreen.this,ProteinFactoryActivity.class);
        SplashScreen.this.startActivity(i);

    }

    public void viewInstructions(View v)
    {
        Intent i = new Intent(SplashScreen.this,InstructionScreen.class);
        SplashScreen.this.startActivity(i);
    }
}