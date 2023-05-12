package Serialization;

import java.awt.Dimension;
import java.awt.Point;

public class Frame implements State {

    public Dimension dim;
    public Point location;
    public boolean isIcon, isMaximum, isSelected;

    public Frame() {
        setOptions(new Dimension(0, 0), new Point(0, 0), false, false, false);
    }

    public Frame(Dimension windowSize, Point windowLocation, boolean isIcon, boolean isMaximum,
                 boolean isSelected){
        setOptions(windowSize, windowLocation, isIcon, isMaximum, isSelected);
    }
    public void setOptions(Dimension windowSize, Point windowLocation, boolean isIcon, boolean isMaximum,
                           boolean isSelected) {
        this.dim = windowSize;
        this.location = windowLocation;
        this.isIcon = isIcon;
        this.isMaximum = isMaximum;
        this.isSelected = isSelected;
    }

    @Override
    public void copy(Object obj) {
        Frame frameState = (Frame) obj;
        setOptions(frameState.dim, frameState.location,
                frameState.isIcon, frameState.isMaximum, frameState.isSelected);
    }
}