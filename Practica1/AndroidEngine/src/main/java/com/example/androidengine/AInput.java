package com.example.androidengine;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AInput extends View implements View.OnTouchListener {

    public static enum InputTouchType{
        TOUCH_DOWN,
        TOUCH_UP,
        TOUCH_MOVE
    }

    public static class TouchEvent{
        public TouchEvent(){}

        public float x;
        public float y;

        public InputTouchType type;
        public int index;
        public Object source;
    }

    private List<TouchEvent> eventList;
    private AGraphics graphics;

    public AInput(SurfaceView window, AGraphics jg) {
        super(window.getContext());
        window.setOnTouchListener(this);
        System.out.println("input init!");
        eventList = new ArrayList<TouchEvent>();
        graphics = jg;
    }

    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }

    public void emptyTouchEvents() {
        eventList.clear();
    }

    public List<TouchEvent> getTouchEvents() {
        return new ArrayList<>(eventList);
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        //recoger datos del evento, posicion y tipo, añadirlo a la lista de eventos
        TouchEvent event = new TouchEvent();
        event.x = (e.getX() - graphics.getCanvasX()) / graphics.getScale();
        event.y = (e.getY() - graphics.getCanvasY()) / graphics.getScale();
        //System.out.println("Coordenadas: " +  evento.x + ", " + evento.y);
        event.type = InputTouchType.values()[e.getAction()];


        event.index = e.getActionIndex();

        eventList.add(event);

        return true;
    }
}
