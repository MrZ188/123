package com.shanghaigm.dms.view.fragment.ck;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shanghaigm.dms.R;
import com.shanghaigm.dms.model.entity.mm.OrderDetailInfoAllocation;
import com.shanghaigm.dms.view.activity.ck.OrderAddActivity;
import com.shanghaigm.dms.view.fragment.BaseFragment;
import com.shanghaigm.dms.view.widget.AllocationTable;
import com.shanghaigm.dms.view.widget.AllocationUnDefaultChoosePopupWindow;
import com.shanghaigm.dms.view.widget.CustomAllocationTable;

import java.util.ArrayList;

//动态添加
public class OrderAddAllocation2Fragment extends BaseFragment {
    private static String TAG = "OrderAddAllocation2";
    private ArrayList<Button> btns = new ArrayList<>();
    private ArrayList<AllocationUnDefaultChoosePopupWindow> popWindows = new ArrayList<>();
    private ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
    private ArrayList<ArrayList<OrderDetailInfoAllocation>> allAssemblyList;
    //储存实时改变的list和原始的list
    private ArrayList<ArrayList<OrderDetailInfoAllocation>> saveLists = new ArrayList<>();
    private ArrayList<AllocationTable> tables = new ArrayList<>();
    private ArrayList<String> names = null;
    private LinearLayout ll;
    private Button btnAdd;
    private CustomAllocationTable customAllocationTable;
    public static OrderAddAllocation2Fragment fragment = null;
    private ArrayList<OrderDetailInfoAllocation> customerAllocationList;
    private Handler allocationHandler = new Handler();

    public OrderAddAllocation2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_add_allocation2, container, false);
        initView(v);
        initData();
        initHandler();
        return v;
    }

    private void initHandler() {

    }

    private void initView(View v) {
        ll = (LinearLayout) v.findViewById(R.id.fragment_ll);
    }

    public static OrderAddAllocation2Fragment getInstance() {
        if (fragment == null) {
            fragment = new OrderAddAllocation2Fragment();
        }
        return fragment;
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示
        } else {// 重新显示到最前端中
            Toast.makeText(getActivity(), "显示配置", Toast.LENGTH_SHORT).show();
            initData();
        }
    }

    private void initData() {
        ll.removeAllViews();
        names = ((OrderAddActivity) getActivity()).getAssemblyNames();
        if (names.size() > 0) {
            for (String name : names) {
                if (((OrderAddActivity) getActivity()).getAssemblyList() != null) {
                    allAssemblyList = ((OrderAddActivity) getActivity()).getAssemblyList();
                    if (allAssemblyList.size() > 0) {
                        for (int i = 0; i < allAssemblyList.size(); i++) {
                            if (allAssemblyList.get(i).get(0).getAssemblyName().equals(name)) {
                                //保存lists
                                saveLists.add(allAssemblyList.get(i));
                                //建button
                                String btnText = "";
//                if (i < names.size()) {
                                btnText = name;
//                }/
                                //button名
                                Button btn = new Button(getActivity());
                                btn.setText(btnText);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(0, getPixelsFromDp(-5), 0, getPixelsFromDp(-5));
                                params.gravity = Gravity.CENTER_HORIZONTAL;
                                btn.setLayoutParams(params);
                                ll.addView(btn);
                                btns.add(btn);
                                //建linearlayout
                                LinearLayout linearLayout = new LinearLayout(getActivity());
//                linearLayout.
                                LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
                                linearLayout.setLayoutParams(llParams);
                                //建表
                                AllocationTable table = new AllocationTable(name, getActivity(), allAssemblyList.get(i), btn, i, ((OrderAddActivity) getActivity()).getSingleAllocationList());
                                LinearLayout.LayoutParams tableParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                table.setLayoutParams(tableParams);
                                linearLayout.addView(table);
                                tables.add(table);
                                linearLayouts.add(linearLayout);
                                ll.addView(linearLayout);
                            }
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), "请选择车型", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getActivity(), getResources().getText(R.string.choose_model), Toast.LENGTH_SHORT).show();
        }
        //新建linearlayout套button
        LinearLayout btnlinearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams llParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llParams1.setMargins(0, getPixelsFromDp(-5), 0, getPixelsFromDp(-5));
        btnlinearLayout.setLayoutParams(llParams1);
        btnlinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button btn = new Button(getActivity());
        btn.setText("自定");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(0, 0, getPixelsFromDp(-20), 0);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        btn.setLayoutParams(params);
//        ll.addView(btn);
        btns.add(btn);
        btnlinearLayout.addView(btn);

        btnAdd = new Button(getActivity());
        btnAdd.setText("+");
        LinearLayout.LayoutParams paramsAdd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(getPixelsFromDp(-5), 0, 0, 0);
        paramsAdd.gravity = Gravity.CENTER_HORIZONTAL;
        btnAdd.setLayoutParams(paramsAdd);

        btnlinearLayout.addView(btnAdd);
        ll.addView(btnlinearLayout);

        //建linearlayout
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        linearLayout.setLayoutParams(llParams);
        //建表
        customerAllocationList = ((OrderAddActivity) getActivity()).getCustomAddList();
        Log.i(TAG, "initData: " + customerAllocationList);

        customAllocationTable = new CustomAllocationTable(getActivity(), customerAllocationList, ((OrderAddActivity) getActivity()).getAssemblyNames());
        LinearLayout.LayoutParams tableParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        customAllocationTable.setLayoutParams(tableParams);
        linearLayout.addView(customAllocationTable);
        linearLayouts.add(linearLayout);
        ll.addView(linearLayout);
        for (LinearLayout l : linearLayouts) {
            l.setVisibility(View.GONE);
        }
        setUpView();
    }

    private void setUpView() {
        allGone();
        for (int i = 0; i < btns.size(); i++) {
            final int finalI = i;
            btns.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (linearLayouts.get(finalI).getVisibility() == View.GONE) {
                        linearLayouts.get(finalI).setVisibility(View.VISIBLE);
                        for (int j = 0; j < linearLayouts.size(); j++) {
                            if (j != finalI) {
                                linearLayouts.get(j).setVisibility(View.GONE);
                            }
                        }
                    } else if (linearLayouts.get(finalI).getVisibility() == View.VISIBLE) {
                        linearLayouts.get(finalI).setVisibility(View.GONE);
                    }
                }
            });

        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAllocationTable.AddItem();
            }
        });
    }

    private void allGone() {
        //令linearlayout全部消失
        for (LinearLayout l : linearLayouts) {
            l.setVisibility(View.GONE);
        }
    }

    //把dp转化为px
    private int getPixelsFromDp(int size) {
        //DisplayMetrics：显示度量
        DisplayMetrics metrics = new DisplayMetrics();
        //度量屏幕
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //dp*dpi/160    dpi/160:是dp长度和像素px的比值
        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }
    //接口回调
//    public interface CallAllcocationInfoBack {
//        void getAllocationInfo(ArrayList<ArrayList<OrderDetailInfoAllocation>> saveData);
//    }
//
//    public void getAllocationInfo(CallAllcocationInfoBack call) {
//        call.getAllocationInfo(saveLists);
//    }
}
