# ImageLSB
基于LSB算法隐藏文本到图片中或从图片中提取隐藏的文本。

# 用法
- 隐藏文本
```java
    // 要隐藏的文本
    String msg = "andy123!";
    // 图片路径
    String imagePath = "xxxx";
    boolean isSucc = ImageSteganography.toImg(msg, imagePath);    
```

- 提取文本
```java
    // 图片路径
    String imagePath = "xxxx";
    // 提取文本
    String text = ImageSteganography.fromImg(imagePath);        
        
```

# 图片
只支持PNG, JPG格式