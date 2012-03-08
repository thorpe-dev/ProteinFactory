package uk.ac.ic.doc.protein_factory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class InstructionScreen extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.instructions);
        super.onCreate(savedInstanceState);
    }
    
    public void home(View v)
    {
        Intent i = new Intent(InstructionScreen.this,SplashScreen.class);
        InstructionScreen.this.startActivity(i);
    }
    
    public void play(View v)
    {
        Intent i = new Intent(InstructionScreen.this, ProteinFactoryActivity.class);
        InstructionScreen.this.startActivity(i);
    }
}