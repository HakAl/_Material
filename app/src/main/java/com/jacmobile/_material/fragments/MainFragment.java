package com.jacmobile._material.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jacmobile._material.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class MainFragment extends ABaseFragment
{
    @Inject
    Picasso picasso;

    public static MainFragment newInstance()
    {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        String imageUrl = "http://cdn1.smosh.com/sites/default/files/legacy.images/smosh-pit/092010/philosoraptor-synonym.jpeg";

        ImageView imageView = getView(R.id.img_poc);
        picasso.load(imageUrl).into(imageView);
    }
}
