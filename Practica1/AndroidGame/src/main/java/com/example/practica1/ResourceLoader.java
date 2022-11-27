package com.example.practica1;


import com.example.androidengine.AEngine;

public class ResourceLoader {
    public void loadResources(AEngine e){
        e.getAudio().newSound("pop", "pop.wav");
        e.getAudio().newSound("tada", "tada.wav");
        e.getAudio().newSound("music", "music.wav");
    }
}