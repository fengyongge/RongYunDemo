package com.zzti.fengyongge.rongyundemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIMClientWrapper;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
/**
 * @author fengyongge
 * @date 2017/1/22 0022
 * @description
 */
public class BaseActivity extends AppCompatActivity implements
        RongIM.UserInfoProvider,RongIMClient.OnReceiveMessageListener,
        RongIM.GroupInfoProvider {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        RongIM.setUserInfoProvider(this, true);//设置用户信息提供者。
        RongIM.setGroupInfoProvider(this, true);//设置群组信息提供者。

        RongIM.getInstance().getRongIMClient();
        RongIMClientWrapper.setOnReceiveMessageListener(this);//设置消息接收监听器。
//      RongIM.getInstance().setSendMessageListener(this);//设置发出消息接收监听器.

        // 扩展功能自定义
        InputProvider.ExtendProvider[] provider = {
                new ImageInputProvider(RongContext.getInstance()),// 图片
                new CameraInputProvider(RongContext.getInstance()),// 相机
                // new LocationInputProvider(RongContext.getInstance()),//地理位置
        };
        RongIM.getInstance();
        RongIM.resetInputExtensionProvider(
                Conversation.ConversationType.PRIVATE, provider);
        RongIM.getInstance().getRongIMClient().setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {

            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                // TODO Auto-generated method stub
                switch (connectionStatus) {
                    case CONNECTED:// 连接成功。
                        break;
                    case DISCONNECTED:// 断开连接。

                        break;
                    case CONNECTING:// 连接中。
                        break;
                    case NETWORK_UNAVAILABLE:// 网络不可用。
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT:// 用户账户在其他设备登录，本机会被踢掉线
//                        StringUtils.showToast(BaseActivity.this,"该账户在其他设备登录，请重新登录！");
//                        Intent intent1 = new Intent(BaseActivity.this, LoginActivity.class);
//                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
            }
        });

        RongIM.getInstance().setOnReceiveUnreadCountChangedListener(
                new RongIM.OnReceiveUnreadCountChangedListener() {

                    @Override
                    public void onMessageIncreased(int noReadCont) {
                        // TODO Auto-generated method stub
                        Log.i("fyg","noReadCont:"+noReadCont);
                          //未读消息条数，可以通过发广播
//                        sendBroadcast(BroadcastDefine.createIntent(BroadcastDefine.RED_NUM,noReadCont));
                    }
                }, Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP);

    }


    @Override
    public Group getGroupInfo(String s) {


        return null;
    }

    @Override
    public UserInfo getUserInfo(String s) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(s);
//        userId = "USER_1_11249";
        if (m.matches()) {
            if (s.contains("USER_")) {

            }

        } else {
            //一般用户
            return new UserInfo(s, "游客", Uri.parse("http://wx.qlogo.cn/mmopen/ED2FDnZ2pt6HqE9u1gbkDjSN2uFTAibuyko7UkWdicZI6Wk58Tq4dpMdzRBBLLdC24s03D8lL1gJWTAicQc28OicQNZYM3j1nj2D/0"));
            }


        return null;
    }

    @Override
    public boolean onReceived(Message message, int i) {
        return false;
    }
}
