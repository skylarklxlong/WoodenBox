# 使用说明

### 此jar包功能为：直接将html格式的字符串显示在textview上。
gradle配置：
```
dependencies {
    compile 'net.sourceforge.htmlcleaner:htmlcleaner:2.16'
    compile 'com.osbcp.cssparser:cssparser:1.5'
}
```

使用方式：
```
HtmlSpanner htmlSpanner;

Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://获取图片路径列表

                    break;
                case 2://图片点击事件

                    break;
            }
        }
    };
    
htmlSpanner = new HtmlSpanner(this, width, handler);

final Spannable spannable = htmlSpanner.fromHtml(zhiHuStory.getBody());

tv_tnd_content.setText(spannable);
tv_tnd_content.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));

```
