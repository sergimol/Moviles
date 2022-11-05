package com.example.engine;



import com.example.interfaces.IInput;
import java.util.List;
import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class JInput implements IInput, MouseListener  {



    public JInput(JFrame frame) {
        //Register for mouse events on blankArea and the panel.
        frame.addMouseListener(this);
    }



    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }


    void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println((eventDescription + " detected on "
                + e.getComponent().getClass().getName()
                + "."));
    }

    public void mousePressed(MouseEvent e) {
        eventOutput("Mouse pressed (# of clicks: "
                + e.getClickCount() + ")", e);
    }

    public void mouseReleased(MouseEvent e) {
        eventOutput("Mouse released (# of clicks: "
                + e.getClickCount() + ")", e);
    }

    public void mouseEntered(MouseEvent e) {
        eventOutput("Mouse entered", e);
    }

    public void mouseExited(MouseEvent e) {
        eventOutput("Mouse exited", e);
    }

    public void mouseClicked(MouseEvent e) {
        eventOutput("Mouse clicked (# of clicks: "
                + e.getClickCount() + ")", e);
    }
}
