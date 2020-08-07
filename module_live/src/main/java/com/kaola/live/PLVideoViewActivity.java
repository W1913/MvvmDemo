package com.kaola.live;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kaola.live.utils.Config;
import com.kaola.live.utils.Utils;
import com.kaola.live.widget.MediaController;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnAudioFrameListener;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoFrameListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoView;
import com.pro.base.router.RouterActivityPath;

import java.util.Arrays;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * This is a demo activity of PLVideoView
 *
 * @author wjh
 */

@Route(path = RouterActivityPath.Live.PAGER_LIVE)
public class PLVideoViewActivity extends AppCompatActivity {

    private static final String TAG = PLVideoViewActivity.class.getSimpleName();

    private PLVideoView mVideoView;
    private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
    private MediaController mMediaController;

    private boolean mIsLiveStreaming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl_video_view);
        String videoPath = getIntent().getStringExtra("videoPath");
//        String videoPath = "http://demo-videos.qnsdk.com/movies/qiniu.mp4";
        mIsLiveStreaming = getIntent().getIntExtra("liveStreaming", 1) == 1;

        mVideoView = findViewById(R.id.VideoView);

        View loadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(loadingView);

        View mCoverView = findViewById(R.id.CoverView);
        mVideoView.setCoverView(mCoverView);


        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
//        int codec = AVOptions.MEDIA_CODEC_AUTO;
        AVOptions options = new AVOptions();

        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codec);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming ? 1 : 0);
        boolean disableLog = getIntent().getBooleanExtra("disable-log", false);
        // options.setString(AVOptions.KEY_DNS_SERVER, "127.0.0.1");
        options.setInteger(AVOptions.KEY_LOG_LEVEL, disableLog ? 5 : 0);
        boolean cache = getIntent().getBooleanExtra("cache", false);
        if (!mIsLiveStreaming && cache) {
            options.setString(AVOptions.KEY_CACHE_DIR, Config.DEFAULT_CACHE_DIR);
        }
        boolean vcallback = getIntent().getBooleanExtra("video-data-callback", false);
        if (vcallback) {
            options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
        }
        boolean acallback = getIntent().getBooleanExtra("audio-data-callback", false);
        if (acallback) {
            options.setInteger(AVOptions.KEY_AUDIO_DATA_CALLBACK, 1);
        }
        if (!mIsLiveStreaming) {
            int startPos = getIntent().getIntExtra("start-pos", 0);
            options.setInteger(AVOptions.KEY_START_POSITION, startPos * 1000);
        }
        // options.setString(AVOptions.KEY_COMP_DRM_KEY,"cWoosgRk");
        mVideoView.setAVOptions(options);

        // Set some listeners
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnVideoFrameListener(mOnVideoFrameListener);
        mVideoView.setOnAudioFrameListener(mOnAudioFrameListener);

        mVideoView.setVideoPath(videoPath);
        mVideoView.setLooping(getIntent().getBooleanExtra("loop", false));

        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(this, !mIsLiveStreaming, mIsLiveStreaming);
        mMediaController.setOnClickSpeedAdjustListener(mOnClickSpeedAdjustListener);
        mVideoView.setMediaController(mMediaController);
        IM();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaController.getWindow().dismiss();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }


    private void IM(){

        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            /**
             * 接收实时或者离线消息。
             * 注意:
             * 1. 针对接收离线消息时，服务端会将 200 条消息打成一个包发到客户端，客户端对这包数据进行解析。
             * 2. hasPackage 标识是否还有剩余的消息包，left 标识这包消息解析完逐条抛送给 App 层后，剩余多少条。
             * 如何判断离线消息收完：
             * 1. hasPackage 和 left 都为 0；
             * 2. hasPackage 为 0 标识当前正在接收最后一包（200条）消息，left 为 0 标识最后一包的最后一条消息也已接收完毕。
             *
             * @param message    接收到的消息对象
             * @param left       每个数据包数据逐条上抛后，还剩余的条数
             * @param hasPackage 是否在服务端还存在未下发的消息包
             * @param offline    消息是否离线消息
             * @return 是否处理消息。 如果 App 处理了此消息，返回 true; 否则返回 false 由 SDK 处理。
             */
            @Override
            public boolean onReceived(Message message, int i) {

                return false;
            }
        });

        RongIMClient.connect("pvzStikOaaFgqrGxAPAypPuzvmiTFLLXsMh2k5TsZ9U=@ud55.cn.rongnav.com;ud55.cn.rongcfg.com", new RongIMClient.ConnectCallbackEx() {
            /**
             * 数据库回调.
             * @param code 数据库打开状态. DATABASE_OPEN_SUCCESS 数据库打开成功; DATABASE_OPEN_ERROR 数据库打开失败
             */
            @Override
            public void OnDatabaseOpened(RongIMClient.DatabaseOpenStatus code) { }
            /**
             * token 无效
             */
            @Override
            public void onTokenIncorrect() { }

            /**
             * 成功回调
             * @param userId 当前用户 ID
             */
            @Override
            public void onSuccess(String userId) {
                String chatroomId = "123";
                int defMessageCount = 50;
                Log.e("RongIMClient", "onSuccess: "+userId);
                RongIMClient.getInstance().joinExistChatRoom(chatroomId, defMessageCount, new RongIMClient.OperationCallback() {
                    /**
                     * 成功回调
                     */
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "IM: " );
                        sendIm();
                    }

                    /**
                     * 失败回调
                     * @param errorCode 错误码
                     */
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.e(TAG, "IM: " );

                    }
                });


            }

            /**
             * 错误回调
             * @param errorCode 错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("onError", "onSuccess: "+errorCode);
            }
        });

    }

    private void sendIm(){
        String content = "发送消息";

        Conversation.ConversationType conversationType = Conversation.ConversationType.CHATROOM;
        String targetId = "123";

        TextMessage messageContent = TextMessage.obtain(content);
        Message message = Message.obtain(targetId, conversationType, messageContent);
        RongIMClient.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback() {
            /**
             * 消息发送前回调, 回调时消息已存储数据库
             * @param message 已存库的消息体
             */
            @Override
            public void onAttached(Message message) {

            }
            /**
             * 消息发送成功。
             * @param message 发送成功后的消息体
             */
            @Override
            public void onSuccess(Message message) {
                Log.e("IM---", message.toString());
            }

            /**
             * 消息发送失败
             * @param message   发送失败的消息体
             * @param errorCode 具体的错误
             */
            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                Log.e("IM---errorCode", message.toString());

            }
        });


    }


    public void onClickSwitchScreen(View v) {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoView.getDisplayAspectRatio()) {
            case PLVideoView.ASPECT_RATIO_ORIGIN:
                Utils.showToastTips(this, "Origin mode");
                break;
            case PLVideoView.ASPECT_RATIO_FIT_PARENT:
                Utils.showToastTips(this, "Fit parent !");
                break;
            case PLVideoView.ASPECT_RATIO_PAVED_PARENT:
                Utils.showToastTips(this, "Paved parent !");
                break;
            case PLVideoView.ASPECT_RATIO_16_9:
                Utils.showToastTips(this, "16 : 9 !");
                break;
            case PLVideoView.ASPECT_RATIO_4_3:
                Utils.showToastTips(this, "4 : 3 !");
                break;
            default:
                break;
        }
    }

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
                    Utils.showToastTips(PLVideoViewActivity.this, "first video render time: " + extra + "ms");
                    Log.i(TAG, "Response: " + mVideoView.getResponseInfo());
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    Log.i(TAG, "video frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    Log.i(TAG, "audio frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.i(TAG, "Gop Time: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                case PLOnInfoListener.MEDIA_INFO_METADATA:
                    Log.i(TAG, mVideoView.getMetadata().toString());
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                    updateStatInfo();
                    break;
                case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                    Log.i(TAG, "Connected !");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    Log.i(TAG, "Rotation changed: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_LOOP_DONE:
                    Log.i(TAG, "Loop done");
                    break;
                case PLOnInfoListener.MEDIA_INFO_CACHE_DOWN:
                    Log.i(TAG, "Cache done");
                    break;
                case PLOnInfoListener.MEDIA_INFO_STATE_CHANGED_PAUSED:
                    Log.i(TAG, "State paused");
                    break;
                case PLOnInfoListener.MEDIA_INFO_STATE_CHANGED_RELEASED:
                    Log.i(TAG, "State released");
                    break;
                default:
                    break;
            }
        }
    };

    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    Log.e(TAG, "IO Error!");
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                    Utils.showToastTips(PLVideoViewActivity.this, "failed to open player !");
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
                    Utils.showToastTips(PLVideoViewActivity.this, "failed to seek !");
                    return true;
                case PLOnErrorListener.ERROR_CODE_CACHE_FAILED:
                    Utils.showToastTips(PLVideoViewActivity.this, "failed to cache url !");
                    break;
                default:
                    Utils.showToastTips(PLVideoViewActivity.this, "unknown error !");
                    break;
            }
            finish();
            return true;
        }
    };

    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            Log.i(TAG, "Play Completed !");
            Utils.showToastTips(PLVideoViewActivity.this, "Play Completed !");
            if (!mIsLiveStreaming) {
                mMediaController.refreshProgress();
            }
            //finish();
        }
    };

    private PLOnBufferingUpdateListener mOnBufferingUpdateListener = new PLOnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(int precent) {
            Log.i(TAG, "onBufferingUpdate: " + precent);
        }
    };

    private PLOnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLOnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(int width, int height) {
            Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
        }
    };

    private PLOnVideoFrameListener mOnVideoFrameListener = new PLOnVideoFrameListener() {
        @Override
        public void onVideoFrameAvailable(byte[] data, int size, int width, int height, int format, long ts) {
            Log.i(TAG, "onVideoFrameAvailable: " + size + ", " + width + " x " + height + ", " + format + ", " + ts);
            if (format == PLOnVideoFrameListener.VIDEO_FORMAT_SEI && bytesToHex(Arrays.copyOfRange(data, 19, 23)).equals("74733634")) {
                // If the RTMP stream is from Qiniu
                // Add &addtssei=true to the end of URL to enable SEI timestamp.
                // Format of the byte array:
                // 0:       SEI TYPE                    This is part of h.264 standard.
                // 1:       unregistered user data      This is part of h.264 standard.
                // 2:       payload length              This is part of h.264 standard.
                // 3-18:    uuid                        This is part of h.264 standard.
                // 19-22:   ts64                        Magic string to mark this stream is from Qiniu
                // 23-30:   timestamp                   The timestamp
                // 31:      0x80                        Magic hex in ffmpeg
                Log.i(TAG, " timestamp: " + Long.valueOf(bytesToHex(Arrays.copyOfRange(data, 23, 31)), 16));
            }
        }
    };

    private PLOnAudioFrameListener mOnAudioFrameListener = new PLOnAudioFrameListener() {
        @Override
        public void onAudioFrameAvailable(byte[] data, int size, int samplerate, int channels, int datawidth, long ts) {
            Log.i(TAG, "onAudioFrameAvailable: " + size + ", " + samplerate + ", " + channels + ", " + datawidth + ", " + ts);
        }
    };

    private MediaController.OnClickSpeedAdjustListener mOnClickSpeedAdjustListener = new MediaController.OnClickSpeedAdjustListener() {
        @Override
        public void onClickNormal() {
            // 0x0001/0x0001 = 2
            mVideoView.setPlaySpeed(0X00010001);
        }

        @Override
        public void onClickFaster() {
            // 0x0002/0x0001 = 2
            mVideoView.setPlaySpeed(0X00020001);
        }

        @Override
        public void onClickSlower() {
            // 0x0001/0x0002 = 0.5
            mVideoView.setPlaySpeed(0X00010002);
        }
    };

    private String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    //设置fps和延时
    private void updateStatInfo() {
        long bitrate = mVideoView.getVideoBitrate() / 1024;
        final String stat = bitrate + "kbps, " + mVideoView.getVideoFps() + "fps";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
}
