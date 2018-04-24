package cc.seedland.inf.corework.mvp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Map;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/03/14
 * 描述：
 * </pre>
 */

public abstract class BaseHolder<M> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }

    /**
     * 绑定数据
     * @param bean
     */
    public abstract void bind(M bean);
}
