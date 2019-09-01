package com.Qian.HairDye;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.camera2.CameraCharacteristics;
import android.media.Image;
import android.util.Size;

import ai.fritz.core.Fritz;
import ai.fritz.core.FritzOnDeviceModel;
import ai.fritz.fritzvisionhairsegmentationmodel.HairSegmentationOnDeviceModel;
import ai.fritz.vision.FritzVision;
import ai.fritz.vision.FritzVisionImage;
import ai.fritz.vision.FritzVisionOrientation;
import ai.fritz.vision.imagesegmentation.BlendMode;
import ai.fritz.vision.imagesegmentation.BlendModeType;
import ai.fritz.vision.imagesegmentation.FritzVisionSegmentResult;
import ai.fritz.vision.imagesegmentation.FritzVisionSegmentPredictor;
import ai.fritz.vision.imagesegmentation.MaskType;
import ai.fritz.vision.imagesegmentation.SegmentOnDeviceModel;

import com.Qian.HairDye.Train_170CustomModel;


public class MainActivity extends LiveCameraActivity {

    private static final String API_KEY = "4892125c17c843c6b06ebce6e77ea631";

    private FritzVisionSegmentPredictor predictor;
    private FritzVisionImage visionImage;
    private FritzVisionSegmentResult segmentResult;

    @Override
    protected void initializeFritz() {
        // TODO: Uncomment this and modify your api key above.
        Fritz.configure(this, API_KEY);
    }

    @Override
    protected void setupPredictor() {
        // STEP 1: Get the predictor and set the options.
        // ----------------------------------------------
        // A FritzOnDeviceModel object is available when a model has been
        // successfully downloaded and included with the app.
        // TODO: Create a predictor
        // SegmentOnDeviceModel onDeviceModel = new HairSegmentationOnDeviceModel();
        SegmentOnDeviceModel onDeviceModel = new Train_170CustomModel();
        predictor = FritzVision.ImageSegmentation.getPredictor(onDeviceModel);
        MaskType.HAIR.color = Color.BLUE;
        // ----------------------------------------------
        // END STEP 1
    }

    @Override
    protected void setupImageForPrediction(Image image) {
        // STEP 2: Create the FritzVisionImage object from media.Image
        // ------------------------------------------------------------------------
        // TODO: Add code for creating FritzVisionImage from a media.Image object
        int rotation = FritzVisionOrientation.getImageRotationFromCamera(this, cameraId);
        visionImage = FritzVisionImage.fromMediaImage(image, rotation);
        System.out.println(visionImage.getRotatedBitmapDimensions().toString());
        // ------------------------------------------------------------------------
        // END STEP 2
    }

    @Override
    protected void runInference() {
        // STEP 3: Run predict on the image
        // ---------------------------------------------------
        segmentResult = predictor.predict(visionImage);
        // ----------------------------------------------------
        // END STEP 3
    }

    @Override
    protected void showResult(Canvas canvas, Size cameraSize) {
        // STEP 4: Draw the prediction result
        // ----------------------------------
        if(segmentResult != null && visionImage != null) {
            BlendMode blendMode = BlendModeType.HUE.create();
            Bitmap maskBitmap = segmentResult.createMaskOverlayBitmap(blendMode.getAlpha());
            Bitmap blendedBitmap = visionImage.blend(maskBitmap, blendMode);
            // Hacky but putting this here to mirror the result for selfies.
            // TODO: Figure out how to apply the rotation to the preview camera session.
            if(getCameraFacingDirection() == CameraCharacteristics.LENS_FACING_FRONT){
                Matrix m = new Matrix();
                m.preScale(-1, 1);
                m.postTranslate(canvas.getWidth(), 0);
                canvas.setMatrix(m);
            }
            canvas.drawBitmap(blendedBitmap, null, new RectF(0, 0, cameraSize.getWidth(), cameraSize.getHeight()), null);
        }
        // ----------------------------------
        // END STEP 4
    }
}
