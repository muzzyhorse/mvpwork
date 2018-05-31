package cc.seedland.inf.seedworks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cc.seedland.inf.corework.helper.PromptHelper;

public class MainActivity extends AppCompatActivity {

//    MaterialProgressDrawable drawable;

    //圈圈颜色,可以是多种颜色
    private int[] COLORS = {
                0xFFFF0000,0xFFFF7F00,0xFFFFFF00,0xFF00FF00
                ,0xFF00FFFF,0xFF0000FF,0xFF8B00FF};

    CircularProgressDrawable drawable;

    PromptHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView t = findViewById(R.id.home_rcv);
        helper = new PromptHelper(t);
        helper.showLoading();

//        ImageView imv = findViewById(R.id.loading);
//
//
//        drawable = new CircularProgressDrawable(this);
//        drawable.setStyle(CircularProgressDrawable.LARGE);
//        drawable.setColorSchemeColors(COLORS);
//        imv.setImageDrawable(drawable);
//        drawable.setArrowEnabled(true);
//        drawable.start();

//        drawable = new MaterialProgressDrawable(this, imv);
//
//        drawable.setBackgroundColor(0xFFFAFAFA);

//        drawable.setColorSchemeColors(colors);
//        //设置圈圈的各种大小
//        drawable.updateSizes(MaterialProgressDrawable.LARGE);
//
//        imv.setImageDrawable(drawable);
//        visable(imv);


    }


    private ValueAnimator valueAnimator;

    boolean start = false;

    boolean visable = false;

    public void visable(View v)
    {
        if(valueAnimator == null)
        {
            valueAnimator = valueAnimator.ofFloat(0f,1f);
            valueAnimator.setDuration(600);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float n = (float) animation.getAnimatedValue();
                    //圈圈的旋转角度
                    drawable.setProgressRotation(n * 0.5f);
                    //圈圈周长，0f-1F
                    drawable.setStartEndTrim(0f, n * 0.8f);
                    //箭头大小，0f-1F
                    drawable.setArrowScale(n);
                    //透明度，0-255
                    drawable.setAlpha((int) (255 * n));
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    visable = true;
                }
            });
        }

        if(!valueAnimator.isRunning())
        {
            if(!visable)
            {
                //是否显示箭头
//                drawable.showArrow(true);
                valueAnimator.start();
            }
            else
            {
                Toast.makeText(this,"看不见吗？",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void start(View v)
    {
        if(visable)
        {
            if (!start)
            {
                drawable.start();
                start = true;
            }
            else
            {
                Toast.makeText(this,"已经在转了",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this,"还没有显示",Toast.LENGTH_SHORT).show();
        }
    }

    public void stop(View v)
    {
        if (start)
        {
            drawable.stop();
            start = false;
            visable = false;
        }
        else
        {
            Toast.makeText(this,"又没有转",Toast.LENGTH_SHORT).show();
        }
    }
}
