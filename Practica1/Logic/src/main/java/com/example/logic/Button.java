package com.example.logic;


import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;

enum States {
    normal, selected
}


public class Button {

private IFont fuente;
private String texto;
private float PosX;
private float PosY;
private float SizeX;
private float SizeY;

    private int clickTopX;
    private int clickTopY;
    private int clickBottomX;
    private int clickBottomY;
    ;

Button (IFont f, String text, float x, float y, float sizeX_, float sizeY_){
fuente = f;
texto = text;
PosX = x;
PosY = y;
SizeX = sizeX_;
SizeY = sizeY_;
//colores de los estados de los botones y bordes??¿¿ yo calentandome

}


void render(IGraphics graphics) {

    //(int)(graphics.getOriginalWidth() / 2) - 15,
      //      (int) (graphics.getOriginalHeight() * 0.5) - 15,
        //    30,
          //  30



    graphics.setColor(0x0000000);

    graphics.fillRect(
            graphics.getOriginalWidth() * (PosX -(SizeX/2)) ,
            graphics.getOriginalHeight() * (PosY - (SizeY/2)),
            graphics.getOriginalWidth() * SizeX,
            graphics.getOriginalHeight() * SizeY);

    clickTopX = (int)((graphics.getWidth()- graphics.getCanvasX()*2 ) * (PosX  -(SizeX/2)))   ;
    clickTopY = (int)((graphics.getHeight() - graphics.getCanvasY()*2) * (PosY  -(SizeY/2))) ;
    clickBottomX = (int)((graphics.getWidth()- graphics.getCanvasX()*2 ) * (PosX  +(SizeX/2))) ;
    clickBottomY = (int)((graphics.getHeight() - graphics.getCanvasY()*2) * (PosY  +(SizeY)));

    graphics.fillRect(
            clickTopX ,
            clickTopY,
            graphics.getOriginalWidth() * SizeX,
            graphics.getOriginalHeight() * SizeY);

    graphics.setColor(0x808080);
    graphics.fillRect(
            graphics.getOriginalWidth() * (PosX  -(((SizeX - SizeX/10.0f))/2)) ,
            graphics.getOriginalHeight() * (PosY  - (((SizeY - SizeY/10.0f))/2)),
            graphics.getOriginalWidth() * (SizeX - SizeX/10.0f),
            graphics.getOriginalHeight() * (SizeY - SizeY/10.0f));

    graphics.setColor(0x0);
    graphics.setFont(fuente);
    graphics.drawText(texto,
            graphics.getOriginalWidth() * (PosX ) - graphics.getFontWidth(texto) / 2 ,
            graphics.getOriginalHeight() * (PosY));

}

boolean click(int x, int y){

    System.out.println("pos " + x + " " +y + " limites " + clickTopX + " " +clickTopY + " botton " + clickBottomX + " " + clickBottomY);

    if(x >= clickTopX && x < clickBottomX)
        if(y >= clickTopY && y < clickBottomY){
            return true;
        }
    return false;
}


}
