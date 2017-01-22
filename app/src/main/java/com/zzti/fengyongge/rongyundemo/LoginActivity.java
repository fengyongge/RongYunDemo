package com.zzti.fengyongge.rongyundemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * @author fengyongge
 * @date 2017/1/22 0022
 * @description
 */
public class LoginActivity extends AppCompatActivity {

    private TextView tv_login;
    private String id="666",name="fsuper";
    private String logo="http://wx.qlogo.cn/mmopen/ED2FDnZ2pt6HqE9u1gbkDjSN2uFTAibuyko7UkWdicZI6Wk58Tq4dpMdzRBBLLdC24s03D8lL1gJWTAicQc28OicQNZYM3j1nj2D/0";
    String token="h1EkCSBSDJEaf36mVfM6pc8qpWurPgIk36L5lQ2Qk6SM0mdsssc6rLtWnrxdDD0JqiqyWCRqJDg=";//后台返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //向融云提交个人信息，并刷新

                connect(token);

                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String userId) {
                        return new UserInfo(id, name, Uri.parse(logo));
                    }
                }, true);
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(id, name, Uri.parse(logo)));

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void initView(){
        tv_login = (TextView) findViewById(R.id.tv_login);
    }

    private void connect(String token) {

        if (LoginActivity.this.getApplicationInfo().packageName.equals(MyApp.getCurProcessName(LoginActivity.this.getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.i("fyg","onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.i("fyg","onSuccess");

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.i("fyg","onError");
                }
            });
        }
    }



}
