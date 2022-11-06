package com.example.interfaces;

import java.awt.Component;
import java.util.List;

public interface IInput {


    void emptyTouchEvents();



    //Android
    public static enum InputTouchType{
        TOUCH_DOWN,
        TOUCH_UP,
        TOUCH_MOVE
    }
    //Desktop
    public static enum InputKeyType{
        KEY_DOWN,
        KEY_UP,
        KEY_MOVE
    }

    //Estamos creando las clases aqui pero podriamos tenerlas por separado
    public static class Event extends TouchEvent {

        public Event() {

        }

        public int x;
        public int y;

        public InputTouchType type;
        public int index;   //Indice del evento dentro del este de eventos
        public Object source;
    }

    class TouchEvent{

    }


    List<TouchEvent> getTouchEvents();
}
