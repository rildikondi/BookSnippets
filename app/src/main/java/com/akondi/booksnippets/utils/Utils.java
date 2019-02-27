package com.akondi.booksnippets.utils;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class Utils {

    public static AbstractDraweeController reduceImageSize(String file_name, int size){
        //ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(file_name))
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Utils.getPackageLabelUri(file_name))
                .setResizeOptions(new ResizeOptions(size, size))
                .build();
        return Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setTapToRetryEnabled(true)
                .build();
    }

    //API_URL +  GET_LABEL_URL + file_name
    private static Uri getPackageLabelUri(String file_name){
        return Uri.parse(file_name);
    }
}
