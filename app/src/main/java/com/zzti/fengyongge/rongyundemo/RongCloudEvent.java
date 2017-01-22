package com.zzti.fengyongge.rongyundemo;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.sea_monster.exception.BaseException;
import com.sea_monster.network.AbstractHttpRequest;
import com.sea_monster.network.ApiCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.OnSendMessageListener;
import io.rong.imkit.RongIM.SentMessageErrorCode;
import io.rong.imkit.RongIMClientWrapper;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;

/**
 *                                         ,s555SB@@&
 *                                      :9H####@@@@@Xi
 *                                     1@@@@@@@@@@@@@@8
 *                                   ,8@@@@@@@@@B@@@@@@8
 *                                  :B@@@@X3hi8Bs;B@@@@@Ah,
 *             ,8i                  r@@@B:     1S ,M@@@@@@#8;
 *            1AB35.i:               X@@8 .   SGhr ,A@@@@@@@@S
 *            1@h31MX8                18Hhh3i .i3r ,A@@@@@@@@@5
 *            ;@&i,58r5                 rGSS:     :B@@@@@@@@@@A
 *             1#i  . 9i                 hX.  .: .5@@@@@@@@@@@1
 *              sG1,  ,G53s.              9#Xi;hS5 3B@@@@@@@B1
 *               .h8h.,A@@@MXSs,           #@H1:    3ssSSX@1
 *               s ,@@@@@@@@@@@@Xhi,       r#@@X1s9M8    .GA981
 *               ,. rS8H#@@@@@@@@@@#HG51;.  .h31i;9@r    .8@@@@BS;i;
 *                .19AXXXAB@@@@@@@@@@@@@@#MHXG893hrX#XGGXM@@@@@@@@@@MS
 *                s@@MM@@@hsX#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&,
 *              :GB@#3G@@Brs ,1GM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@B,
 *            .hM@@@#@@#MX 51  r;iSGAM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@8
 *          :3B@@@@@@@@@@@&9@h :Gs   .;sSXH@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:
 *      s&HA#@@@@@@@@@@@@@@M89A;.8S.       ,r3@@@@@@@@@@@@@@@@@@@@@@@@@@@r
 *   ,13B@@@@@@@@@@@@@@@@@@@5 5B3 ;.         ;@@@@@@@@@@@@@@@@@@@@@@@@@@@i
 *  5#@@#&@@@@@@@@@@@@@@@@@@9  .39:          ;@@@@@@@@@@@@@@@@@@@@@@@@@@@;
 *  9@@@X:MM@@@@@@@@@@@@@@@#;    ;31.         H@@@@@@@@@@@@@@@@@@@@@@@@@@:
 *   SH#@B9.rM@@@@@@@@@@@@@B       :.         3@@@@@@@@@@@@@@@@@@@@@@@@@@5
 *     ,:.   9@@@@@@@@@@@#HB5                 .M@@@@@@@@@@@@@@@@@@@@@@@@@B
 *           ,ssirhSM@&1;i19911i,.             s@@@@@@@@@@@@@@@@@@@@@@@@@@S
 *              ,,,rHAri1h1rh&@#353Sh:          8@@@@@@@@@@@@@@@@@@@@@@@@@#:
 *            .A3hH@#5S553&@@#h   i:i9S          #@@@@@@@@@@@@@@@@@@@@@@@@@A.
 *
 *
 *    又看融云，看你妹啊！
 */


/**
 * 融云SDK事件监听处理。
 * 把事件统一处理，开发者可直接复制到自己的项目中去使用。
 * <p/>
 * 该类包含的监听事件有：
 * 1、消息接收器：OnReceiveMessageListener。
 * 2、发出消息接收器：OnSendMessageListener。
 * 3、用户信息提供者：GetUserInfoProvider。
 * 4、好友信息提供者：GetFriendsProvider。
 * 5、群组信息提供者：GetGroupInfoProvider。
 * 7、连接状态监听器，以获取连接相关状态：ConnectionStatusListener。
 * 8、地理位置提供者：LocationProvider。
 * 9、自定义 push 通知： OnReceivePushMessageListener。
 * 10、会话列表界面操作的监听器：ConversationListBehaviorListener。
 */

public final class RongCloudEvent implements RongIM.ConversationBehaviorListener,
        RongIMClient.ConnectionStatusListener, RongIM.LocationProvider,
        OnSendMessageListener,RongIMClient.OnReceiveMessageListener,
        RongIM.ConversationListBehaviorListener,
        ApiCallback, Handler.Callback {
    private static final String TAG = RongCloudEvent.class.getSimpleName();
    private static RongCloudEvent mRongCloudInstance;
    private Context mContext;

    public static void init(Context context) {

        if (mRongCloudInstance == null) {

            synchronized (RongCloudEvent.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new RongCloudEvent(context);
                }
            }
        }
    }


    private RongCloudEvent(Context context) {
        mContext = context;
        initDefaultListener();
    }

    /**
     * RongIM.init(this) 后直接可注册的Listener。
     */
    private void initDefaultListener() {
//        RongIM.setUserInfoProvider(this, true);//设置用户信息提供者。
//        RongIM.setGroupInfoProvider(this, true);//设置群组信息提供者。
        RongIM.setConversationBehaviorListener(this);//设置会话界面操作的监听器。
        RongIM.getInstance().getRongIMClient();
        RongIMClientWrapper.setOnReceiveMessageListener(this);//设置消息接收监听器。
//      RongIM.getInstance().setSendMessageListener(this);//设置发出消息接收监听器.
//      RongIM.setLocationProvider(this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
//      RongIM.setConversationListBehaviorListener(this);

    }


//    /**
//     * 用户信息的提供者：GetUserInfoProvider 的回调方法，获取用户信息。
//     *
//     * @param userId
//     *            用户 Id。
//     * @return 用户信息，（注：由开发者提供用户信息）。
//     */
//    @Override
//    public UserInfo getUserInfo(final String userId) {
//        return new UserInfo(userId, "游客",Uri.parse(""));
//
//    }
//
//    /**
//     * 群组信息的提供者：GetGroupInfoProvider 的回调方法， 获取群组信息。
//     *
//     * @param groupId
//     *            群组 Id.
//     * @return 群组信息，（注：由开发者提供群组信息）。
//     */
//    @Override
//    public Group getGroupInfo(String groupId) {
//        return new Group(groupId, "游客",Uri.parse(""));
//    }



    /**
     * 会话界面操作的监听器：ConversationBehaviorListener 的回调方法，当点击用户头像后执行。
     *
     * @param context          应用当前上下文。
     * @param conversationType 会话类型。
     * @param user             被点击的用户的信息。
     * @return 返回True不执行后续SDK操作，返回False继续执行SDK操作。
     */
    @Override
    public boolean onUserPortraitClick(Context context, ConversationType conversationType, UserInfo user) {

        if (user != null) {
            if (conversationType.equals(ConversationType.PUBLIC_SERVICE) || conversationType.equals(ConversationType.APP_PUBLIC_SERVICE)) {
                RongIM.getInstance().startPublicServiceProfile(mContext, conversationType, user.getUserId());
            } else {
                String regex=".*[a-zA-Z]+.*";
                Matcher m=Pattern.compile(regex).matcher(user.getUserId());
                if(!m.matches()){

//                    Intent in = new Intent(context, MemberDetailActivity.class);
//                    in.putExtra("USER", user);
//                    in.putExtra("SEARCH_USERID", user.getUserId());
//                    in.putExtra("SEARCH_CONVERSATIONTYPE", conversationType);
//                    context.startActivity(in);
                }
            }
        }
        return false;
    }



    @Override
    public boolean handleMessage(android.os.Message arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onStartLocation(Context arg0, LocationCallback arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onMessageClick(Context context, View arg1, Message message) {
        // TODO Auto-generated method stub
        if (message.getContent() instanceof ImageMessage) {
            ImageMessage imageMessage = (ImageMessage) message.getContent();

            if (imageMessage.getThumUri() != null) {
//            	Uri uri= imageMessage.getLocalUri() == null ? imageMessage.getRemoteUri() : imageMessage.getLocalUri();
//            	PicBean picBean = new PicBean();
//            	ArrayList<PicBean> arrayList = new ArrayList<PicBean>();
//            	picBean.setImg(uri.toString());
//            	picBean.setImg_thumb(uri.toString());
//            	arrayList.add(picBean);
//                Bundle bundle = new Bundle();
//    			bundle.putSerializable("pics", (Serializable)arrayList);
//    			bundle.putInt("position",0);
//    			bundle.putString("save","save");
//    			bundle.putBoolean("is_chat", true);
//                CommonUtils.launchActivity(context, PhotoPreviewActivity.class, bundle);
            }
        }
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context arg0, String arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context arg0, View arg1, Message arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context arg0, ConversationType arg1,
                                           UserInfo arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onConversationClick(Context arg0, View arg1,
                                       UIConversation arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context arg0, View arg1,
                                           UIConversation arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onComplete(AbstractHttpRequest arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFailure(AbstractHttpRequest arg0, BaseException arg1) {
        // TODO Auto-generated method stub


    }



    @Override
    public boolean onConversationPortraitClick(Context arg0,
                                               ConversationType arg1, String arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context arg0,
                                                   ConversationType arg1, String arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Message onSend(Message arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onSent(Message arg0, SentMessageErrorCode arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onReceived(Message arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus){

            case CONNECTED://连接成功。

                break;
            case DISCONNECTED://断开连接。

                break;
            case CONNECTING://连接中。

                break;
            case NETWORK_UNAVAILABLE://网络不可用。

                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉
//                StringUtils.showToast(mContext,"该账户在其他设备登录，请重新登录！");
//                Intent intent1 = new Intent(mContext, LoginActivity.class);
//                mContext.startActivity(intent1);
                break;
        }
    }



}
