package cc.seedland.inf.corework.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/02/07
 * 描述：
 * </pre>
 */

public abstract class BasePresenter<T extends IBaseView> {

    protected Reference<T> view;

    public BasePresenter(T view) {
        this.view = new WeakReference<T>(view);
    }

    /**
     * 初始化业务逻辑
     */
    public abstract void init();

    /**
     * 清理资源依赖
     * 默认实现，将Presenter与View解除绑定
     */
    public void destory() {
        if(view != null) {
            view.clear();
            view = null;
        }
    }

    /**
     * 获取View实例
     * @return
     */
    protected T getView() {
        if(view != null) {
            return view.get();
        }
        return null;
    }
}
