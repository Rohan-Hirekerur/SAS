package com.example.rohan.sas;

import java.util.ArrayList;

public class Lists {
    ArrayList<Boolean> bookmarked;
    ArrayList<Integer> marked;

    public Lists(ArrayList<Boolean> bookmarked, ArrayList<Integer> marked) {
        this.bookmarked = bookmarked;
        this.marked = marked;
    }

    public ArrayList<Boolean> getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(ArrayList<Boolean> bookmarked) {
        this.bookmarked = bookmarked;
    }

    public ArrayList<Integer> getMarked() {
        return marked;
    }

    public void setMarked(ArrayList<Integer> marked) {
        this.marked = marked;
    }
}
