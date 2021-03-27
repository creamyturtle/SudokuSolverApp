package com.example.sudokusolver;

public class Solver {

    private static int selected_row;
    private static int selected_column;

    Solver(){
        selected_row = -1;
        selected_column = -1;


    }

    public int getSelectedRow(){
        return selected_row;
    }

    public int getSelectedColumn(){
        return selected_column;
    }

    public void setSelectedRow(int r){
        selected_row = r;
    }

    public void setSelectedColumn(int c){
        selected_column = c;
    }
}
