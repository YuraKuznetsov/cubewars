package org.geekhub.yurii.model.solve;

import lombok.Data;

import java.util.List;

@Data
public class Statistics {

    private List<Time> times;
    private List<Time> ao5;
    private List<Time> ao12;
    private List<Time> ao50;
    private List<Time> ao100;
    private Time best;
    private Time worst;
    private Time mean;
    private int count;
}
