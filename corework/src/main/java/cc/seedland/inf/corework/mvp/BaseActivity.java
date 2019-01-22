package cc.seedland.inf.corework.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/02/07
 * 描述：
 * </pre>
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        presenter = createPresenter();
        initViews();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showLoading() {
        showToast("show loading");
    }

    @Override
    public void hideLoading() {
        showToast("hide loading");
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 布局资源ID，{@link BaseActivity}BaseActivity通过{@link #setContentView(int)}方法加载
     * @return 界面需要加载的布局资源ID
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 创建界面的Presenter实例
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 初始化视图
     */
    protected void initViews() {

    }
}
