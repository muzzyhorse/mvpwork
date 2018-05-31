package cc.seedland.inf.corework.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限代理
 * <p>
 *     一般用在Activity或Fragment中需要权限验证的情景
 * </p>
 * Created by Ryan.Xu on 2017/8/17.
 */

public class PermissionAgent {

    private int requestCode;
    private WeakReference<Activity> activity;
    private WeakReference<Fragment> fragment;
    private OnPermissionCallback callback;

    public PermissionAgent(Activity activity, int requestCode) {
        this.requestCode = requestCode;
        this.activity = new WeakReference<>(activity);
    }

    public PermissionAgent(Fragment fragment, int requestCode) {
        this.requestCode = requestCode;
        this.fragment = new WeakReference<>(fragment);
    }

    public void setCallback(OnPermissionCallback callback) {
        this.callback = callback;
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission(String[] permissions) {
        if (checkPermissions(permissions)) {
            if(callback != null) {
                callback.permissionSuccess(requestCode);
            }
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            if(activity != null && activity.get() != null) {
                ActivityCompat.requestPermissions(activity.get(), needPermissions.toArray(new String[needPermissions.size()]), requestCode);
            }else if(fragment != null && fragment.get() != null) {
                fragment.get().requestPermissions(permissions, requestCode);
            }

        }
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    public void verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                if(callback != null) {
                    callback.permissionFail(requestCode);
                }
                return;
            }
        }
        if(callback != null) {
            callback.permissionSuccess(requestCode);
        }
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        Context context = null;
        if(activity != null && activity.get() != null) {
            context = activity.get();
        }else if(fragment != null && fragment.get() != null) {
            context = fragment.get().getContext();
        }
        if(context != null) {
            for (String permission : permissions) {

                if (ContextCompat.checkSelfPermission(context, permission) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private List<String> getDeniedPermissions(String[] permissions) {
        Activity activity = getActivity();
        List<String> needRequestPermissionList = new ArrayList<>();
        if(activity != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) !=
                        PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    needRequestPermissionList.add(permission);
                }
            }
        }

        return needRequestPermissionList;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private Activity getActivity() {
        Activity result = null;
        if(activity != null && activity.get() != null) {
            result = activity.get();
        }else if(fragment != null && fragment.get() != null) {
            result = fragment.get().getActivity();
        }
        return result;
    }
}
