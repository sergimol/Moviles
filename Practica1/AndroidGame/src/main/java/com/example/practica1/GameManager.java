package com.example.practica1;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class GameManager {

    Context context;
    int Money;

    public GameManager(Context c) {
        context = c;
    }

    public void saveUnlocks(boolean[] unlocks, String name) {
        try {
            FileOutputStream f = context.openFileOutput(name,
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

        String[] a = context.fileList();

        try {
            int quantity = q;
            for (String file : a) {
                if (file.equals(nombre)) {
                    //file exits
                    FileInputStream f = context.openFileInput(nombre);
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

    //METODOS PARA INT (LO HARIA SOLO CON ESTOS TODO)

    public void saveUnlocksINT(int[] unlocks, String name) {
        try {
            FileOutputStream f = context.openFileOutput(name,
                    Context.MODE_PRIVATE);
            String text = "" + unlocks.length;
            for (int i = 0; i < unlocks.length; i++) {
                text += ("\n" + (unlocks[i]));
            }
            f.write(text.getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("guardado", e.getMessage(), e);
        }
    }

    public int[] loadUnlocksINT(String nombre, int q) {

        String[] a = context.fileList();

        try {
            int quantity = q;
            for (String file : a) {
                if (file.equals(nombre)) {
                    //file exits
                    FileInputStream f = context.openFileInput(nombre);
                    BufferedReader input = new BufferedReader(
                            new InputStreamReader(f));

                    int n = 0;
                    String line;
                    int read;

                    line = input.readLine();
                    quantity = Integer.parseInt(line);
                    int[] unlocks = new int[quantity];

                    do {
                        line = input.readLine();
                        if (line != null) {
                            read = Integer.parseInt(line);
                            unlocks[n] = read;
                            n++;
                        }
                    }
                    while (n < quantity && line != null);

                    f.close();
                    return unlocks;
                }
            }
            return createSaveFilesINT(quantity);
        } catch (Exception e) {
            Log.e("guardados error", e.getMessage(), e);
            return null;
        }
    }

    public int[] createSaveFilesINT(int quantity) {
        int[] res = new int[quantity];
        res[0] = 1;
        return res;
    }


}
