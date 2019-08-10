# Hair Dye Android   

![hairicon](./images/appicon.png)

This android app is based on the [Fritz tutorial](<https://github.com/fritzlabs/fritz-android-tutorials/tree/master/HairColoringApp>). I changed the model to our own model. Currently, the hair can be colored to RED.

For model **training** part, refer to our [repo](<https://github.com/aobo-y/hair-dye>).

The network model was proposed by [Alex L. Cheng C, etc. 'Real-time deep hair matting on mobile devices'](https://arxiv.org/pdf/1712.07168.pdf)

## ML model

I first convert our Pytorch model to tensorflow GraphDef model(end with .pb) by using ONNX and ONNX-TF. Then generate the Tensorflow Lite FlatBuffer file(.tflite) from a Tensorflow model(.pb). The model is placed in `/app/src/main/assets/converted_model_hairnet.tflite`.

## Requirements

- Android Studio 3.2 or above
- Android device in developer model (USB debugging enabled)

## Getting Started

**Step 1: Clone / Fork this repository and open the HairColoringApp in Android Studio**

```
git clone https://github.com/quq99/hair-dye-android.git
```

In Android Studio, choose "Open an existing Android Studio project" and select `hair-dye-android`.

**Step 2: Build the Android Studio Project**

First, "File->Sync Project with Gradle Files" to sync the gradle dependencies. Then, select "Build > Make Project" from the top nav. Download any missing libraries if applicable.

**Step 3: Install the app onto your device**

With your Android device connected, select `Run > Run App` from the top nav. When running the app for the first time, you'll have to give permissions to access the camera. After the app is installed and running, point your camera at someone's hair to automatically color it.



## Thanks

- Thanks for [Fritz](<https://www.fritz.ai/>) Framework.
