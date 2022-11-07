package com.example.androidengine;

import android.content.Context;
import android.view.View;

import com.example.interfaces.IInput;

import java.util.List;

public class AInput extends View implements IInput {


    public AInput(Context context) {
        super(context);
    }

    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }

    @Override
    public void emptyTouchEvents() {
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }
}
