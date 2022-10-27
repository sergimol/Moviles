package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

/*

* JFRame window = new JFrame ("ventana")
* window.setSize(width, height);
* window.setVisible(true)
  DEngine engine = new DEngine(window)
  OScene scene = new OScene();
  engine.start(scene);

* public void resume(){
    this.currentThread = new Thread
}
*
*
* */