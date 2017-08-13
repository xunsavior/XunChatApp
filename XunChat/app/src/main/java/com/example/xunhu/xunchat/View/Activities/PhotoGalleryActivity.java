package com.example.xunhu.xunchat.View.Activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xunhu.xunchat.Model.Entities.Image;
import com.example.xunhu.xunchat.R;
import com.example.xunhu.xunchat.View.AllAdapters.GalleryGridViewAdapter;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunhu on 8/12/2017.
 */
@EActivity(R.layout.photo_gallery_activity_layout)
public class PhotoGalleryActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>,
        GalleryGridViewAdapter.GalleryGridViewInterface{
    @ViewById RecyclerView rvGalleryActivity;
    @ViewById LinearLayout galleryLayout;
    @ViewById Button btnSelectionDone;
    GalleryGridViewAdapter adapter;
    private final static int MEDIA_STORE_LOADER_ID = 0;
    public static int selectedPhoto=0;
    List<Image> images = new ArrayList<>();
    List<Integer> items;
    @StringRes String done;
    @AfterViews void setPhotoGridViews(){
        selectedPhoto=0;
        items = new ArrayList<>();
        adapter = new GalleryGridViewAdapter(this,this,images);
        rvGalleryActivity.setLayoutManager(new GridLayoutManager(this,3));
        rvGalleryActivity.setAdapter(adapter);
        getLoaderManager().initLoader(MEDIA_STORE_LOADER_ID,null,  this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //details of each column we are looking
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
        };
        //specify my selection criteria (SQL where...)
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE+"="
                +MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
        return new android.content.CursorLoader(
                this,
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_ADDED+" DESC"
        );
    }
    @Click({R.id.btnSelectionDone}) void setPhotoGalleryClicked(View view){
        switch (view.getId()){
            case R.id.btnSelectionDone:
                if (selectedPhoto==0){
                    Toast.makeText(this,"Please select images",Toast.LENGTH_SHORT).show();
                }else {
                    switch (items.size()){
                        case 1:
                            EditMomentActivity_.intent(this).
                                    extra("image",true).
                                    extra("image_0",String.valueOf(images.get(items.get(0)).getUri())).start();
                            break;
                        case 2:
                            EditMomentActivity_.intent(this).
                                    extra("image",true).
                                    extra("image_0",String.valueOf(images.get(items.get(0)).getUri())).
                                    extra("image_1",String.valueOf(images.get(items.get(1)).getUri())).start();
                            break;
                        case 3:
                            EditMomentActivity_.intent(this).
                                    extra("image",true).
                                    extra("image_0",String.valueOf(images.get(items.get(0)).getUri())).
                                    extra("image_1",String.valueOf(images.get(items.get(1)).getUri())).
                                    extra("image_2",String.valueOf(images.get(items.get(2)).getUri())).start();
                            break;
                    }
                    finish();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()){
            do {
                int idIndex = data.getColumnIndex(MediaStore.Files.FileColumns._ID);
                Uri imageUri= ContentUris
                        .withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                data.getInt(idIndex));
                Image image = new Image(imageUri,0);
                images.add(image);
            }while (data.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    public void addUri(int position) {
        selectedPhoto++;
        btnSelectionDone.setText(done+"("+String.valueOf(selectedPhoto)+"/"+3+")");
        items.add(position);
        images.get(position).setIsChecked(1);
        adapter.notifyItemChanged(position);
    }
    @Override
    public void removeUri(int position) {
        selectedPhoto--;
        btnSelectionDone.setText(done+"("+String.valueOf(selectedPhoto)+"/"+3+")");
        items.remove(items.indexOf(position));
    }
}
