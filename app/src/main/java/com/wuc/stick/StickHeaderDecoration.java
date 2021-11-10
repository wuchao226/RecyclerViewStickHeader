package com.wuc.stick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author : wuchao5
 * @date : 2021/11/9 16:56
 * @desciption : 自定义装饰器（实现分组+吸顶效果）
 */
public class StickHeaderDecoration extends RecyclerView.ItemDecoration {
  /**
   * 头部的高
   */
  private final int mItemHeaderHeight;
  private int mTextPaddingLeft;
  /**
   * 绘制头部和分割线
   */
  private Paint mItemHeaderPaint;
  private Paint mTextPaint;
  private Paint mLinePaint;

  private Rect mTextRect;

  public StickHeaderDecoration(Context context) {
    mItemHeaderHeight = dp2px(context, 60);
    mTextPaddingLeft = dp2px(context, 10);

    mItemHeaderPaint = new Paint();
    mItemHeaderPaint.setAntiAlias(true);
    mItemHeaderPaint.setColor(Color.RED);

    mTextPaint = new Paint();
    mTextPaint.setAntiAlias(true);
    mTextPaint.setTextSize(50);
    mTextPaint.setColor(Color.WHITE);

    mLinePaint = new Paint();
    mLinePaint.setAntiAlias(true);
    mLinePaint.setColor(Color.GRAY);

    mTextRect = new Rect();
  }

  /**
   * 绘制Item的分割线和组头
   */
  @Override
  public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);
    if (parent.getAdapter() instanceof StickAdapter) {
      StickAdapter adapter = (StickAdapter) parent.getAdapter();
      int left = parent.getPaddingLeft();
      int right = parent.getWidth() - parent.getPaddingRight();
      // 获取可见范围内Item的总数
      int count = adapter.getItemCount();
      for (int i = 0; i < count; i++) {
        // 获取对应i的View
        View view = parent.getChildAt(i);
        if (view == null) {
          return;
        }
        // 获取View的布局位置
        int position = parent.getChildLayoutPosition(view);
        // 是否是头部
        boolean isItemHeader = adapter.isItemHeader(position);
        int topDistance = view.getTop() - mItemHeaderHeight - parent.getPaddingTop();
        if (isItemHeader && topDistance >= 0) {
          // 在当前 view 上面预留的 mItemHeaderHeight 间距内绘制 header
          c.drawRect(left, view.getTop() - mItemHeaderHeight, right, view.getTop(), mItemHeaderPaint);
          String groupName = adapter.getGroupName(position);
          mTextPaint.getTextBounds(groupName, 0, groupName.length(), mTextRect);
          float y = view.getTop() - (mItemHeaderHeight >> 1) + (mTextRect.height() >> 1);
          c.drawText(groupName, left + mTextPaddingLeft, y, mTextPaint);
        } else if (topDistance >= 0) {
          c.drawRect(left, view.getTop() - 1, right, view.getTop(), mLinePaint);
        }
      }
    }
  }

  /**
   * 绘制Item的顶部布局（吸顶效果）
   */
  @Override
  public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.onDrawOver(c, parent, state);
    if (parent.getAdapter() instanceof StickAdapter) {
      StickAdapter adapter = (StickAdapter) parent.getAdapter();
      LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
      if (layoutManager == null) {
        return;
      }
      // 返回可见区域内的第一个 item 的 position
      int position = layoutManager.findFirstVisibleItemPosition();

      RecyclerView.ViewHolder viewHolderForAdapterPosition = parent.findViewHolderForAdapterPosition(position);
      if (viewHolderForAdapterPosition == null) {
        return;
      }
      // 获取对应 position 的 View
      View itemView = viewHolderForAdapterPosition.itemView;
      int left = parent.getPaddingLeft();
      int right = parent.getWidth() - parent.getPaddingRight();
      int top = parent.getPaddingTop();
      // 当第二个是组的头部的时候
      boolean isItemHeader = adapter.isItemHeader(position + 1);
      if (isItemHeader) {
        int bottom = Math.min(mItemHeaderHeight, itemView.getBottom() - parent.getPaddingTop());
        c.drawRect(left, top, right, top + bottom, mItemHeaderPaint);
        String groupName = adapter.getGroupName(position);
        mTextPaint.getTextBounds(groupName, 0, groupName.length(), mTextRect);
        // 绘制文字的高度不能超出区域
        c.clipRect(left, top, right, top + bottom);

        int y = top + bottom - (mItemHeaderHeight >> 1) + (mTextRect.height() >> 1);
        c.drawText(groupName, left + mTextPaddingLeft, y, mTextPaint);
      } else {
        c.drawRect(left, top, right, top + mItemHeaderHeight, mItemHeaderPaint);
        String groupName = adapter.getGroupName(position);
        mTextPaint.getTextBounds(groupName, 0, groupName.length(), mTextRect);
        int y = top + (mItemHeaderHeight >> 1) + (mTextRect.height() >> 1);
        c.drawText(groupName, left + mTextPaddingLeft, y, mTextPaint);
      }
    }
  }

  /**
   * 设置Item的间距
   */
  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    if (parent.getAdapter() instanceof StickAdapter) {
      StickAdapter adapter = (StickAdapter) parent.getAdapter();
      int position = parent.getChildLayoutPosition(view);
      // 判断 itemView 是头部
      if (adapter.isItemHeader(position)) {
        // 如果是头部，预留更大的地方
        outRect.set(0, mItemHeaderHeight, 0, 0);
      } else {
        outRect.set(0, 1, 0, 0);
      }
    }
  }

  /**
   * dp转换成px
   */
  private int dp2px(Context context, float dpValue) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }
}