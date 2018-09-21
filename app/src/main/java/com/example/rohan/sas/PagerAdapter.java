package com.example.rohan.sas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;
    ArrayList<MCQ> list;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList list) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Question tab1 = new Question();
                Bundle bundle = new Bundle();
                bundle.putSerializable("mcq",list.get(0));
                bundle.putInt("pos",0);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                Question tab2 = new Question();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("mcq",list.get(1));
                bundle1.putInt("pos",1);
                tab2.setArguments(bundle1);
                return tab2;
            case 2:
                Question tab3 = new Question();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("mcq",list.get(2));
                bundle2.putInt("pos",2);
                tab3.setArguments(bundle2);
                return tab3;
            case 3:
                Question tab4 = new Question();
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("mcq",list.get(3));
                bundle3.putInt("pos",3);
                tab4.setArguments(bundle3);
                return tab4;
            case 4:
                Question tab5 = new Question();
                Bundle bundle4 = new Bundle();
                bundle4.putSerializable("mcq",list.get(4));
                bundle4.putInt("pos",4);
                tab5.setArguments(bundle4);
                return tab5;
            case 5:
                Question tab6 = new Question();
                Bundle bundle5 = new Bundle();
                bundle5.putSerializable("mcq",list.get(5));
                bundle5.putInt("pos",5);
                tab6.setArguments(bundle5);
                return tab6;
            case 6:
                Question tab7 = new Question();
                Bundle bundle6 = new Bundle();
                bundle6.putSerializable("mcq",list.get(6));
                bundle6.putInt("pos",6);
                tab7.setArguments(bundle6);
                return tab7;
            case 7:
                Question tab8 = new Question();
                Bundle bundle7 = new Bundle();
                bundle7.putSerializable("mcq",list.get(7));
                bundle7.putInt("pos",7);
                tab8.setArguments(bundle7);
                return tab8;
            case 8:
                Question tab9 = new Question();
                Bundle bundle8 = new Bundle();
                bundle8.putSerializable("mcq",list.get(8));
                bundle8.putInt("pos",8);
                tab9.setArguments(bundle8);
                return tab9;
            case 9:
                Question tab10 = new Question();
                Bundle bundle9 = new Bundle();
                bundle9.putSerializable("mcq",list.get(9));
                bundle9.putInt("pos",9);
                tab10.setArguments(bundle9);
                return tab10;
            case 10:
                SubmitFragment submitFragment = new SubmitFragment();
                return submitFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
