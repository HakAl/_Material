package com.jacmobile._material.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.jacmobile._material.activities.ABaseActivity;

/**
 * Base fragment which performs injection using the activity-scoped object graph
 */
public abstract class ABaseFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assume that it lives within a BaseActivity host
        ((ABaseActivity)getActivity()).inject(this);
    }

    protected <T> T getView(int id) {
        return (T) getView().findViewById(id);
    }
}
