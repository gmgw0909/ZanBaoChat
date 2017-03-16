package com.leeboo.zanbaochat;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.leeboo.zanbaochat.fragment.FriendFragment;
import com.leeboo.zanbaochat.fragment.MessageFragment;
import com.leeboo.zanbaochat.fragment.ZoneFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.tbHeadBar)
    Toolbar mTbHeadBar;
    /*侧滑菜单布局*/
    @InjectView(R.id.llMenu)
    LinearLayout mLlMenu;
    /*侧滑菜单ListView放置菜单项*/
    @InjectView(R.id.lvMenu)
    ListView mLvMenu;
    @InjectView(R.id.dlMenu)
    DrawerLayout mMyDrawable;
    @InjectView(R.id.btn_message)
    Button btnMessage;
    @InjectView(R.id.btn_friend)
    Button btnFriend;
    @InjectView(R.id.btn_zone)
    Button btnZone;

    ActionBarDrawerToggle mToggle;

    private FragmentManager fm;
    private FragmentTransaction ft;

    MessageFragment f1;
    FriendFragment f2;
    ZoneFragment f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.inject(this);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        /*初始化Toolbar与DrawableLayout*/
        initToolBarAndDrawableLayout();
        /*初始化 right_content*/
        initHomePageRightContent();

    }

    private void initHomePageRightContent() {
        f1 = new MessageFragment();
        f2 = new FriendFragment();
        f3 = new ZoneFragment();
        setBackgroundColorById();
        btnMessage.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_content, f1);
        ft.commit();
    }

    private void initToolBarAndDrawableLayout() {
        setSupportActionBar(mTbHeadBar);
        /*以下俩方法设置返回键可用*/
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*设置标题文字不可显示*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToggle = new ActionBarDrawerToggle(this, mMyDrawable, mTbHeadBar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Toast.makeText(MainActivity.this, R.string.open, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //Toast.makeText(MainActivity.this, R.string.close, Toast.LENGTH_SHORT).show();
            }
        };
        mMyDrawable.addDrawerListener(mToggle);
        mToggle.syncState();/*同步状态*/

        mLvMenu.setAdapter(new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, lvMenuList));
        mLvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomePageActivity.this, lvMenuList.get(position), Toast.LENGTH_SHORT).show();
                mMyDrawable.closeDrawers();/*收起抽屉*/
            }
        });
    }

    private List<String> lvMenuList = new ArrayList<String>() {{
        add("item1");
        add("item2");
        add("item3");
        add("item4");
    }};

    private List<Integer> imageList = new ArrayList<Integer>() {{
        add(R.mipmap.ic_launcher);
        add(R.mipmap.ic_launcher);
        add(R.mipmap.ic_launcher);
        add(R.mipmap.ic_launcher);
    }};

    private void setBackgroundColorById() {
        btnMessage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnFriend.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnZone.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }


    @OnClick({R.id.btn_message, R.id.btn_friend, R.id.btn_zone})
    public void onClick(View view) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        setBackgroundColorById();
        switch (view.getId()) {
            case R.id.btn_message:
                ft.replace(R.id.fragment_content, f1);
                btnMessage.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case R.id.btn_friend:
                ft.replace(R.id.fragment_content, f2);
                btnFriend.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case R.id.btn_zone:
                ft.replace(R.id.fragment_content, f3);
                btnZone.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
        }
        ft.commit();
    }
}
