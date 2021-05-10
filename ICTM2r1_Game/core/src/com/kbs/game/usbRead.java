package com.kbs.game;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.TimerTask;

public class usbRead {
    private static SerialPort comPort = SerialPort.getCommPorts()[0];

    //TODO: Zorgen dat er een structuur komt in de data die verstuurd word, zodat kan worden onderscheden tussen verschillende waardes.
    public static String getSensor(){
        //Lees de sensorwaardes van de arduino uit

        comPort.openPort();
        try {
            while (true) {
                while (comPort.bytesAvailable() == 0)
                    Thread.sleep(20);
                byte[] readBuffer = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                String output = new String(readBuffer, StandardCharsets.UTF_8);
                //System.out.println("Read " + numRead + " bytes. value: " + output); //Debug line
                //TODO: misschien een bepaalde waarde aan het eind van de verstuurde data toevoegen zodat het systeem weet of hij alles correct gelezen heeft.
                if (numRead == 4) {
                    return output;
                }
            }
        } catch (NegativeArraySizeException Ne){
            System.out.println("Negative readbuffer size!, Negative amount of bytes availible!");
        } catch (Exception e) { e.printStackTrace(); }
        comPort.closePort();
        return "Nan";
    }


}
