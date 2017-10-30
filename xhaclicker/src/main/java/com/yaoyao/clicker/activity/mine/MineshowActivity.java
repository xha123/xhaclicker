package com.yaoyao.clicker.activity.mine;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.control.GetImagePath;
import com.yaoyao.clicker.utils.DataUtils;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.MyUtils;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;
import com.yaoyao.clicker.utils.UIcallBack;
import com.yaoyao.clicker.view.PicWindow;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

/** 个人中心修改用户信息页面
 * Created by Administrator on 2017/8/9.
 */

public class MineshowActivity extends BaseActivity{
    ImageView back_iv;
    TextView title_tv,right_tv;
    SimpleDraweeView show_iv;
    EditText miao_ed,name_ed,number_ed,email_ed,sex_ed;

    private PicWindow picWindow;
    String name,udesshow,number,emailshow,picurl;
    int sexshow;

    @Override
    public void inidata() {

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_mineshow);
    }

    @Override
    public void iniview() {
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        right_tv = (TextView) findViewById(R.id.title_right_tv);
        miao_ed = (EditText) findViewById(R.id.mineshow_personshow_ed);
        name_ed = (EditText) findViewById(R.id.mineshow_personname_ed);
        number_ed = (EditText) findViewById(R.id.mineshow_phone_ed);
        email_ed = (EditText) findViewById(R.id.mineshow_email_ed);
        sex_ed = (EditText) findViewById(R.id.mineshow_sex_ed);
        show_iv = (SimpleDraweeView) findViewById(R.id.mineshow_show_iv);
    }

    @Override
    public void setview() {
        title_tv.setText(R.string.bianji);
        right_tv.setText(R.string.over);

        setall();

    }

    private void setall() {
        picurl = (String) SharedPreferenceUtils.get("picurl","");
        Uri uri = Uri.parse(picurl);
        DataUtils.getInstans().setImageSrc(show_iv,uri,160,160);
        name = (String) SharedPreferenceUtils.get("name","");
        name_ed.setText(name);
        Log.e(TAG, "setall: "+name );
        name_ed.setSelection(name.length());
        udesshow = (String) SharedPreferenceUtils.get("udes","");
        miao_ed.setText(udesshow);
        miao_ed.setSelection(udesshow.length());
        number = (String) SharedPreferenceUtils.get("phone","");
        number_ed.setText(number);
        number_ed.setSelection(number.length());
        sexshow = (int) SharedPreferenceUtils.get("sex",2);
        if(sexshow==0){
            sex_ed.setText("男");
            sex_ed.setSelection(1);
        }else if (sexshow==1){
            sex_ed.setText("女");
            sex_ed.setSelection(1);
        }
        emailshow = (String) SharedPreferenceUtils.get("email","");
        email_ed.setText(emailshow);
        email_ed.setSelection(emailshow.length());

    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
        right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               changeMess();
            }
        });
        show_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //头像来源选择（相册，拍照）
                //如果为空，创建实例（实现监听）
                if (picWindow == null) {
                    picWindow = new PicWindow(MineshowActivity.this, listener);

                }
                //如果已经显示，则关闭
                if (picWindow.isShowing()) {
                    picWindow.dismiss();
                    return;
                }
                picWindow.show();//显示来源选择框
            }
        });
    }
    //判断修改信息
    private void changeMess() {
        number = number_ed.getText().toString();
        udesshow = miao_ed.getText().toString();
        name = name_ed.getText().toString();
        emailshow = email_ed.getText().toString();
        String sexs = sex_ed.getText().toString();
        if (!DataUtils.getInstans().isChinaPhone(number)){
            showtoast(MineshowActivity.this,"手机号码不正确");
            return;
        }
        if (!emailshow.equals("")&&!DataUtils.getInstans().isEmail(emailshow)){
            showtoast(MineshowActivity.this,"邮箱格式不正确");
            return;
        }
        if (sexs.equals("男")){
            sexshow = 0;
        }else if (sexs.equals("女")){
            sexshow = 1;
        }else {
            showtoast(MineshowActivity.this,"性别请输入男或女");
            return;
        }
        final String url = HttpUtils.URL+"user/updateinfo";
        final Map<String,String> map = new HashMap<>();
        String uid = (String) SharedPreferenceUtils.get("uid","");
        map.put("uid",uid);
        map.put("name", MyUtils.getInstans().getIsoShow(name));
        map.put("phone",MyUtils.getInstans().getIsoShow(number));
        map.put("email",MyUtils.getInstans().getIsoShow(emailshow));
        map.put("sex",sexshow+"");
        udesshow = udesshow.replace("\n","\\n");
        map.put("udes",MyUtils.getInstans().getIsoShow(udesshow));
        map.put("picurl",picurl);
        String modjson = jsonEnclose(map).toString();
        Log.e(TAG, "changeMess: "+modjson );
        Map<String,String> allmap = new HashMap<>();
        allmap.put("modifiedMsg",modjson);

        Call call = HttpUtils.getInstance().mapCall(allmap,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: "+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    showtoast(MineshowActivity.this,"修改成功");
                    MyUtils.getInstans().setHomeShua(true);
                    MyUtils.getInstans().setMineShua(true);
                    AppManager.getAppManager().finishActivity();
                }else {
                    HttpUtils.getInstance().Errorcode(MineshowActivity.this,code);
                }
            }
        });
    }
    public final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    /**
     * 将对象分装为json字符串 (json + 递归)
     * @param obj 参数应为{@link java.util.Map} 或者 {@link java.util.List}
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object jsonEnclose(Object obj) {
        try {
            if (obj instanceof Map) {   //如果是Map则转换为JsonObject
                Map<String, Object> map = (Map<String, Object>)obj;
                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                JSONStringer jsonStringer = new JSONStringer().object();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    jsonStringer.key(entry.getKey()).value(jsonEnclose(entry.getValue()));
                }
                JSONObject jsonObject = new JSONObject(new JSONTokener(jsonStringer.endObject().toString()));
                return jsonObject;
            } else if (obj instanceof List) {  //如果是List则转换为JsonArray
                List<Object> list = (List<Object>)obj;
                JSONStringer jsonStringer = new JSONStringer().array();
                for (int i = 0; i < list.size(); i++) {
                    jsonStringer.value(jsonEnclose(list.get(i)));
                }
                JSONArray jsonArray = new JSONArray(new JSONTokener(jsonStringer.endArray().toString()));
                return jsonArray;
            } else {
                return obj;
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "jsonEnclose: "+e.toString() );
            return e.getMessage();
        }
    }


    Uri imageUricar;
    //图片选择弹窗的自定义监听
    private PicWindow.Listener listener = new PicWindow.Listener() {
        @Override
        public void toGallery() {
            //从相册中选择
            //清空裁剪的缓存
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                 /* 开启Pictures画面Type设定为image */
                Intent intent = new Intent();
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
             /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 111);

            }else {
                CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
                Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
                startActivityForResult(intent, CropHelper.REQUEST_CROP);
                Log.e(TAG, "toGallery: 相册选择" );
            }

        }

        @Override
        public void toCamera() {
            //从相机中选择

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                takePictureFromCamera();
            }else {
                CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
                Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
                try {
                    startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
                }catch (Exception e){
                    showtoast(MineshowActivity.this,"请检查手机拍照权限");
                }
            }
        }
    };

    //图片裁剪的handler
    private CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
//            //通过uri拿到图片文件
//            File file = new File(uri.getPath());
//            //业务类上传头像
//            updataAvatar(file);
            Log.e(TAG, "onPhotoCropped: 拿到uri"+uri );
            Bitmap picbit = getBitmapFromUri(uri);
            if (picbit==null){
                showtoast(MineshowActivity.this,"获取图片失败");
                return;
            }
            //转换为图片信息上传
            String base64Str = "";
            try
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                picbit.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                byte[] bytes = baos.toByteArray();
                base64Str = Base64.encodeToString(bytes, Base64.DEFAULT);
                baos.close();
            } catch (Exception e)
            {
                base64Str = "";
            }
            updataAvatar(picbit,base64Str);
        }

        @Override
        public void onCropCancel() {

        }

        @Override
        public void onCropFailed(String message) {

        }

        @Override
        public CropParams getCropParams() {
            //自定义裁剪大小参数
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 1000;
            cropParams.aspectY = 1000;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return MineshowActivity.this;
        }
    };

    private void updataAvatar(final Bitmap bitmap, final String base64Str) {
        Map<String,String> map = new HashMap<>();
        map.put("img",base64Str);
        map.put("type","1");
        String url = HttpUtils.URL+"upload/uploadimg";
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: "+body );

            }
        });
    }

    //转换为bitmap
    @Nullable
    private Bitmap getBitmapFromUri(Uri uri)
    {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        }catch (Exception e){
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //帮助我们去处理结果（裁剪完的图像）
        switch (requestCode){
            case 111:

                if (data!=null){
                    File imgUri = new File(GetImagePath.getPath(this, data.getData()));
                    Uri newUri = FileProvider.getUriForFile(MineshowActivity.this,"com.yaoyao.clicker.fileprovider",imgUri);
                    startPhotoZoom(newUri);//设置输入类型
                }
                break;

            case 510:
                Log.e(TAG, "onActivityResult: 510" );
//                try {
//                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                    mine_iv.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }

                Uri inputUri = FileProvider.getUriForFile(MineshowActivity.this, "com.yaoyao.clicker.fileprovider", mOutputImage);//通过FileProvider创建一个content类型的Uri
                startPhotoZoom(inputUri);//设置输入类型

                break;

            case 3333:
                Log.e(TAG, "onActivityResult: 3333" );
                if (outPutUri != null) {
//                    String path = parsePicturePath(this, outPutUri);
//                    mine_iv.setImageBitmap(BitmapFactory.decodeFile(path));
                    Bitmap picbit = getBitmapFromUri(outPutUri);

                    //转换为图片信息上传
                    String base64Str = "";
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        picbit.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                        byte[] bytes = baos.toByteArray();
                        base64Str = Base64.encodeToString(bytes, Base64.DEFAULT);
                        baos.close();
                    } catch (Exception e) {
                        base64Str = "";
                    }
                    updataAvatar(picbit,base64Str);
                } else {
                    Log.e(TAG, "onActivityResult: 3333空" );
                }
                break;

            default:
                Log.e(TAG, "onActivityResult: 回调图像" );
                CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
                break;
        }

    }


    Uri imageUri;
    File mOutputImage,mCropFile;
    public int REQUEST_CAPTURE = 510;
    public static final java.lang.String ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT";
    public static final java.lang.String ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE";
    private void takePictureFromCamera() {

        String pictureName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault()).format(new Date()) +
                "-" + System.currentTimeMillis() + ".jpg";

        mOutputImage = new File(getExternalCacheDir(), pictureName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(this,"com.yaoyao.clicker.fileprovider", mOutputImage);

            Log.e(TAG,imageUri.getPath());
        } else {
            imageUri = Uri.fromFile(mOutputImage);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if (componentName != null) {
            try {
                startActivityForResult(intent, REQUEST_CAPTURE);
            }catch (Exception e){
                BaseActivity.showtoast(MineshowActivity.this,"请检查手机拍照权限");
                return;
            }
        }
    }

    Uri outPutUri;
    //caijian
    public void startPhotoZoom(Uri inputUri) {
        if (inputUri == null) {
            Log.e(TAG, "startPhotoZoom: The uri is not exist" );
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            outPutUri = Uri.fromFile(mCropFile);
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        } else {
            outPutUri = Uri.fromFile(mCropFile);
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                String url = GetImagePath.getPath(MineshowActivity.this, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(inputUri, "image/*");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        startActivityForResult(intent, 3333);//这里就将裁剪后的图片的Uri返回了
    }

}
