package upm.pmd.grupo14.common;

import android.app.Activity;

import upm.pmd.grupo14.R;

public enum Category {
    National,Sports,Technology,Economy,None;

    public String show(Activity act){
        if(this == None) return "";
        return act.getResources().getStringArray(R.array.categories)[this.ordinal()];
    }
}
