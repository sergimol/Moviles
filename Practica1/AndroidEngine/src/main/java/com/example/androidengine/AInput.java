package com.example.androidengine;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AInput extends View implements View.OnTouchListener {

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
        //System.out.println("un evento!");
        //recoger datos del evento, posicion y tipo, añadirlo a la lista de eventos
        Event evento = new Event();
        evento.x = (e.getX() - graphics.getCanvasX()) / graphics.getScale();
        evento.y = (e.getY() - graphics.getCanvasY()) / graphics.getScale();
        //System.out.println("Coordenadas: " +  evento.x + ", " + evento.y);
        evento.type = InputTouchType.values()[e.getAction()];

        //System.out.println(e.getAction());

        evento.index = e.getActionIndex();

        eventList.add(evento);

        return true;
    }
}
