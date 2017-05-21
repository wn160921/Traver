package com.example.traver;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.traver.util.SearchView;
import com.example.traver.zhiyuangongyi.Zhiyuan;
import com.example.traver.zhiyuangongyi.ZhiyuanAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity implements SearchView.SearchViewListener {
    /**
     * 搜索结果列表view
     */
    private RecyclerView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private ZhiyuanAdapter resultAdapter;

    private List<Zhiyuan> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<Zhiyuan> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        SearchActivity.hintSize = hintSize;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        initData();
        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        lvResults = (RecyclerView) findViewById(R.id.main_lv_search_results);
        lvResults.setLayoutManager(new LinearLayoutManager(this));
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getzhiyuan");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|gongyi");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|zhumingjingdian");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|youxi");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|tsjingdian");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|tesechanping");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|shougongzhizhuo");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|nongjiatiyan");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|meishi");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc = new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|jiudian");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    while (true) {
                        String dianming = dis.readUTF();
                        if (dianming.equals("jieshu")) {
                            break;
                        }
                        Zhiyuan zhiyuan = new Zhiyuan();
                        zhiyuan.name = dianming;
                        zhiyuan.jieshaotu = dis.readUTF();
                        zhiyuan.image1 = dis.readUTF();
                        zhiyuan.image2 = dis.readUTF();
                        zhiyuan.image3 = dis.readUTF();
                        zhiyuan.neirong = dis.readUTF();
                        zhiyuan.zanren = dis.readUTF();
                        zhiyuan.zanshu = dis.readInt();
                        zhiyuan.uri = dis.readUTF();
                        dbData.add(zhiyuan);
                    }
                } catch (IOException e) {

                }
            }
        }).start();
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        hintData.add("东湖");
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).name.contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).name);
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).name.contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new ZhiyuanAdapter(resultData, this);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     *
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();


    }

}
