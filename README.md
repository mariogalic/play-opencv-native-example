# Setup Native OpenCV library in Play Framework

This example shows how to load native OpenCV in Scala Play project. 
Also, it is an attempt to answer Stack Overflow question [UnsatisfiedLinkError: How to load native library of opencv when running play application?](https://stackoverflow.com/questions/55520731/unsatisfiedlinkerror-how-to-load-native-library-of-opencv-when-running-play-app)

The key is to make sure the [native library and its Java counterpart are loaded by the same classloader](https://github.com/playframework/playframework/issues/2212#issuecomment-58824866),
which is not the case when executing Play's `sbt run`.

1. Install OpenCV with Java support in Mac as per [docs](https://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html#install-opencv-3-x-under-macos)
1. `brew install ant` ([do not forget this step](https://stackoverflow.com/a/46656269/5205022))
1. `brew edit opencv`
3. Switch the java flag on: `-DBUILD_opencv_java=ON`
4. `brew install --build-from-source opencv`
5. native library and its java counterpart should be in 
    ```
    /usr/local/Cellar/opencv/4.0.1/share/java/opencv4/libopencv_java401.dylib
    /usr/local/Cellar/opencv/4.0.1/share/java/opencv4/opencv-401.jar
    ```
1. Create `lib/` directory under project's root
1. Copy `opencv-401.jar` to `lib/`
1. Point JVM's `java.library.path` to native libraries locations
    ```
    export JAVA_OPTS="-Djava.library.path=/usr/local/Cellar/opencv/4.0.1/lib:/usr/local/Cellar/opencv/4.0.1/share/java/opencv4"
    ```
1. Import `play-native-loader`:
    ```
    libraryDependencies += "com.typesafe.play" %% "play-native-loader" % "1.0.0"
    ``` 
1. Load opencv with `NativeLoader.load` instead of `System.loadLibrary`
   ```
   NativeLoader.load(Core.NATIVE_LIBRARY_NAME)
   ```
1. Test with `sbt run` and hitting [localhost:9000](http://localhost:9000)

