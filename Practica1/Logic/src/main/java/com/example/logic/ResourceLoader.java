package com.example.logic;

import com.example.interfaces.IEngine;

public class ResourceLoader {
    public void loadResources(IEngine e){
        e.getAudio().newSound("pop", "pop.wav");
    }
}
