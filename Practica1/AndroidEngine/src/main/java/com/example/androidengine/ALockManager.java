package com.example.androidengine;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class ALockManager extends AppCompatActivity {

    public ALockManager(){}

    public void saveUnlocks(boolean[] unlocks, String name) {
        try {
            FileOutputStream f = openFileOutput(name,
                    Context.MODE_PRIVATE);
            String text = "" + unlocks.length;
            for (int i = 0; i < unlocks.length; i++) {
                text += ("\n" + (unlocks[i] ? 1 : 0));
            }
            f.write(text.getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("guardado", e.getMessage(), e);
        }
    }

    public boolean[] loadUnlocks(String nombre, int q) {

        String[] a = fileList();

        try {
            int quantity = q;
            for (String file : a) {
                if (file.equals(nombre)) {
                    //file exits
                    FileInputStream f = openFileInput(nombre);
                    BufferedReader input = new BufferedReader(
                            new InputStreamReader(f));

                    int n = 0;
                    String line;
                    int read;

                    line = input.readLine();
                    quantity = Integer.parseInt(line);
                    boolean[] unlocks = new boolean[quantity];

                    do {
                        line = input.readLine();
                        if (line != null) {
                            read = Integer.parseInt(line);
                            unlocks[n] = (read == 1);
                            n++;
                        }
                    }
                    while (n < quantity && line != null);

                    f.close();
                    return unlocks;
                }
            }
            return createSaveFiles(quantity);
        } catch (Exception e) {
            Log.e("guardados error", e.getMessage(), e);
            return null;
        }
    }

    public boolean[] createSaveFiles(int quantity) {
        boolean[] res = new boolean[quantity];
        res[0] = true;
        return res;
    }
}
