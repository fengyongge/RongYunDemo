package com.zzti.fengyongge.rongyundemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * @author fengyongge
 * @date 2017/1/22 0022
 * @description
 */

public class ConversationActivity extends FragmentActivity {

    private String typeString="";
    private String mTargetId;
    private String mTargetIds;
    private String name;
    private Conversation.ConversationType mConversationType;
    TextView top_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        Intent intent = getIntent();
        getIntentDate(intent);

    }




    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetIds = intent.getData().getQueryParameter("targetIds");
        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));


        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
            @Override
            public Message onSend(Message message) {
                // TODO Auto-generated method stub

                MessageContent mMessageContent =  message.getContent();
                if (mMessageContent instanceof TextMessage) {
                    TextMessage mTextMsg  = (TextMessage)mMessageContent;
                    mTextMsg.setExtra(UUID.randomUUID().toString());
                }
                return message;
            }

            @Override
            public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
                // TODO Auto-generated method stub
                if (message.getSentStatus() == Message.SentStatus.FAILED) {

                    if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_CHATROOM) {//不在聊天室

                    } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION) {//不在讨论组

                    } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_GROUP) {//不在群组

                    } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST) {//你在他的黑名单中

                    }
                }

                if(message.getConversationType().equals(Conversation.ConversationType.PRIVATE)){

                    MessageContent messageContent = message.getContent();

                    if (messageContent instanceof TextMessage) {//文本消息
                        TextMessage textMessage = (TextMessage) messageContent;
                        typeString = "1";
                        String regex = ".*[a-zA-Z]+.*";
                        Matcher m = Pattern.compile(regex).matcher(mTargetId);
//                        mTargetId = "USER_1_11249";

                        if (m.matches()) {
                            if (mTargetId.contains("USER_")) {
                                String supplier_id = mTargetId.substring( mTargetId.indexOf("_")+1,mTargetId.lastIndexOf("_"));
                                String member_id = mTargetId.substring( mTargetId.lastIndexOf("_")+1,mTargetId.length());
                                WxChat(supplier_id,member_id,typeString,textMessage.getContent());

                            }else if(mTargetId.contains("STAFF_")){
                                String supplier_id = mTargetId.substring( mTargetId.indexOf("_")+1,mTargetId.lastIndexOf("_"));
                                String staff_id = mTargetId.substring( mTargetId.lastIndexOf("_")+1,mTargetId.length());

                            }
                        }

                    } else if (messageContent instanceof VoiceMessage) {//语音消息
                        VoiceMessage voiceMessage = (VoiceMessage) messageContent;
                        typeString = "2";

//                        String voice= read(voiceMessage.getUri().getPath());

//                        String voice2 = android.util.Base64.encodeToString(voice.getBytes(), android.util.Base64.NO_WRAP);

//                        getSave(mTargetId, rongYunGroupMemberBean.getName(), rongYunGroupMemberBean.getLogo(),typeString,voice2,"");


                    } else if (messageContent instanceof ImageMessage) {//图片消息
                        ImageMessage imageMessage = (ImageMessage) messageContent;

                        typeString = "3";

                        String image = android.util.Base64.encodeToString(imageMessage.getRemoteUri().toString().getBytes(), android.util.Base64.NO_WRAP);

//                        getSave(mTargetId, rongYunGroupMemberBean.getName(), rongYunGroupMemberBean.getLogo(), typeString,imageMessage.getRemoteUri().toString(), image);

                    } else if (messageContent instanceof RichContentMessage) {//图文消息
                        RichContentMessage richContentMessage = (RichContentMessage) messageContent;
                    } else {

                    }

                }
                return true;
            }
        });
    }


    //后台接口
    public void WxChat(final String supplier_id ,
                       final String member_id, final String type, final String content){

    }

    //备份接口
    public void getSave(final String supplier_id ,
                       final String member_id, final String type, final String content){

    }

}
