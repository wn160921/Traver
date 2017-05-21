package com.example.traver.util;

import android.os.Handler;
import android.util.Log;

import com.example.traver.fuping_fragment.NFJingdian;
import com.example.traver.shujubiao.Banben;
import com.example.traver.shujubiao.Lunbotu;
import com.example.traver.shujubiao.yonghu;
import com.example.traver.shuo_shuo.Shuo;

import org.litepal.crud.DataSupport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28/028.
 */

public class DatabaseUtil {
    public static void genxinlunbo(final Handler handler) {
        Log.d("ss", "statrt");
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;

                try {
                    final int[] dangqianban = new int[1];
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF("banben");
                    DataInputStream ima = new DataInputStream(s.getInputStream());
                    dangqianban[0] = ima.readInt();
                    Log.d("Ban", String.valueOf(dangqianban[0]));
                    List<Banben> banbens = DataSupport.findAll(Banben.class);
                    Log.d("ben di", String.valueOf(banbens.get(0).getBanbenhao()));
                    if (banbens.get(0).getBanbenhao() == dangqianban[0]) {
                        flag = false;
                        handler.sendEmptyMessage(1);
                    }
                    if (flag) {
                        DataSupport.deleteAll(Lunbotu.class);
                        s = new Socket("119.23.34.226", 30000);
                        dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF("getlunbotu");
                    }
                    int i = 0;
                    while (flag) {
                        ima = new DataInputStream(s.getInputStream());
                        int size = ima.readInt();
                        Log.d("AA", String.valueOf(size));
                        byte[] bytes = new byte[size];
                        int len = 0;
                        while (len < size) {
                            len += ima.read(bytes, len, size - len);
                        }
                        Lunbotu lunbotu = new Lunbotu();
                        lunbotu.setId(i);
                        lunbotu.setImage(bytes);
                        lunbotu.save();
                        //lunbotu.saveOrUpdateAsync("id=?",String.valueOf(i));
                        i++;
                        Log.d("AA", "jie shou");
                        if (i >= 3) {
                            List<Banben> Banbens = DataSupport.findAll(Banben.class);

                            Banbens.get(0).setBanbenhao(dangqianban[0]);
                            Banbens.get(0).save();
                            Log.d("ben di2", String.valueOf(Banbens.get(0).getBanbenhao()));
                            handler.sendEmptyMessage(1);
                            break;
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void zhuceD(final Handler handler, final String aaa, final byte[] pic) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    try {
                        Socket s = new Socket("119.23.34.226", 30000);
                        DataOutputStream printStream = new DataOutputStream(s.getOutputStream());
                        printStream.writeUTF(aaa);
                        printStream.writeInt(pic.length);
                        printStream.write(pic);
                        DataInputStream ima = new DataInputStream(s.getInputStream());
                        int a = ima.readInt();
                        if (a == 1) {
                            handler.sendEmptyMessage(1);
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void denglu(final Handler handler, final String aaa) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    try {
                        Socket s = new Socket("119.23.34.226", 30000);
                        DataOutputStream printStream = new DataOutputStream(s.getOutputStream());
                        printStream.writeUTF(aaa);
                        DataInputStream ima = new DataInputStream(s.getInputStream());
                        int a = ima.readInt();
                        if (a > 1) {
                            String dizhi = ima.readUTF();
                            String ssd[] = aaa.split("\\|");
                            DataSupport.deleteAll(yonghu.class);
                            yonghu yonghu = new yonghu();
                            yonghu.setYonghuming(ssd[1]);
                            yonghu.setJifen(100);
                            yonghu.setTouxiang(dizhi.replaceAll("\\\\", "/"));
                            yonghu.setNecen(ima.readUTF());
                            yonghu.save();
                            handler.sendEmptyMessage(0);
                        } else {
                            handler.sendEmptyMessage(1);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void tijiaoshuoshuo(final String wenzi, final byte[] p1, final byte[] p2, final byte[] p3, final String leibie) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket s = new Socket("119.23.34.226", 30000);
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        int shuliang = 0;
                        if (p1 != null) {
                            shuliang++;
                        }
                        if (p2 != null) {
                            shuliang++;
                        }
                        if (p3 != null) {
                            shuliang++;
                        }
                        dos.writeUTF("fashuoshuo" + "|" + getName() + "|" + getTime() + "|" + wenzi + "|" + String.valueOf(shuliang)
                                + "|" + getNecen() + "|" + leibie);
                        dos.writeUTF(DataSupport.findAll(yonghu.class).get(0).getTouxiang());
                        if (p1 != null) {
                            dos.writeInt(p1.length);
                            dos.write(p1);
                        }
                        if (p2 != null) {
                            dos.writeInt(p2.length);
                            dos.write(p2);
                        }
                        if (p3 != null) {
                            dos.writeInt(p3.length);
                            dos.write(p3);
                        }
                        break;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void getShuo(final Handler handler, final String minglin, final byte[] p, final String leibie) {
        Log.d("ss", "statrt");
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF(minglin + "|" + leibie);
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    String name;
                    String fanxie = "\\\\";
                    for (int i = 0; ; i++) {
                        if ((name = dis.readUTF()).equals("wenziwanbi")) {
                            handler.sendEmptyMessage(1);//初次加载
                            break;
                        }
                        Log.d("ShuoN", name);
                        Shuo shuo = new Shuo(name);
                        shuo.setNecen(dis.readUTF());
                        shuo.setTouxiangdizhi(dis.readUTF());
                        shuo.setZanren(dis.readUTF());
                        shuo.setCairen(dis.readUTF());
                        int xuli = dis.readInt();
                        shuo.seeXulie(xuli);
                        shuo.setZanshu(dis.readInt());
                        shuo.setZanren(dis.readUTF());
                        shuo.setCaishu(dis.readInt());
                        shuo.setCairen(dis.readUTF());
                        shuo.setTime(dis.readUTF());
                        shuo.setNeirong(dis.readUTF());
                        shuo.setFenlei(dis.readUTF());
                        //shuo.setPinglun(dis.readUTF());
                        int imagecount = dis.readInt();
                        shuo.setShuliag(imagecount);
                        if (imagecount == 0) {

                        } else if (imagecount == 1) {
                            shuo.setimage1path(dis.readUTF().replaceAll(fanxie, "/"));
                        } else if (imagecount == 2) {
                            shuo.setimage1path(dis.readUTF().replaceAll(fanxie, "/"));
                            shuo.setImage2(dis.readUTF().replaceAll(fanxie, "/"));
                        } else if (imagecount == 3) {
                            shuo.setimage1path(dis.readUTF().replaceAll(fanxie, "/"));
                            shuo.setImage2(dis.readUTF().replaceAll(fanxie, "/"));
                            shuo.setImage3(dis.readUTF().replaceAll(fanxie, "/"));
                        }
                        shuo.save();

                        Log.d("Shuo", "jieshouS");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static String getName() {
        List<yonghu> yonghuList = DataSupport.findAll(yonghu.class);
        Log.d("yonghusize", String.valueOf(yonghuList.size()));
        if (yonghuList.size() > 0) {
            return yonghuList.get(0).getYonghuming();
        }
        return "I don't know";
    }

    public static String getNecen() {
        List<yonghu> yonghuList = DataSupport.findAll(yonghu.class);
        Log.d("yonghusize", String.valueOf(yonghuList.size()));
        if (yonghuList.size() > 0) {
            return yonghuList.get(0).getNecen();
        }
        return "I don't know";
    }

    public static String getTouxiang() {
        List<yonghu> yonghuList = DataSupport.findAll(yonghu.class);
        Log.d("yonghusize", String.valueOf(yonghuList.size()));
        if (yonghuList.size() > 0) {
            return yonghuList.get(0).getTouxiang();
        }
        return "Idonknow";
    }

    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        return str;
    }

    public static void getNFjindianList(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    try {
                        DataSupport.deleteAll(NFJingdian.class);
                        Socket s = new Socket("119.23.34.226", 30000);
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF("getNFjingdian");
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        for (int i = 0; ; i++) {
                            NFJingdian nfJingdian = new NFJingdian();
                            String name = dis.readUTF();
                            if (name.equals("jieshu")) {
                                break;
                            }
                            nfJingdian.setName(name);
                            String weizhi = dis.readUTF();
                            nfJingdian.setWeizhi(weizhi);
                            int size = dis.readInt();
                            int len = 0;
                            byte[] log = new byte[size];
                            while (len < size) {
                                len += dis.read(log, len, size - len);
                            }
                            nfJingdian.setLog(log);
                            nfJingdian.save();
                        }
                        Log.d("NF", "jiejue");
                        handler.sendEmptyMessage(0);//完成
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void getNFJDDetil(final Handler handler, final String chuanname) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    try {
                        Socket s = new Socket("119.23.34.226", 30000);
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF("getNFjdd|" + chuanname);
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        NFJingdian nfJingdian = new NFJingdian();
                        String jieshao = dis.readUTF();
                        nfJingdian.setJieshao(jieshao);
                        String pinlun = dis.readUTF();
                        nfJingdian.setPinglun(pinlun);
                        String uri = dis.readUTF();
                        nfJingdian.setUri(uri);
                        handler.sendEmptyMessage(2);
                        nfJingdian.setPic1(dis.readUTF());
                        nfJingdian.setPic2(dis.readUTF());
                        handler.sendEmptyMessage(2);
                        nfJingdian.setPic3(dis.readUTF());
                        handler.sendEmptyMessage(2);
                        nfJingdian.updateAll("name = ?", chuanname.trim());
                        Log.d("NF", "jiejueD");
                        handler.sendEmptyMessage(1);//完成
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void fashuoshuopinglun(final String ping) {//包括id
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF("gxsping|" + ping);
                    dos.flush();
                    dos.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void gexzan(final String xin) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF("genxingzan|" + xin);
                    dos.flush();
                    dos.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void gexcai(final String xin) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF("genxingcai|" + xin);
                    dos.flush();
                    dos.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static boolean isDenglu() {
        List<yonghu> yonghuList = DataSupport.findAll(yonghu.class);
        if (yonghuList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void setJifen(int jifen) {
        List<yonghu> yonghus = DataSupport.findAll(yonghu.class);
        if (yonghus.size() > 0) {
            final String yhm = yonghus.get(0).getYonghuming();
            final int yuanjifen = yonghus.get(0).getJifen() + jifen;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket s = new Socket("119.23.34.226", 30000);
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF("jiajifen|" + yhm + "|" + String.valueOf(yuanjifen));
                        dos.flush();
                        dos.close();
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

