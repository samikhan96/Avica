package com.example.myapplication.AvicaPatient.VivaLink;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.vivalnk.sdk.common.utils.PermissionHelper;
import com.vivalnk.sdk.demo.base.app.BaseToolbarActivity;
import com.vivalnk.sdk.demo.base.app.Layout;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class WelcomeActivity extends AppCompatActivity {

  public static String[] permissions;
  ImageView bt_continue;

  static {
    permissions = new String[]{
            permission.MANAGE_EXTERNAL_STORAGE
    };

    if (Build.VERSION.SDK_INT < 31) {
      //location permission on pre Android 12
      permissions = ArrayUtils.add(permissions, Manifest.permission.ACCESS_COARSE_LOCATION);
      permissions = ArrayUtils.add(permissions, Manifest.permission.ACCESS_FINE_LOCATION);
      // background location permission on Android 10
      // 因为包含了后台定位权限，所以请不要申请和定位无关的权限，因为在 Android 11 上面，后台定位权限不能和其他非定位的权限一起申请
      // 否则会出现只申请了后台定位权限，其他权限会被回绝掉的情况，因为在 Android 11 上面，后台定位权限是要跳 Activity，并非弹 Dialog
      // 另外如果你的应用没有后台定位的需求，请不要一同申请 Permission.ACCESS_BACKGROUND_LOCATION 权限
      // permission.ACCESS_BACKGROUND_LOCATION
    }

    if (Build.VERSION.SDK_INT >= 31) {
      //bluetooth permission on Android 12
      permissions = ArrayUtils.add(permissions, Manifest.permission.BLUETOOTH_CONNECT);
      permissions = ArrayUtils.add(permissions, Manifest.permission.BLUETOOTH_SCAN);
      permissions = ArrayUtils.add(permissions, permission.BLUETOOTH_ADVERTISE);
    }

    if (Build.VERSION.SDK_INT >= 33) {
      //NOTIFICATIONS permission on Android 12
      permissions = ArrayUtils.add(permissions, permission.POST_NOTIFICATIONS);
    }

  }
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
    bt_continue=findViewById(R.id.bt_continue);
    bt_continue.setOnClickListener(v -> {
      checkPermission();
    });

  }

  //request location and write permissions at rum time
  private void checkPermission() {
    XXPermissions.with(this)
            .permission(permissions)
            .request(new OnPermissionCallback() {

              @Override
              public void onGranted(List<String> permissions, boolean all) {
                if (all) {
//              ExceptionHandlerUtils.Companion.getInstances().initCrash(getApplicationContext());
                  Intent intent=new Intent(WelcomeActivity.this, ConfigActivity.class);
                  startActivity(intent);
//              ConstantTest.BASELOGPATH= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"testLog"+File.separator;
//              File file=new File(ConstantTest.BASELOGPATH);
//              if(!file.exists()){
//                file.mkdirs();
//              }
                  finish();
                  return;
                }

                if (Build.VERSION.SDK_INT < 29) {
                  if (!PermissionHelper.hasPermission(WelcomeActivity.this, permission.ACCESS_COARSE_LOCATION)) {
                    AppUtils.Toast("You must grant the location permission under Android 10!");
                    finish();
                    return;
                  }
                }

                // 29 <= api < 31
                if (Build.VERSION.SDK_INT < 31) {
                  if (
                          !PermissionHelper.hasPermission(WelcomeActivity.this, permission.ACCESS_COARSE_LOCATION)
                                  || !PermissionHelper.hasPermission(WelcomeActivity.this, permission.ACCESS_FINE_LOCATION)
                  ) {
                    AppUtils.Toast("You must grant the location permissions under Android 12!");
                    finish();
                    return;
                  }
                }

                //api >= 31
                if (Build.VERSION.SDK_INT >= 31) {
                  if (
                          !PermissionHelper.hasPermission(WelcomeActivity.this, permission.ACCESS_FINE_LOCATION)
                                  || !PermissionHelper.hasPermission(WelcomeActivity.this, permission.BLUETOOTH_CONNECT)
                                  || !PermissionHelper.hasPermission(WelcomeActivity.this, permission.BLUETOOTH_SCAN)
                  ) {
                    AppUtils.Toast("You must grant the bluetooth and location permissions on Android 12!");
                    finish();
                    return;
                  }
                }

                Intent intent=new Intent(WelcomeActivity.this, ConfigActivity.class);
                startActivity(intent);
                finish();

              }


              @Override
              public void onDenied(List<String> permissions, boolean never) {
                if (never) {
                  AppUtils.Toast("被永久拒绝授权，请手动授予App相关权限");
                  // 如果是被永久拒绝就跳转到应用权限系统设置页面
                  XXPermissions.startPermissionActivity(WelcomeActivity.this, permissions);
                } else {
                  AppUtils.Toast("App相关权限授权失败");
                }
              }
            });

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
