package com.example.androidengine;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.interfaces.IInput;

import java.util.ArrayList;
import java.util.List;

public class AInput extends View implements IInput, View.OnTouchListener {

    private List<TouchEvent> eventList;

    public AInput(SurfaceView window) {
        super(window.getContext());
        window.setOnTouchListener(this);
        System.out.println("input init!");
        eventList = new ArrayList<TouchEvent>();
    }

    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }

    @Override
    public void emptyTouchEvents() {
        eventList.clear();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        //System.out.println("un evento!");
        //recoger datos del evento, posicion y tipo, añadirlo a la lista de eventos
        Event evento = new Event();
        evento.x = e.getX();
        evento.y = e.getY();
        evento.type = InputTouchType.values()[e.getAction()];

        System.out.println(e.getAction());

        evento.index = e.getActionIndex();

        eventList.add(evento);

        return true;
    }
}
