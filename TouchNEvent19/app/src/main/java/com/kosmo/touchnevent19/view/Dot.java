package com.kosmo.touchnevent19.view;

public class Dot {
    //터치한 지정ㅁ의 x,y 좌표 지정
    private int xPos, yPos;
    //그리기 여부 판단
    private boolean isDrawing;
    //constructor
    public Dot() {}
    public Dot(int xPos, int yPos, boolean isDrawing) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isDrawing = isDrawing;
    }

    //getter, setter
    public int getxPos() {
        return xPos;
    }
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    public int getyPos() {
        return yPos;
    }
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    public boolean isDrawing() {
        return isDrawing;
    }
    public void setDrawing(boolean drawing) {
        isDrawing = drawing;
    }

}
