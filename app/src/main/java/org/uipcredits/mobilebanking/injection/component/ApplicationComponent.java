package org.uipcredits.mobilebanking.injection.component;

import android.app.Application;
import android.content.Context;

import org.uipcredits.mobilebanking.api.BaseApiManager;
import org.uipcredits.mobilebanking.api.DataManager;
import org.uipcredits.mobilebanking.api.local.DatabaseHelper;
import org.uipcredits.mobilebanking.api.local.PreferencesHelper;
import org.uipcredits.mobilebanking.injection.ApplicationContext;
import org.uipcredits.mobilebanking.injection.module.ApplicationModule;


import org.uipcredits.mobilebanking.api.BaseApiManager;
import org.uipcredits.mobilebanking.api.DataManager;
import org.uipcredits.mobilebanking.api.local.DatabaseHelper;
import org.uipcredits.mobilebanking.api.local.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author ishan
 * @since 08/07/16
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();
    DataManager dataManager();
    PreferencesHelper prefManager();
    BaseApiManager baseApiManager();
    DatabaseHelper databaseHelper();

}
