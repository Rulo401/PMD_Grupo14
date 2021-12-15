package upm.pmd.grupo14.common;

import android.app.Activity;

import upm.pmd.grupo14.R;

/**
 * Enum class with the categories defined by the server.
 */
public enum Category {
    National,Sports,Technology,Economy,None;

    /**
     * Method for showing the category in the UI.
     * @param act Activity where the method is called
     * @return Category name specified in the language used by the user
     */
    public String show(Activity act){
        if(this == None) return "";
        return act.getResources().getStringArray(R.array.categories)[this.ordinal()];
    }
}
