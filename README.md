# 递到
递到 已上传至应用宝  下载链接
pc端：http://android.myapp.com/myapp/detail.htm?apkName=com.example.kys_31.team
移动端：http://a.app.qq.com/o/simple.jsp?pkgname=com.example.kys_31.team
二维码：
<img src= "http://o.app.qq.com/image/microQr.png?template=1&url=http%3A%2F%2Fa.app.qq.com%2Fo%2Fsimple.jsp%3Fpkgname%3Dcom.example.kys_31.team&imageUrl=&pkgName=com.example.kys_31.team" width=100 heigth=100>
<br><br>
<h2>悬浮窗功能</h2>
<h4>获得手机状态栏高度：</h4>
通过反射获取状态栏高度
<pre><code>
//通过反射获得状态栏高度
Class<?> c=Class.forName("com.android.internal.R$dimen");
Object o=c.newInstance();
Field field=c.getField("status_bar_height");
int x =(Integer)field.get(o); //得到的是px值
statusBarHeight=getResources().getDimensionPixelSize(x); //将x值乘以 density 得到相应的dp值。
</code></pre>

<h4>2、实现思路</h4>

（1）自定义ViewGroup，为了复写onTouchEvent(......) 监听滑动事件获得x, y值；

（2）创建系统级别Window概念，需要加权限android.permission.SYSTEM_ALERT_WINDOW；

（3）将自定义的ViewGroup添加进Window概念。

（4）将监听得到的x, y 值赋值到 WindowManager.Layoutparams 的 x, y 上。

（5）不断通过windowManager.updateViewlayout(......)更新Window概念的位置。


<h3>相关源码看 递到项目 悬浮窗功能实现。<h3>
