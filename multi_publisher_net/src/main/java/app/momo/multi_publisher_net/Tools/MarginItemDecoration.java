package app.momo.multi_publisher_net.Tools;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginItemDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                outRect.top = margin;
                outRect.bottom = margin;
            } else if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                outRect.left = margin;
                outRect.right = margin;
            }
        }
    }
}
