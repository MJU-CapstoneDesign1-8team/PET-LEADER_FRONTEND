package org.tensorflow.lite.examples.detect.network

/*
 "result": "D:\\ProjectList\\Github\\GitHub\\PET-READER_BACKEND\\5.png",
    "result_breed": true,
    "result_breed_imgPath": "yolov5\\runs\\detect\\exp4\\5.png",
    "result_muzzle": false,
    "result_muzzle_imgPath": "yolov5\\runs\\detect\\exp6\\5.png",
    "result_safety": true,
    "result_safety_imgPath": "yolov5\\runs\\detect\\exp5\\5.png"
*/
data class FlaskDto(
    val result : String,
    val result_breed : Boolean,
    val result_breed_imgPath : String,
    val result_muzzle : Boolean,
    val result_muzzle_imgPath : String,
    val result_safety : Boolean,
    val result_safety_imgPath : String,
)
