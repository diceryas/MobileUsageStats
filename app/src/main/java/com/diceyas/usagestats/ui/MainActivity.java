package com.diceyas.usagestats.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.diceyas.usagestats.R;

import com.diceyas.usagestats.db.LocalDataBase;
import com.diceyas.usagestats.db.MyDataBaseHelper;
import com.diceyas.usagestats.lib.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.diceyas.usagestats.lib.yalantis.contextmenu.lib.MenuObject;
import com.diceyas.usagestats.lib.yalantis.contextmenu.lib.MenuParams;
import com.diceyas.usagestats.lib.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.diceyas.usagestats.lib.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private MyDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDataBaseHelper(this, "lianji.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        LocalDataBase.init(db, this);



        Intent intent = new Intent();
        intent.setAction("com.broadcast.boot");
        //无序广播
        this.sendBroadcast(intent);

        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();

        addFragment(new MainFragment(), true, R.id.container);
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject rank = new MenuObject("排行榜");
        rank.setResource(R.drawable.icn_ranking);

        MenuObject cen = new MenuObject("日历");
        cen.setResource(R.drawable.icn_cenlander);

        MenuObject usr = new MenuObject("用户");
        usr.setResource(R.drawable.icn_setting);

        MenuObject un = new MenuObject("注销");
        un.setResource(R.drawable.setting3);


        menuObjects.add(close);
        menuObjects.add(rank);
        menuObjects.add(cen);
        menuObjects.add(usr);
        menuObjects.add(un);
        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setBackgroundResource(R.color.barcolor);
        mToolbar.setNavigationIcon(R.drawable.empty);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("恋机指数");
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        if (position == 1) {
            Intent i = new Intent(MainActivity.this, UserRankList.class);
            startActivity(i);
            finish();
        } else if (position == 2) {
            Intent i = new Intent(MainActivity.this, History.class);
            startActivity(i);
            finish();
        } else if (position == 3) {
            Intent i = new Intent(MainActivity.this, Setting.class);
            startActivity(i);
            finish();
        } else if (position == 4) {
            //注销操作
            SharedPreferences mySharedPreferences = getSharedPreferences("test",
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putString("userName", "");
            editor.putString("password", "");
            editor.commit();
            finish();
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

}
