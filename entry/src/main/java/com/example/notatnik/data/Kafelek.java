package com.example.notatnik.data;

public class Kafelek{
  private String wiekszy, mniejszy, slice;
  public Kafelek(String wiekszy, String mniejszy, String slice){
      this.mniejszy =mniejszy;
      this.slice = slice;
      this.wiekszy = wiekszy;
  }

    public String getMniejszy() {
        return mniejszy;
    }

    public String getSlice() {
        return slice;
    }

    public String getWiekszy() {
        return wiekszy;
    }

    public void setMniejszy(String mniejszy) {
        this.mniejszy = mniejszy;
    }
}