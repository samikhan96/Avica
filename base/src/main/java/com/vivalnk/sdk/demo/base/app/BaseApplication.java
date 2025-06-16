package com.vivalnk.sdk.demo.base.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import com.vivalnk.sdk.common.utils.FileUtils;
import com.vivalnk.sdk.demo.base.common.ActivityStackManager;
import xcrash.XCrash.InitParameters;

/**
 * Created by JakeMo on 18-4-25.
 */
public class BaseApplication extends Application {
  private static BaseApplication base;

  @Override
  public void onCreate() {
    super.onCreate();
    base = this;
    InitParameters parameters = new InitParameters();
    parameters.setLogDir(this.getExternalFilesDir(null) + "/crash/");
    xcrash.XCrash.init(this, parameters);
    ActivityStackManager.getInstance().init(this);
  }

  public static BaseApplication getApplication() {
    return base;
  }

  public static Context getAppContext() {
    return base.getApplicationContext();
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
  }

  @Override
  public void onTrimMemory(int level) {
    super.onTrimMemory(level);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }
}
