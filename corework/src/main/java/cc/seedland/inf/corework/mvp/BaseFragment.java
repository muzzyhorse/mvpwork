package cc.seedland.inf.corework.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/02/08
 * 描述：
 * </pre>
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    protected P presenter;

    private BaseActivity hostActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity) {
            hostActivity = (BaseActivity)context;
        }
        presenter = createPresenter();
    }

    @Override
    public void showLoading() {
        if(hostActivity != null) {
            hostActivity.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if(hostActivity != null) {
            hostActivity.hideLoading();
        }
    }

    @Override
    public void showToast(String message) {
        if(hostActivity != null) {
            hostActivity.showToast(message);
        }
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract P createPresenter();

    protected void initViews(View view) {

    }
}
