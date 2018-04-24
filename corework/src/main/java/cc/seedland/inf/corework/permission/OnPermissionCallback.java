package cc.seedland.inf.corework.permission;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/04/03
 * 描述：请求权限时的回调接口
 * </pre>
 */

public interface OnPermissionCallback {
    /**
     * 请求成功回调
     * @param requestCode
     */
    void permissionSuccess(int requestCode);

    /**
     * 请求失败回调
     * @param requestCode
     */
    void permissionFail(int requestCode);
}
