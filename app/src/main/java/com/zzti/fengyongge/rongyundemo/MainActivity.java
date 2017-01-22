package com.zzti.fengyongge.rongyundemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zzti.fengyongge.rongyundemo.bean.MemberBean;
import com.zzti.fengyongge.rongyundemo.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * @author fengyongge
 * @date 2017/1/22 0022
 * @description
 */
public class MainActivity extends BaseActivity {
    private List<MemberBean> message_list = new ArrayList();
    TextView tv_im;
    ListView lv;
    private String title;
    int total_conversation;
    private LayoutInflater inflater;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        tv_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启单聊
                if (RongIM.getInstance()!=null){
                    RongIM.getInstance().startPrivateChat(MainActivity.this,"56146",title);
                }
            }
        });

        adapter = new MessageAdapter();
        lv.setAdapter(adapter);
        getConversationList();

    }

    public void initView(){
        tv_im = (TextView) findViewById(R.id.tv_im);
        lv = (ListView) findViewById(R.id.lv);
    }



    private void getConversationList(){

        message_list.clear();

        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {

                if (conversations != null){
                    total_conversation = conversations.size();

                    //聊天列表融云返回的只是用户id，需要调用后台接口返回头像，名称

                    for (int i = 0; i < conversations.size(); i++) {
                        try {
                            if (conversations.get(i).getTargetId().contains("USER_")) {
                                getMemberDetail(conversations.get(i).getTargetId(),conversations.get(i).getReceivedTime(),conversations.get(i).getLatestMessage());
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }



    private void getMemberDetail(final String target_id, final long receivedTime, final Object latestMessage ) throws Exception {


//        MyNet.Inst(MainActivity.this).getMemberDetail(getId(target_id), new ApiCallback() {
//            @Override
//            public void onDataSuccess(JSONObject data) {
//
//                MemberBean msgBean = JSON.parseObject(data.getString("data"),MemberBean.class);
//
//                msgBean.setTargetId(target_id);
//                msgBean.setReceivedTime(receivedTime);
//
//                if(latestMessage instanceof TextMessage){
//                    msgBean.setLatestMessage(((TextMessage) latestMessage).getContent());
//                }
//
//                if(latestMessage instanceof VoiceMessage){
//                    msgBean.setLatestMessage("语音消息");
//                }
//
//                if(latestMessage instanceof RichContentMessage){
//                    msgBean.setLatestMessage("图文消息");
//                }
//
//                if(latestMessage instanceof ImageMessage){
//                    msgBean.setLatestMessage("图片消息");
//                }
//
//                if(latestMessage instanceof LocationMessage){
//                    msgBean.setLatestMessage("地理位置消息");
//                }
//
//                if(latestMessage instanceof InformationNotificationMessage){
//                    msgBean.setLatestMessage("系统消息");
//                }
//
//
//                message_list.add(msgBean);
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onDataError(JSONObject data) {
//
//            }
//
//            @Override
//            public void onNetError(String data) {
//
//            }
//        });
    }


    public class MessageAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return message_list.size();

        }

        @Override
        public Object getItem(int arg0) {
            return message_list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup arg2) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_main_item, null);
                holder = new ViewHolder();

                holder.meaaage_photo = (CircleImageView) convertView.findViewById(R.id.meaaage_photo);
                holder.message_name_text = (TextView) convertView.findViewById(R.id.message_name_text);
                holder.message_context_text = (TextView) convertView.findViewById(R.id.message_context_text);
                holder.message_time_text = (TextView) convertView.findViewById(R.id.message_time_text);
                holder.item = convertView.findViewById(R.id.item);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }



            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RongIM.getInstance().startPrivateChat(MainActivity.this, message_list.get(position).getTargetId(), message_list.get(position).getNick_name());

                }
            });

            holder.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteConversation(position);

                    return false;
                }
            });


            if (message_list.get(position).getTargetId().contains("USER")){

                holder.message_name_text.setText(message_list.get(position).getNick_name());

                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RongIM.getInstance().startPrivateChat(MainActivity.this, message_list.get(position).getTargetId(), message_list.get(position).getNick_name());
                    }
                });
            }

//            ImageLoader.getInstance().displayImage(message_list.get(position).getHead_img(),holder.meaaage_photo);
            holder.message_context_text.setText(message_list.get(position).getLatestMessage());
            holder.message_time_text.setText(TimeUtil.handTime(TimeUtil.getTime(message_list.get(position).getReceivedTime())));


            return convertView;
        }
    }

    class ViewHolder {
        View item;
        CircleImageView meaaage_photo;
        TextView message_name_text;
        TextView message_context_text;
        TextView message_time_text;
    }


    private void deleteConversation(final int position){

        RongIM.getInstance().getRongIMClient().removeConversation(Conversation.ConversationType.PRIVATE,
                message_list.get(position).getTargetId(), new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean b) {
                        try {
                            message_list.remove(position);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode arg0) {
                        // TODO Auto-generated method stub
                    }
                });
    }

    private String getId(String id){
        return id.split("_")[2];
    }
}
