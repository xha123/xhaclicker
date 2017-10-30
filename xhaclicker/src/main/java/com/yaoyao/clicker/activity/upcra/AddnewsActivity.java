package com.yaoyao.clicker.activity.upcra;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.adapter.AddnewsRecyclerAdapter;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.utils.DataUtils;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.MyUtils;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;
import com.yaoyao.clicker.utils.UIcallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/12.
 */

public class AddnewsActivity extends BaseActivity{
    ImageView back_iv;
    TextView title_tv,up_tv;
    EditText add_ed;
    AddnewsRecyclerAdapter addnewsRecyclerAdapter;
    RecyclerView recyclerView;
    List<String> list;

    OSS oss;
    String endpoint;
    int uppos;//上传的pos
    String updateduotu;//传给服务器的图片
    String userid;
    @Override
    public void inidata() {
        userid = (String) SharedPreferenceUtils.get("uid","");
        list = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            list = bundle.getStringArrayList("image");
        }
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_addnews);
        endpoint = "oss-cn-shenzhen.aliyuncs.com";
    }

    @Override
    public void iniview() {
        recyclerView = (RecyclerView) findViewById(R.id.addnews_recy);
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        up_tv = (TextView) findViewById(R.id.addnews_up_tv);
        add_ed = (EditText) findViewById(R.id.addnews_add_ed);
    }

    @Override
    public void setview() {
        title_tv.setText(R.string.upphoto);

        String stsaccesskeyid = MyUtils.getInstans().getAccessKey();
        Log.e(TAG, "setview: "+stsaccesskeyid );
        if (stsaccesskeyid==null){
            getOssAccess();
        }else {
            setOss();
        }

        setRecy();
    }

    private void getOssAccess() {
        String url = HttpUtils.URL+"aliststoken";
        Map<String,String> map = new HashMap<>();
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: ststoken"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("status");
                if (code.equals(HttpUtils.SUCCESS)){
                    MyUtils.getInstans().setAccessKey((String) result.get("AccessKeyId"));
                    MyUtils.getInstans().setSecretKeyId((String) result.get("AccessKeySecret"));
                    MyUtils.getInstans().setSecurityToken((String) result.get("SecurityToken"));
                    MyUtils.getInstans().setExpiration((String) result.get("Expiration"));
                    setOss();
                }else {
                    HttpUtils.getInstance().Errorcode(AddnewsActivity.this,code);
                }
            }
        });
    }

    private void setOss() {

        String AccessKeyId = MyUtils.getInstans().getAccessKey();
        String SecretKeyId = MyUtils.getInstans().getSecretKeyId();
        String SecurityToken = MyUtils.getInstans().getSecurityToken();
    // 在移动端建议使用STS方式初始化OSSClient。更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId,SecretKeyId,SecurityToken);
    //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//开启可以在控制台看到日志，并且会支持写入手机sd卡中的一份日志文件位置在SDCard_path\OSSLog\logs.csv  默认不开启
//日志会记录oss操作行为中的请求数据，返回数据，异常信息
//例如requestId,response header等
//android_version：5.1  android版本
//mobile_model：XT1085  android手机型号
//network_state：connected  网络状况
//network_type：WIFI 网络连接类型
//具体的操作行为信息:
//[2017-09-05 16:54:52] - Encounter local execpiton: //java.lang.IllegalArgumentException: The bucket name is invalid.
//A bucket name must:
//1) be comprised of lower-case characters, numbers or dash(-);
//2) start with lower case or numbers;
//3) be between 3-63 characters long.
//------>end of log
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
    }

    private void setRecy() {
        addnewsRecyclerAdapter = new AddnewsRecyclerAdapter(this);
        addnewsRecyclerAdapter.setListimg(list);
        addnewsRecyclerAdapter.setOnClick(onClick);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(addnewsRecyclerAdapter);
        addnewsRecyclerAdapter.notifyDataSetChanged();
    }

    AddnewsRecyclerAdapter.OnClick onClick = new AddnewsRecyclerAdapter.OnClick() {
        @Override
        public void onclick(int position, int allint) {
            PhotoPickerIntent intent = new PhotoPickerIntent(AddnewsActivity.this);
            intent.setPhotoCount(allint);
            intent.setShowCamera(true);
            startActivityForResult(intent, 1024);
        }
    };

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        up_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addshow = add_ed.getText().toString();
                if (list.size()<=0||addshow==null||addshow.length()==0){
                    showtoast(AddnewsActivity.this,"请填写描述或增加图片");
                    return;
                }else {
                    updateduotu = "";
                    updateTu();
                }
            }
        });
    }

    private void delTu() {
        String url = HttpUtils.URL+"pic/delpic";
        Map<String,String> map = new HashMap<>();
        map.put("pid","352");
        map.put("requid",userid);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: 删除发表"+body );
            }
        });
    }

    //递归调用向阿里云服务器传图片
    private void updateTu() {
        if (uppos==list.size()){
            updateAll();
            return;
        }else {

        }
        String uploadFilepath = list.get(uppos);
        Log.e(TAG, "updateTu: 上传的图片地址"+uploadFilepath );
        String uuid = MyUtils.getInstans().getMyUUID();
        String objectKey = uuid+".jpeg";
        updateduotu = updateduotu+"http://你自己申请的阿里云地址/"+objectKey;
        if (uppos<list.size()-1){
            updateduotu = updateduotu+",";
        }
        Log.e(TAG, "updateTu: key"+objectKey );
        PutObjectRequest put = new PutObjectRequest("clicker1", objectKey, uploadFilepath);
        // 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.e("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
//            }
//        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.e("PutObject", "UploadSuccess"+result.toString());
                uppos++;
                updateTu();
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
// task.cancel(); // 可以取消任务
// task.waitUntilFinished(); // 可以等待直到任务完成
    }

    private void updateAll() {
        String url = HttpUtils.URL+"pic/upnewpic";
        String pdes = add_ed.getText().toString();
        Map<String,String> map = new HashMap<>();
        map.put("uid",userid);
        map.put("purl",updateduotu);
        String isopdes = MyUtils.getInstans().getIsoShow(pdes);
        map.put("pdes",isopdes);
        String picsize = getImageWidthHeight(list.get(0));
        Log.e(TAG, "updateAll: 图片宽高"+picsize );
        map.put("size",picsize);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: 发表图片"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                        uppos=0;
                    MyUtils.getInstans().setHomeShua(true);
                    MyUtils.getInstans().setMineShua(true);
                    AppManager.getAppManager().finishActivity();
                }else {
                    HttpUtils.getInstance().Errorcode(AddnewsActivity.this,code);
                }
            }
        });
    }

    public static String getImageWidthHeight(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return options.outWidth+"-"+options.outHeight;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode==1024){
            ArrayList<String> photos = new ArrayList<>();
                   photos =  data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            list.addAll(photos);
            addnewsRecyclerAdapter.setListimg(list);
            addnewsRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
