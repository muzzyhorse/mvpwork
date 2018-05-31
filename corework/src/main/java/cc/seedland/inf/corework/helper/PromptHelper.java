package cc.seedland.inf.corework.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CircularProgressDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import cc.seedland.inf.R;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/03/18
 * 描述：用于控制提示信息
 * </pre>
 */

public class PromptHelper {

    private ViewGroup container;
    private View showView;
    private View promptView;
    private TextView contentV;
    private ImageView loadingV;
    private CircularProgressDrawable loadingDrawable;

    private static final int[] COLORS = {
            0xFFFF0000,0xFFFF7F00,0xFF00FF00
            ,0xFF00FFFF,0xFF0000FF,0xFF8B00FF};

    public PromptHelper(View root) {
        this.showView = root;
        if(showView != null) {
            container = (ViewGroup) showView.getParent();
            if(container == null) {
                throw new IllegalStateException(root.getClass().getSimpleName() + " doesn't have a parent view");
            }
        }
    }

    public void showLoading() {

        ensurePromptView();
        // 开启加载动画
        if(loadingV.getAnimation() == null) {

            contentV.setVisibility(View.GONE);
            loadingV.setVisibility(View.VISIBLE);

//            loadingV.setImageResource(android.R.drawable.btn_star);
//            loadingV.setImageDrawable(loadingDrawable);
            if(loadingDrawable instanceof Animatable) {
                loadingDrawable.start();
            }else {
                Animation anim = AnimationUtils.loadAnimation(promptView.getContext(), R.anim.loading_animation);
                loadingV.startAnimation(anim);
            }
        }
        // 移除显示内容
        container.removeView(showView);
    }

    public void hideLoading() {

        if(loadingDrawable instanceof Animatable) {
            loadingDrawable.stop();
        }else if(loadingV.getAnimation() != null) {
            loadingV.setImageResource(0);
            loadingV.setVisibility(View.GONE);
        }



        clear();
    }

    public void showDataError(String message) {
        ensurePromptView();

        // 设置内容
        contentV.setText(message);

        // 隐藏动作按钮
        container.removeView(showView);
    }


    public void showDataError(String message, Drawable image) {
        showDataError(message);

        // 设置图片内容
        TextView content = promptView.findViewById(R.id.prompt_txv_content);

        if(image != null) {
            content.setCompoundDrawablesWithIntrinsicBounds(null, image, null, null);
        }

    }

    public void showDataErrorWithAction(String message, View.OnClickListener actionListener) {
        showDataError(message);
        contentV.setOnClickListener(actionListener);
    }

    public void showDataErrorWithAction(String message, Drawable image, View.OnClickListener actionListener) {
        showDataError(message, image);
        contentV.setOnClickListener(actionListener);
    }

    public void clear() {
        if(promptView != null) {
            container.removeView(promptView);
            container.addView(showView);
            container.requestFocus(); // 让控件默认获取焦点
            promptView = null;
        }

    }

    private void ensurePromptView() {
        if(promptView == null) {
            final Context context = container.getContext();
            promptView = LayoutInflater.from(context).inflate(R.layout.layout_prompt, container, false);
            promptView.setLayoutParams(showView.getLayoutParams());
            container.addView(promptView);
            loadingV = promptView.findViewById(R.id.prompt_imv_loading);
            contentV = promptView.findViewById(R.id.prompt_txv_content);

            loadingDrawable = new CircularProgressDrawable(context);
            loadingDrawable.setStyle(CircularProgressDrawable.LARGE);
            loadingDrawable.setColorSchemeColors(COLORS);
            loadingDrawable.setArrowEnabled(true);
            loadingV.setImageDrawable(loadingDrawable);
        }
    }

}
