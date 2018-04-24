package cc.seedland.inf.corework.mvp;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/02/07
 * 描述：MVP架构-基础视图接口，所有视图接口的父类
 * </pre>
 */

public interface IBaseView {

    /**
     * 显示加载对话框
     */
    void showLoading();

    /**
     * 隐藏加载对话框
     */
    void hideLoading();

    /**
     * 显示toast信息
     * @param message
     */
    void showToast(String message);

    /**
     * 显示错误
     * @param code
     * @param message
     */
    void showError(int code, String message);
}
