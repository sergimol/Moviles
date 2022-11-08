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
    private JGraphics graphics;

    public JInput(JFrame frame, JGraphics jg) {
        //Register for mouse events on blankArea and the panel.
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        eventList = new ArrayList<TouchEvent>();
        graphics  = jg;

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

    //input de raton
    public void mousePressed(MouseEvent e) {


        //repetir esta estructura con el resto de eventos que queramos registrar
        Event evento = new Event();
        //evento.x = e.getX();
        //evento.y = e.getY();
        evento.x = e.getXOnScreen() - graphics.getCanvasX();
        evento.y = e.getYOnScreen() - graphics.getCanvasY();
        evento.type = InputTouchType.TOUCH_DOWN;
        evento.index = e.getID();

        //añadir el evento a la lista de cosas procesadas
        eventList.add(evento);
        evento.source = e.getSource();

        //podemos consumir el evento para que en el caso de que el canvas sea hijo de algun otro objeto, se termine aqui la propagacion de este y el padre no tenga que vovler a procesar este input (evitando asi que se registre otra vez el input)
       //ejemplo de esot es querer registrar el pulsado de un cubo que es  hijo de el canvas con el resto de cubois, de formar que solo el cubo clickado proceasa el input)ç
        e.consume();

    }
    public void mouseReleased(MouseEvent e) {

        //repetir esta estructura con el resto de eventos que queramos registrar
        Event evento = new Event();
        //evento.x = e.getX();
        //evento.y = e.getY();
        evento.x = e.getXOnScreen() - graphics.getCanvasX();
        evento.y = e.getYOnScreen() - graphics.getCanvasY();
        evento.type = InputTouchType.TOUCH_UP;
        evento.index = e.getID();

        //añadir el evento a la lista de cosas procesadas
        eventList.add(evento);
        evento.source = e.getSource();

        //podemos consumir el evento para que en el caso de que el canvas sea hijo de algun otro objeto, se termine aqui la propagacion de este y el padre no tenga que vovler a procesar este input (evitando asi que se registre otra vez el input)
        //ejemplo de esot es querer registrar el pulsado de un cubo que es  hijo de el canvas con el resto de cubois, de formar que solo el cubo clickado proceasa el input)ç
        e.consume();

    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    //movimiento del raton
    @Override
    public void mouseDragged(MouseEvent e) {
        //repetir esta estructura con el resto de eventos que queramos registrar
        Event evento = new Event();
        //evento.x = e.getX();
        //evento.y = e.getY();
        evento.x = e.getXOnScreen() - graphics.getCanvasX();
        evento.y = e.getYOnScreen() - graphics.getCanvasY();
        evento.type = InputTouchType.TOUCH_MOVE;
        evento.index = e.getID();
        evento.source = e.getSource();
        //añadir el evento a la lista de cosas procesadas
        eventList.add(evento);

        //podemos consumir el evento para que en el caso de que el canvas sea hijo de algun otro objeto, se termine aqui la propagacion de este y el padre no tenga que vovler a procesar este input (evitando asi que se registre otra vez el input)
        //ejemplo de esto es querer registrar el pulsado de un cubo que es  hijo de el canvas con el resto de cubos, de forma que solo el cubo clickado procesa el input)
        e.consume();

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
