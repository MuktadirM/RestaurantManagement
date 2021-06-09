package com.example.restaurantmanagement.di;

import android.app.Application;

import com.example.restaurantmanagement.BaseApplication;
import com.example.restaurantmanagement.SessionManager;
import com.example.restaurantmanagement.di.auth.AuthViewModelsModule;
import com.example.restaurantmanagement.di.core.CommonFragmentsBuildersModule;
import com.example.restaurantmanagement.di.core.CommonViewModelsModule;
import com.example.restaurantmanagement.di.domain.DomainCoreModules;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AuthViewModelsModule.class,
        AppModule.class,
        CommonFragmentsBuildersModule.class,
        CommonViewModelsModule.class,
        DomainCoreModules.class,
        ActivityBuildersModule.class,
        ViewModelFactoryModule.class,
})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager();

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
