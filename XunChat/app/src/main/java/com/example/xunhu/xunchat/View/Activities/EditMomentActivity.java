package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.xunhu.xunchat.Model.Entities.Me;
import com.example.xunhu.xunchat.Presenter.PostActionPresenter;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllViewClasses.MyDialog;
import com.example.xunhu.xunchat.View.Fragments.LocationListDialog;
import com.example.xunhu.xunchat.View.Interfaces.PostPhotoView;
import com.example.xunhu.xunchat.View.MainActivity;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntegerRes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunhu on 6/20/2017.
 */
@EActivity(R.layout.edit_moment_layout)
public class EditMomentActivity extends Activity implements LocationListDialog.LocationDialogInterface,
        PostPhotoView{
    public static Me me = MainActivity.me;
    @ViewById(R.id.iv_edit_moment_back) ImageView btnBack;
    @ViewById(R.id.btn_edit_moment_send) Button btnSend;
    @ViewById(R.id.et_edit_moment) EditText edMoment;
    @ViewById(R.id.iv_get_current_location) ImageView ivCurrentLocation;
    @ViewById(R.id.tv_display_current_location) TextView tvDisplayLocation;
    @ViewById
    ImageView ivSelectedOne,ivSelectedTwo,ivSelectedThree;
    @ViewById
    LinearLayout selectedImageLayout;
    String uriOne,uriTwo,uriThree;
    List<String> images = new ArrayList<>();
    MyDialog myDialog;
    PostActionPresenter postActionPresenter;
    PostPhotoView postPhotoView = this;
    @IntegerRes
    Integer postText,postImage;
    boolean hasImage = false;
    @AfterViews void setEditMomentActivityViews(){
        SubActivity.isRefreshNeeded=false;
        myDialog = new MyDialog(this);
        hasImage = getIntent().getExtras().getBoolean("image");
        try {
            checkImagePostOrNot(hasImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Click({R.id.iv_edit_moment_back,R.id.btn_edit_moment_send,R.id.iv_get_current_location})
    public void respond(View view){
        switch (view.getId()){
            case R.id.iv_edit_moment_back:
                onBackPressed();
                break;
            case R.id.btn_edit_moment_send:
                myDialog.createLoadingGifDialog();
                if (hasImage){
                    postImages();
                }else {
                    postActionPresenter = new PostActionPresenter(EditMomentActivity.this);
                    postActionPresenter.setPostPhotoView(postPhotoView);
                    postActionPresenter.operatePost(MainActivity.me.getId(),
                            postText,edMoment.getText().toString(),
                            String.valueOf(System.currentTimeMillis()),
                            tvDisplayLocation.getText().toString());
                }
                break;
            case R.id.iv_get_current_location:
                LocationListDialog dialog = new LocationListDialog("moment");
                dialog.show(getFragmentManager(),"Location Dialog");
                break;
            default:
                break;
        }
    }
    public void postImages(){
        new ImageToCodeTask().execute(images);
    }
    public void checkImagePostOrNot(boolean hasImage) throws IOException {
        if (!hasImage){
            selectedImageLayout.setVisibility(View.GONE);
        }else {
             uriOne = getIntent().getStringExtra("image_0");
             uriTwo = getIntent().getStringExtra("image_1");
             uriThree = getIntent().getStringExtra("image_2");
            ivSelectedOne.setImageURI(Uri.parse(uriOne));
            if (uriTwo!=null){
                ivSelectedTwo.setImageURI(Uri.parse(uriTwo));
            }
            if (uriThree!=null){
                ivSelectedThree.setImageURI(Uri.parse(uriThree));
            }
            images.add(uriOne);
            if (uriTwo!=null){
                images.add(uriTwo);
            }
            if (uriThree!=null){
                images.add(uriThree);
            }
        }
    }
    @Override
    public void setSelectedLocation(String location) {
        tvDisplayLocation.setText(location);
    }

    @Override
    public void setEditLocation(String title, String content) {

    }

    @Override
    public void postSuccess(String msg) {
        myDialog.cancelLoadingGifDialog();
        Toast.makeText(getApplicationContext(),"post success",Toast.LENGTH_SHORT).show();
        SubActivity.isRefreshNeeded=true;
        finish();
    }

    @Override
    public void postFail(String msg) {
        myDialog.cancelLoadingGifDialog();
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

     class ImageToCodeTask extends AsyncTask<List<String>,Void,List<String>> {
        @Override
        protected List<String> doInBackground(List<String>[] lists) {
            List<String> list = lists[0];
            List<String> images = new ArrayList<>();
            for (int i=0;i<list.size();i++){
                try {
                    Bitmap bitmap = resizeBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.parse(list.get(i))));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    images.add(encoded);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return images;
        }
        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            JSONArray jsonArray = new JSONArray();
            for (int i =0;i<strings.size();i++){
                try {
                    jsonArray.put(i,strings.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            JSONObject object = new JSONObject();
            try {
                object.put("images",jsonArray.toString());
                object.put("caption",edMoment.getText().toString());
                postActionPresenter = new PostActionPresenter(EditMomentActivity.this);
                postActionPresenter.setPostPhotoView(postPhotoView);
                postActionPresenter.operatePost(MainActivity.me.getId(),postImage,object.toString(),
                        String.valueOf(System.currentTimeMillis()),
                        tvDisplayLocation.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    Bitmap resizeBitmap(Bitmap bitmap){
        float ratio = (float) bitmap.getByteCount()/(float) 1024/(float)1024;
        if (ratio>=1){
            float originalWidth = bitmap.getWidth();
            float originalHeight = bitmap.getHeight();
            float resizeWidth = (float) (originalWidth/Math.sqrt(ratio));
            float resizeHeight = (float) (originalHeight/Math.sqrt(ratio));
            bitmap=Bitmap.createScaledBitmap(bitmap, (int) resizeWidth, (int) resizeHeight, true);
        }
        return bitmap;
    }
}
