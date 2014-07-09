
auth:@卓轩(高江涛)

#### Android Native 与 Html5 互相通信整理

##### 一、打开网络权限以及基础设置
 
 ```
  <uses-permission android:name="android.permission.INTERNET"/>
 ```
 
 1、如果希望点击链接继续在当前browser中响应，而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象或者覆盖webview的 setWebChromeClient.
 
 ```
 requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
mgjWebView = (WebView)findViewById(R.id.mgjWebView);
settings.setJavaScriptEnabled(true); //启用webview支持js
mgjWebView.setWebViewClient(new WebViewClient()); //设置为应用自行处理，不使用android的浏览器打开

 ```
 2、在js中使用android自身的对话框
 
 ```
 //设置js中使用android自身的对话框
 mgjWebView.setWebChromeClient(new WebChromeClient(){
	 @Override
     public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				result.confirm();
				return true;
			}
	 @Override
	 public boolean onJsConfirm(WebView view, String url,
					String message, JsResult result) {
				result.confirm();
				return true;
			}
 });
 ```
 3、如果不做特殊的处理的话，点击 “Back”； 整个Browser会调用finish()而结束自身，如果希望浏览的网 页回退而不是推出浏览器，需要在当前Activity中处理并消费掉该Back事件。
 
 ```
 //消费系统的回退按键
 @Override
 public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && mgjWebView.canGoBack()){
			mgjWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
 }
 ```
 
#### 二、在mgjWebView中注入Java对象，是的h5页面上的js可以调用native的组件
###### 1、封装native的java组件对象，建议将多媒体，消息，手势分别建立，这样容易管理和扩展。
android 4.2 之后版本提供给js调用的函数必须带有注释语句@JavascriptInterface 

```
/**
 * 
 * <p>
 *  消息提示 给 h5 的接口
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年5月6日
 * @version： V1.0
 */
public class MessageInJsHander extends InJavaScriptHander{

	public MessageInJsHander(Activity act, Context context) {
		super(act, context);
	}
	/**
	 * 使用android自身的提示框
	 * @param title
	 * @param message
	 */
	@JavascriptInterface
	public void showDialogMessage(String title,String message){
		
	}
	
	/**
	 * 显示短暂的提示
	 * @param message
	 */
	@JavascriptInterface
	public void showToastMessage(String message){
		Toast.makeText(act, message, Toast.LENGTH_LONG).show();
	}

}



/**
 * 
 * <p>
 *  多媒体提供给h5的接口
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年5月6日
 * @version： V1.0
 */
public class MediaInJavaScriptHander extends InJavaScriptHander{
	
	public MediaInJavaScriptHander(Activity act, Context context) {
		super(act, context);
	}

	/**
	 * 打开照相机
	 */
	@JavascriptInterface
	public void openCamera(){
		  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
		  act.startActivityForResult(intent, 0); 
	}
}

```
###### 2、将native对象组件注入到webview中

```
//将native对象组件注入到webview中
mgjWebView.addJavascriptInterface(new MessageInJsHander(MainActivity.this,this), "jsMessageHander");
mgjWebView.addJavascriptInterface(new MediaInJavaScriptHander(MainActivity.this,this), "jsMediaHander");
		
mgjWebView.loadUrl("http://10.0.17.118:8080/marketing/h5.jsp");
```

##### 3、在js中访问native的方法
```
//调用android打开照相机
function showCamera(){
			window.jsMediaHander.openCamera();
}
//显示短暂的提示
function showToast(){		
		window.jsMessageHander.showToast("这是有Android Api显示的提示哦。。。");
}
```

 
