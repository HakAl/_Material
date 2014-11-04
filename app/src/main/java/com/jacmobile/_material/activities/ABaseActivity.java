package com.jacmobile._material.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.jacmobile._material.injection.ActivityScopeModule;
import com.jacmobile._material.injection.DaggerApplication;
import com.jacmobile._material.interfaces.DaggerInjector;

import dagger.ObjectGraph;


public abstract class ABaseActivity extends ActionBarActivity implements DaggerInjector
{
    private ObjectGraph mActivityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Create the activity graph by .plus-ing our modules onto the application graph.
        DaggerApplication application = (DaggerApplication) getApplication();
        mActivityGraph = application.getObjectGraph().plus(geActivitytModules());

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        mActivityGraph.inject(this);
    }

    @Override
    protected void onDestroy()
    {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected ASAP
        mActivityGraph = null;
        super.onDestroy();
    }

    protected <T> T getView(int id)
    {
        return (T) findViewById(id);
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    @Override
    public void inject(Object object)
    {
        mActivityGraph.inject(object);
    }

    public ObjectGraph getObjectGraph()
    {
        return mActivityGraph;
    }

    protected Object[] geActivitytModules()
    {
        return new Object[]{
                new ActivityScopeModule(this),
                // new AnotherActivityScopedModule(),
        };
    }
}
