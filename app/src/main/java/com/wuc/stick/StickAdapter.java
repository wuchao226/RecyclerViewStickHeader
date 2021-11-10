package com.wuc.stick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * @author : wuchao5
 * @date : 2021/11/9 16:47
 * @desciption :
 */
public class StickAdapter extends RecyclerView.Adapter<StickAdapter.StickViewHolder> {

  private Context context;
  private List<Star> starList;

  public StickAdapter(Context context, List<Star> starList) {
    this.context = context;
    this.starList = starList;
  }

  /**
   * 判断position对应的Item是否是组的第一项
   *
   * @param position
   * @return
   */
  public boolean isItemHeader(int position) {
    if (position == 0) {
      return true;
    } else {
      String currentGroupName = getGroupName(position);
      String lastGroupName = getGroupName(position - 1);
      // 判断上一个数据的组别和下一个数据的组别是否一致，如果不一致则是不同组，也就是为第一项（头部）
      return !currentGroupName.equals(lastGroupName);
    }
  }

  public String getGroupName(int position) {
    return starList.get(position).getGroundName();
  }

  @NonNull @Override
  public StickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.rv_item_star, null);
    return new StickViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull StickViewHolder holder, int position) {
    holder.tv.setText(starList.get(position).getName());
  }

  @Override
  public int getItemCount() {
    return starList == null ? 0 : starList.size();
  }

  public static class StickViewHolder extends RecyclerView.ViewHolder {
    private TextView tv;

    public StickViewHolder(@NonNull View itemView) {
      super(itemView);
      tv = itemView.findViewById(R.id.tv_star);
    }
  }
}