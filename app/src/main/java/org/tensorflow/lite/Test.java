package org.tensorflow.lite;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Test {

    public Context context;
    public Interpreter interpreter;
    public final String MODEL_NAME = "dog-breed.tflite";

    public Test(Context context) {
        this.context = context;
    }

    // 1. 모델 로드 하는 코드(1)
    public void init() throws IOException {
        ByteBuffer model = loadModelFile(MODEL_NAME);
        model.order(ByteOrder.nativeOrder());
        interpreter = new Interpreter(model);

        initModelShape();
    }
    // 2. 모델 로드 하는 코드(2)
    private ByteBuffer loadModelFile(String modelName) throws IOException {
        AssetManager am = context.getAssets();
        AssetFileDescriptor afd = am.openFd(modelName);
        FileInputStream fis = new FileInputStream(afd.getFileDescriptor());
        FileChannel fc = fis.getChannel();
        long startOffset = afd.getStartOffset();
        long declaredLength = afd.getDeclaredLength();

        return fc.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // 3. interpreter를 통하여 0 번째 인덱스의 Tensor 를 가져오는 코드
    private void initModelShape() {
        Tensor inputTensor = interpreter.getInputTensor(0);
        int[] inputShape = inputTensor.shape();

        Tensor outputTensor = interpreter.getOutputTensor(0);
        int[] outputShape = outputTensor.shape();



    }
}
