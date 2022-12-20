package com.example.practica1;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class GameManager {

    private Context context;
    private int Money;

    public GameManager(Context c) {
        context = c;
        loadMoney();
    }


    private static String getSalt()
            throws NoSuchAlgorithmException, NoSuchProviderException
    {
        // Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

        // Create array for salt
        byte[] salt = new byte[16];

        // Get a random salt
        sr.nextBytes(salt);

        // return salt
        return salt.toString();
    }



    public void  CreateHashForFile(String fileName) throws IOException, NoSuchAlgorithmException, NoSuchProviderException {
        //el archivo ya esta guardado, ahora vamos a crear
        // la instancia/contraseña que comprueba que no se va a modificar ese archivo

        //Create checksum for this file
        FileInputStream file = context.openFileInput(fileName);

        //Use SHA-1 algorithm
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");



        //generate Salt
        String salt = getSalt();
        //ADD Salt
        shaDigest.update(salt.getBytes());



        //SHA-1 checksum
        String checksum = getFileChecksum(shaDigest, file);

        //see checksum
        System.out.println(checksum);


        //hay que guardar tanto el checkSum como la salt, como hago que esto sea seguro??

        try {
            FileOutputStream f = context.openFileOutput(fileName + "CheckSum",
                    Context.MODE_PRIVATE);

            ObjectOutputStream out = new ObjectOutputStream(f) ;
            out.writeObject(checksum.getBytes());

            out.close() ;
            f.close();

            f = context.openFileOutput(fileName + "CheckSalt",
                    Context.MODE_PRIVATE);

            out = new ObjectOutputStream(f) ;
            out.writeObject(salt.getBytes());

            out.close() ;
            f.close();

        } catch (Exception e) {
            Log.e("guardado", e.getMessage(), e);
        }
    }

    public void saveMoney() throws NoSuchAlgorithmException, IOException, NoSuchProviderException {

        try {
            FileOutputStream f = context.openFileOutput("MisterCrabMony",
                    Context.MODE_PRIVATE);

            ObjectOutputStream out = new ObjectOutputStream(f) ;
            out.writeInt(Money);

           // String text = "" +Money;
           // f.write(text.getBytes());

            out.close() ;
            f.close();
        } catch (Exception e) {
            Log.e("guardado", e.getMessage(), e);
        }

        CreateHashForFile("MisterCrabMony");
    }


    public Boolean GetCheckSumForFile(String fileName)  {

        try {
            // Reading the object from a file
            FileInputStream f = context.openFileInput(fileName);
            //FileInputStream file = new FileInputStream("MisterCrabMony");
            //Use SHA-1 algorithm
            MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");

            //vamos a recoger la ultimaSalt
            // Reading the object from a file
            FileInputStream fSalt = context.openFileInput(fileName + "CheckSalt");
            ObjectInputStream inSalt = new ObjectInputStream(fSalt);

            byte[] bytes = (byte[]) inSalt.readObject();
            String salt = new String(bytes);

            inSalt.close();
            fSalt.close();

            shaDigest.update(salt.getBytes());


            //SHA-1 checksum
            String checksum = getFileChecksum(shaDigest, f);
            f.close();

            //todo comprobar que el checksum es el mismo, por tanto el archivo nunca tuvo modificaciones

            FileInputStream fSCheck = context.openFileInput(fileName + "CheckSum");
            ObjectInputStream inCheck = new ObjectInputStream(fSCheck);

            bytes = (byte[]) inCheck.readObject();
            String check = new String(bytes, StandardCharsets.UTF_8);

            inCheck.close();
            fSCheck.close();


            //returneamos si son iguales con la sal correspondiente (somos unos cracks)
            boolean res = (check.equals(checksum));

            return res;
            //pa la siguiente hago una ensalada
            //me auto otorgo un cuñao certificate que es navida
            //los comentarios debajo de un return no cuentan
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int loadMoney(){

        try
        {

            //comprobamos que no ah sido modificado
            if (GetCheckSumForFile("MisterCrabMony")){

                // Reading the object from a file
                FileInputStream f = context.openFileInput("MisterCrabMony");
                //FileInputStream file = new FileInputStream("MisterCrabMony");
                ObjectInputStream in = new ObjectInputStream(f);
                // Method for deserialization of object
                Money = in.readInt();
                in.close();
                f.close();
                System.out.println("Object has been deserialized ");
            }
            else{
                //como castigo por modificar te lo pongo a 0
                Money = 0;
            }




        } catch(Exception ex) {
            System.out.println("Exception is caught");
            //de normal no vas a tener ni un duro mister,
            //y si te falla al cargar el dinero, pos no aber tocao el arhcivo de guardao listo.
            Money = 0;
        }



        System.out.println("tienes esta amaising cantidad de dineros: " + Money);
        return Money;
    }

    public void addMoney(int quantity){
        Money += quantity;
    }

    public boolean restMoney(int quantity){
        if (Money >= quantity){
            Money -= quantity;
            return true;
        }
        else return false;
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

        try {
            CreateHashForFile(name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

    }

    public boolean[] loadUnlocks(String nombre, int q) {

        String[] a = context.fileList();

        if (GetCheckSumForFile(nombre))
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
            return createSaveFiles(q);
        }
        return createSaveFiles(q);
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

        try {
            CreateHashForFile(name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

    }

    public int[] loadUnlocksINT(String nombre, int q) {

        String[] a = context.fileList();

        if (GetCheckSumForFile(nombre))
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
            return createSaveFilesINT(q);
        }
        return createSaveFilesINT(q);
    }

    public int[] createSaveFilesINT(int quantity) {
        int[] res = new int[quantity];
        res[0] = 1;
        return res;
    }


    private static String getFileChecksum(MessageDigest digest, FileInputStream fis) throws IOException
    {
        //Get file input stream for reading the file content
        //FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }


}
