package org.uip.mobilebanking.injection.component;

import android.app.Application;
import android.content.Context;

import org.uip.mobilebanking.api.BaseApiManager;
import org.uip.mobilebanking.api.DataManager;
import org.uip.mobilebanking.api.local.DatabaseHelper;
import org.uip.mobilebanking.api.local.PreferencesHelper;
import org.uip.mobilebanking.injection.ApplicationContext;
import org.uip.mobilebanking.injection.module.ApplicationModule;


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
