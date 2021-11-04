package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;

public class All_Series {
//    private final SimpleIntegerProperty _id;
    private SimpleIntegerProperty _X;
    private SimpleIntegerProperty _Y;

    public All_Series( Integer _X, Integer _Y) {
//        this._id = new SimpleIntegerProperty(_id);
        this._X = new SimpleIntegerProperty(_X);
        this._Y = new SimpleIntegerProperty(_Y);

    }
    public int get_X() {
        return _X.get();
    }

    public void set_X(int _X){
        this._X = new SimpleIntegerProperty(_X);
    }
    public void set_Y(int _Y){
        this._Y = new SimpleIntegerProperty(_Y);
    }

    public int get_Y() {
        return _Y.get();
    }


}
