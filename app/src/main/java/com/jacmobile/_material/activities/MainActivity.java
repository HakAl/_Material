package com.jacmobile._material.activities;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.jacmobile._material.R;
import com.jacmobile._material.Utils;
import com.jacmobile._material.adapter.ApplicationAdapter;
import com.jacmobile._material.entity.AppInfo;
import com.jacmobile._material.util.UploadHelper;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alex on 10/12/14.
 */
public class MainActivity extends ABaseActivity
{
    private ApplicationAdapter mAdapter;
    private List<AppInfo> applicationList = new ArrayList<AppInfo>();

    private Toolbar toolbar;
    private LinearLayout mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    private View fabButton;
    private RecyclerView mRecyclerView;

    private boolean isOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Handle ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.hello_world, R.string.action_settings);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        // Handle DrawerList
        mDrawerList = (LinearLayout) findViewById(R.id.drawerList);


        ((Switch) mDrawerList.findViewById(R.id.drawer_autoupload)).setChecked(isOn);
        ((Switch) mDrawerList.findViewById(R.id.drawer_autoupload)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                isOn = !isOn;
            }
        });

        mDrawerList.findViewById(R.id.drawer_opensource).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this, "Drawer", Toast.LENGTH_LONG).show();
            }
        });
        ((ImageView) mDrawerList.findViewById(R.id.drawer_opensource_icon)).setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_github).colorRes(R.color.primary).actionBarSize());

        // Fab Button
        fabButton = findViewById(R.id.fab_button);
        fabButton.setOnClickListener(fabClickListener);
        Utils.configureFab(fabButton);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>(), R.layout.row_application, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);


        new InitializeApplicationsTask().execute();
    }


    View.OnClickListener fabClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            UploadHelper.getInstance(MainActivity.this, applicationList).uploadAll();
        }
    };


    public void animateActivity(AppInfo appInfo, View appIcon)
    {
//        Intent i = new Intent(this, DetailActivity.class);
//        i.putExtra("appInfo", appInfo.getComponentName());
//
//        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(fabButton, "fab"), Pair.create(appIcon, "appIcon"));
//        startActivity(i, transitionActivityOptions.toBundle());
    }


    private class InitializeApplicationsTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            //MainActivity.this.setProgressBarIndeterminate(true);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            //Clean up ail
            applicationList.clear();

            List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
            for (ResolveInfo ri : ril) {
                applicationList.add(new AppInfo(MainActivity.this, ri));
            }
            Collections.sort(applicationList);

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            //MainActivity.this.setProgressBarIndeterminate(false);
            mAdapter.setApplications(applicationList);

            Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left);
            fadeIn.setDuration(250);
            LayoutAnimationController layoutAnimationController = new LayoutAnimationController(fadeIn);
            mRecyclerView.setLayoutAnimation(layoutAnimationController);
            mRecyclerView.startLayoutAnimation();

            super.onPostExecute(result);
        }
    }

}
