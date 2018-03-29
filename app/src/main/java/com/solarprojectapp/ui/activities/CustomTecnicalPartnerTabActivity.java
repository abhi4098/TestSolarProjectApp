package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.ui.adapters.ViewPagerAdapter;
import com.solarprojectapp.ui.fragments.ClosedTabFragment;
import com.solarprojectapp.ui.fragments.InProgressTabFragment;
import com.solarprojectapp.ui.fragments.OpenTabFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomTecnicalPartnerTabActivity extends AppCompatActivity implements View.OnClickListener {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
    Context mContext;

    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    OpenTabFragment openTabFragment;
    InProgressTabFragment inProgressFragment;
    ClosedTabFragment closedTabFragment;

    String[] tabTitle={"OPEN","IN PROGRESS","CLOSED"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        mContext = CustomTecnicalPartnerTabActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("COMPLAINT LIST");

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        try
        {
            setupTabIcons();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);
                Log.e("abhi", "onPageSelected: ............." +position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }


    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        openTabFragment=new OpenTabFragment();
        inProgressFragment=new InProgressTabFragment();
        closedTabFragment=new ClosedTabFragment();
        adapter.addFragment(openTabFragment,"OPEN");
        adapter.addFragment(inProgressFragment,"IN PROGRESS");
        adapter.addFragment(closedTabFragment,"CLOSED");
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(tabTitle[pos]);
        return view;
    }

    private void setupTabIcons()
    {

        for(int i=0;i<tabTitle.length;i++)
        {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }


    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }
}
