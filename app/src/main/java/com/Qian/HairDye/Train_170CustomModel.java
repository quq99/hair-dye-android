package com.Qian.HairDye;

import java.util.ArrayList;

import ai.fritz.core.FritzOnDeviceModel;
import ai.fritz.fritzvisionhairsegmentationmodel.HairSegmentationOnDeviceModel;
import ai.fritz.vision.imagesegmentation.MaskType;
import ai.fritz.vision.imagesegmentation.SegmentOnDeviceModel;

public class Train_170CustomModel extends SegmentOnDeviceModel {

    private static final String MODEL_PATH = "file:///android_asset/converted_model_hairnet.tflite";
    private static final String MODEL_ID = "9c021ee48c3c4d668b1f8a1a01198a71";
    private static final int MODEL_VERSION = 1;
    private static MaskType[] hair = { MaskType.HAIR };



    public Train_170CustomModel() {
        super(MODEL_PATH, MODEL_ID, MODEL_VERSION, hair, "input:0", 224, "Sigmoid:0", 224);
    }
}
