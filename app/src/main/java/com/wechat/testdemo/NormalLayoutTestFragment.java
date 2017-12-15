package com.wechat.testdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsjwzh.test.AutoScrollHandler;
import com.lsjwzh.test.FastTextView;
import com.lsjwzh.test.TestSpan;
import com.lsjwzh.test.TestTextView;
import com.lsjwzh.test.Util;

public class NormalLayoutTestFragment extends Fragment {

  private ListView listView;

  private NormalListAdapter adapter;

  private AutoScrollHandler autoScrollHandler;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View viewRoot = inflater.inflate(R.layout.normal_layout_ui, container, false);

    listView = (ListView) viewRoot.findViewById(R.id.test_list);

    adapter = new NormalListAdapter(getActivity());

    listView.setAdapter(adapter);

    autoScrollHandler = new AutoScrollHandler(listView, Util.TEST_LIST_ITEM_COUNT);

    viewRoot.findViewById(R.id.scroll_down_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        TestTextView.TEST_STATS.reset();
        adapter.bindCost = 0;
        autoScrollHandler.startAutoScrollDown(new AutoScrollHandler.Callback() {
          @Override
          public void callback(int fps) {
            Toast.makeText(listView.getContext(), "Average FPS: " + fps, Toast.LENGTH_LONG).show();
            Log.e("drawFps", "TestTextView.avgFps" + fps);
            Log.e("bindCost", "bindCost" + adapter.bindCost);
            Log.e("TestTextViewStats", TestTextView.TEST_STATS.toString());
          }
        });
      }
    });

    viewRoot.findViewById(R.id.scroll_up_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        TestTextView.TEST_STATS.reset();
        adapter.bindCost = 0;
        autoScrollHandler.startAutoScrollUp(new AutoScrollHandler.Callback() {
          @Override
          public void callback(int fps) {
            Toast.makeText(listView.getContext(), "Average FPS: " + fps, Toast.LENGTH_LONG).show();
            Log.e("drawFps", "TestTextView.avgFps" + fps);
            Log.e("bindCost", "bindCost" + adapter.bindCost);
            Log.e("TestTextViewStats", TestTextView.TEST_STATS.toString());

          }
        });
      }
    });
    return viewRoot;
  }

  private static class NormalListAdapter extends TestListAdapter {

    private NormalListAdapter(Context context) {
      super(context);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {

      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.normal_list_item, parent,
            false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textView = (TextView) convertView.findViewById(R.id.normal_text);

        convertView.setTag(viewHolder);
      }

      ViewHolder holder = (ViewHolder) convertView.getTag();
      holder.textView.setMovementMethod(LinkMovementMethod.getInstance());
      holder.textView.setText(TestSpan.getSpanString(position));
      return convertView;
    }

    private class ViewHolder {
      TextView textView;
    }
  }
}
