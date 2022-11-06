package com.example.engine;



import com.example.interfaces.IInput;

import java.awt.Component;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList; //<----------- podemos usarlo¿?



import java.util.List;
import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class JInput implements IInput, MouseListener, MouseMotionListener {


    private List<TouchEvent> eventList;


    public JInput(JFrame frame) {
        //Register for mouse events on blankArea and the panel.
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        eventList = new ArrayList<TouchEvent>();
    }

    public void madeInput(Component frame) {
        //Register for mouse events on blankArea and the panel.
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
    }


    @Override
    public List<TouchEvent> getTouchEvents() {
        return eventList;
    }

    //habra que vaciar eventualmente la lista de inputs procesados para que no ocupen toda la memoria.
    @Override
    public void emptyTouchEvents(){
        eventList.clear();
    }




    void eventOutput(String eventDescription, MouseEvent e) {

        e.getSource();

        System.out.println((eventDescription + " detected on "
                + e.getComponent().getClass().getName()
                + "."));
    }

    //input de raton
    public void mousePressed(MouseEvent e) {


        //repetir esta estructura con el resto de eventos que queramos registrar
        Event evento = new Event();
        evento.x = e.getX();
        evento.y = e.getY();
        //evento.x = e.getXOnScreen();
        //evento.y = e.getYOnScreen();
        evento.type = InputTouchType.TOUCH_DOWN;
        evento.index = e.getID();

        //añadir el evento a la lista de cosas procesadas
        eventList.add(evento);
        evento.source = e.getSource();

        //podemos consumir el evento para que en el caso de que el canvas sea hijo de algun otro objeto, se termine aqui la propagacion de este y el padre no tenga que vovler a procesar este input (evitando asi que se registre otra vez el input)
       //ejemplo de esot es querer registrar el pulsado de un cubo que es  hijo de el canvas con el resto de cubois, de formar que solo el cubo clickado proceasa el input)ç
        e.consume();

        eventOutput("Mouse pressed (# of clicks: "
                + e.getClickCount() + " position: " +e.getX() + " " + e.getY() + " )", e);
    }
    public void mouseReleased(MouseEvent e) {

        //repetir esta estructura con el resto de eventos que queramos registrar
        Event evento = new Event();
        evento.x = e.getX();
        evento.y = e.getY();
        //evento.x = e.getXOnScreen();
        //evento.y = e.getYOnScreen();
        evento.type = InputTouchType.TOUCH_UP;
        evento.index = e.getID();

        //añadir el evento a la lista de cosas procesadas
        eventList.add(evento);
        evento.source = e.getSource();

        //podemos consumir el evento para que en el caso de que el canvas sea hijo de algun otro objeto, se termine aqui la propagacion de este y el padre no tenga que vovler a procesar este input (evitando asi que se registre otra vez el input)
        //ejemplo de esot es querer registrar el pulsado de un cubo que es  hijo de el canvas con el resto de cubois, de formar que solo el cubo clickado proceasa el input)ç
        e.consume();


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
    //movimiento del raton
    @Override
    public void mouseDragged(MouseEvent e) {
        //repetir esta estructura con el resto de eventos que queramos registrar
        Event evento = new Event();
        evento.x = e.getX();
        evento.y = e.getY();
        //evento.x = e.getXOnScreen();
        //evento.y = e.getYOnScreen();
        evento.type = InputTouchType.TOUCH_MOVE;
        evento.index = e.getID();
        evento.source = e.getSource();
        //añadir el evento a la lista de cosas procesadas
        eventList.add(evento);

        //podemos consumir el evento para que en el caso de que el canvas sea hijo de algun otro objeto, se termine aqui la propagacion de este y el padre no tenga que vovler a procesar este input (evitando asi que se registre otra vez el input)
        //ejemplo de esto es querer registrar el pulsado de un cubo que es  hijo de el canvas con el resto de cubos, de forma que solo el cubo clickado procesa el input)
        e.consume();



        eventOutput("Mouse pressed moved (button: "
                + e.getButton() + " position: " +e.getX() + " " + e.getY() + " )", e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
