package com.example.restaurantmanagement.di;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.restaurantmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions.placeholderOf(R.drawable.ic_baseline_downloading_24)
                .error(R.drawable.ic_baseline_error_outline_24);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    FirebaseFirestore provideFirebaseStore() {
        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    static FirebaseAuth provideAuth(){
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Nullable
    @Provides
    static FirebaseUser provideUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    @Singleton
    @Provides
    static FirebaseStorage provideStorage(){
        return FirebaseStorage.getInstance();
    }


    @Singleton
    @Provides
    static Context context(Application application){
        return application.getApplicationContext();
    }
}
