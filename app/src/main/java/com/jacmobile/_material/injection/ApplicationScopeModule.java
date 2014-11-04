package com.jacmobile._material.injection;

import android.content.Context;

import com.jacmobile._material.BuildConfig;
import com.jacmobile._material.injection.annotations.ForApplication;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Here it provides the dependencies those are used in the whole scope of your MyCoolApp
 */
@Module(
        complete = true,    // Here it enables object graph validation
        library = true,
        addsTo = AndroidAppModule.class, // Important for object graph validation at compile time
        injects = {
                DaggerApplication.class
        }
)
public class ApplicationScopeModule
{
    @Provides
    Picasso providesPicasso(@ForApplication Context context) {
        Picasso picasso = Picasso.with(context);

        // some app-wide common settings
        picasso.setDebugging(BuildConfig.DEBUG);

        return picasso;
    }
}
