package com.diceyas.usagestats.ui;

import com.diceyas.usagestats.R;

/**
 * Created by lenovo on 2016/5/29.
 */
public class exchange {
    public static int getimage(int n)
    {
        switch(n)
        {
            case 0:
                return R.drawable.image00;
            case 1:
                return R.drawable.image02;
            case 2:
                return R.drawable.image06;
            case 3:
                return R.drawable.image10;
            case 4:
                return R.drawable.image11;
            case 5:
                return R.drawable.image12;
            case 6:
                return R.drawable.image19;
            case 7:
                return R.drawable.image20;
            case 8:
                return R.drawable.image03;
            case 9:
                return R.drawable.image21;
            default:
                return R.drawable.image04;

        }
    }
}
