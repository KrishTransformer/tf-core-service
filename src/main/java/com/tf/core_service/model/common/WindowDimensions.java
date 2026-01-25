package com.tf.core_service.model.common;

public class WindowDimensions {

    public double lvTurnsPerLayer;
    public double bi;
    public double b;
    public double h;

    public WindowDimensions(double lvTurnsPerLayer, double bi, double b, double h) {
        this.lvTurnsPerLayer = lvTurnsPerLayer;
        this.bi = bi;
        this.b = b;
        this.h = h;
    }
}
