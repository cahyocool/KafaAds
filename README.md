# KafaAds Library Admob, AppLovin, UnityAds And FB FAN for Android

## Table of Contents
+ [About](#about)
+ [Getting Started](#getting_started)
+ [Usage](#usage)
+ [Contributing](../CONTRIBUTING.md)

## About
This library help to you display ads from Admob, AppLovin or FAN, you can use it for simple display Ads to your app

## Getting Started
This library support Admob, AppLovin and FAN provider
- Admob
  - Admob SDK 21.4.0
  - Lifecycle-runtime 2.2.0
  - Lifecycle-compiler 2.2.0
  - Lifecycle-Extensions 2.2.0
- FAN
  - FAN SDK 5.10.1
- AppLovin
  - SDK 11.6.1 
  - lifecycle-process:2.5.1
- UnityAds
  - unity-ads:4.5.0
- Mediation
  - AppLovin  
- Retrofit 2.7.1
- Gson 2.9.0
- Glide 4.13.1

View Support Ads
- Admob
  - Banner
  - Interstitial
  - Native
  - OpenApp Ads
  - Rewards
- AppLovin
  - Banner
  - Interstitial
  - Native
  - OpenApp Ads
  - Rewards
- UnityAds
  - Banner
  - Interstitial
  - Rewards  
- FAN
  - Banner
  - Interstitial
  - OpenApp Ads
  - Rewards
- Support Main / Backup Ads
- Intervals Interstitial
- Intervals Rewards  
  
### Prerequisites
What things you need to install the software and how to install them, use Jitpack

```Groove
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
### Installing

A step by step series of examples that tell you how to get a development env running.

Add the dependency
[![](https://jitpack.io/v/cahyocool/KafaAds.svg)](https://jitpack.io/#cahyocool/KafaAds)
```Groove
dependencies {
	  implementation 'com.github.cahyocool:KafaAds:2.1.5'
}
```

That's it!

## Usage
To use the library for display ads are simple, let's code.
##### Declare class
```java
private KafaAds ads;
```
The library comes with the model, so you can store your data into the model and pass it into your app
there are support local json or remote json ads.
below this setting json example for ads
```json
{
	"status":"1",
	"interval-ads-inters":"2",
	"interval-ads-rewa":"1",
	"interval-ads-native":"0",
	"main-ads":"Admob",
	"backup-ads":"Applovin",
	"backup-status":"0",
	"unity-test-mode":true,
	"appid":"ca-app-pub-3940256099942544~3347510000",
	"banner":"ca-app-pub-3940256099942544/6300978111",
	"inters":"ca-app-pub-3940256099942544/1033173712",
	"rewa":"ca-app-pub-3940256099942544/5224354917",
	"native":"ca-app-pub-3940256099942544/2247696110",
	"openapp":"ca-app-pub-3940256099942544/3419835294",
	"backup-appid":"<YOUR SDK KEY APPLOVIN OR FB FAN KEY>",
    "backup-banner":"<YOUR BANNER ID APPLOVIN OR FB FAN>",
    "backup-inters":"<YOUR INTERSTITIAL ID APPLOVIN OR FB FAN>",
    "backup-rewa":"<YOUR REWARDS ID APPLOVIN OR FB FAN>",
	"backup-native":"<OPTIONAL - STILL ON PROGRESS>",
    "backup-openapp":"<YOUR OPENAPP ID APPLOVIN OR FB FAN>",
	"app_suspend":false,
	"link_app_redirect":"<LINK TO REDIRECT IF APP SUSPEND, APP SUSPEN MUST BE SET TO TRUE>",
	"link-app-privacy":"YOUR LINK PRIVACY APP"
}
```
and now in your code, you can write like below
```java
KafaModel kafaModel = new KafaModel();
kafaModel.set_appid(<Your-AppId> or from json object ex:jo.optString(KafaConst.APPID));
kafaModel.set_banner(<Your-BannerId> or from json object ex:jo.optString(KafaConst.BANNER));
...<etc>
KafaAds.setAds(kafaModel);
```
Now you just call the model and pass it into your function or class
```java
KafaAds.getAds().get_appid()
KafaAds.getAds().get_banner()
..<etc>
```
##### Initialize Ads
To initialize ads, you can call the function like below<br/>
note : since SDK 20+ version, Admob cannot passing APPID on initialize
```java
KafaAds.initAd(activity);
```
##### Banner
This example to display BannerAd
```java
/* Banner
 * AdType - BANNER / HALF_BANNER
 */
//for Admob 
ads = new KafaAds.Builder(context)
    .setContainer(findViewById(R.id.lyt_banner))
    .setAd(new Ad(AdName.ADMOB, AdType.BANNER, KafaAds.getAds().get_banner()))
    .build();
ads.load();

//for AppLovin
//AppLovin banner default are BOTTOM place, but you can setting it to custom Place, like below code,
//KafaAds.setMaxBannerCustom(true);
//KafaAds.setMaxBanner(ViewGroup.LayoutParams.MATCH_PARENT,
//	activity.getResources().getDimensionPixelSize(com.kafaads.kafaadslibrary.R.dimen.banner_height),
//  Gravity.TOP);
ads = new KafaAds.Builder(context)
    .setContainer(findViewById(R.id.lyt_banner))
    .setAd(new Ad(AdName.APPLOVIN, AdType.BANNER, "YOUR-AppLovin-BANNERID"))
    .build();
ads.load();

//for UnityAds
ads = new KafaAds.With(context)
    .setContainer(activity.findViewById(R.id.lyt_banner))
    .setAd(new Ad(AdName.UNITY, AdType.BANNER, KafaAds.getAds().get_backup_banner()))
    .build();
ads.load();

//for FAN
ads = new KafaAds.Builder(context)
    .setContainer(findViewById(R.id.lyt_banner))
    .setAd(new Ad(AdName.FACEBOOK, AdType.BANNER, "YOUR-FAN-BANNERID"))
    .build();
ads.load();
```
##### Interstitial
This example to display InterstitialAd
```java
/* Interstitial
 * 
 */
//for Admob
ads = new KafaAds.With(context)
	.setAd(new Ad(AdName.ADMOB, AdType.INTERSTITIAL, KafaAds.getAds().get_interstitial()))
	.setOnInterstitialAdLoadListener(new OnInterstitialAdLoadListener() {
		@Override
		public void onAdLoaded() {
			ads.showInterstitial();
			Log.d("showInter", "iklan inter muncul");
		}

		@Override
		public void onAdFailedToLoad() {

		}

		@Override
		public void onAdClosed() {
			Log.d("closedInter", "iklan inter closed");
		}
	})
	.build();
ads.load(); 

//for AppLovin
ads = new KafaAds.With(context)
	.setAd(new Ad(AdName.APPLOVIN, AdType.INTERSTITIAL, KafaAds.getAds().get_backup_interstitial()))
	.setOnMaxInterstitialAdLoadListener(new OnMaxInterstitialAdLoadListener() {
		@Override
		public void onAdExpanded() {

		}

		@Override
		public void onAdCollapsed() {

		}

		@Override
		public void onAdLoaded() {
			ads.showMaxInterstitial();
			Log.d("showInter", "iklan max inter muncul");
		}

		@Override
		public void onAdDisplayed() {

		}

		@Override
		public void onAdHidden() {
			Log.d("closedInter", "iklan max inter closed");
		}

		@Override
		public void onAdClicked() {

		}

		@Override
		public void onAdFailedToLoad() {

		}

		@Override
		public void onAdDisplayFailed() {

		}
	})
	.build();
ads.load();

//for UnityAds
IUnityAdsShowListener showListener;
showListener = new IUnityAdsShowListener() {
	@Override
	public void onUnityAdsShowFailure(String placementId, UnityAdsShowError unityAdsShowError, String message) {

	}

	@Override
	public void onUnityAdsShowStart(String placementId) {
		Log.d("showInter", "iklan inter muncul " + placementId);
	}

	@Override
	public void onUnityAdsShowClick(String placementId) {
		Log.d("showInter", "iklan inter klik " + placementId);
	}

	@Override
	public void onUnityAdsShowComplete(String placementId) {
		Log.d("showComplete", "iklan inter complete " + placementId);
	}
};
ads = new KafaAds(context);
ads.unityShowInterstitial(KafaAds.getAds().get_backup_interstitial(), showListener);
```
##### Rewards
This example to display RewardsAd
```java
/* RewardsAd
 * 
 */
//for Admob
ads = new KafaAds.With(context)
	.setAd(new Ad(AdName.ADMOB, AdType.REWARD, KafaAds.getAds().get_reward()))
	.setOnRewardAdLoadListener(new OnRewardAdLoadListener() {
		@Override
		public void onAdLoaded() {
			ads.showReward();
			Log.d("showRewa", "iklan rewa muncul");
		}

		@Override
		public void onAdFailedToLoad() {

		}

		@Override
		public void onAdClosed() {
			Log.d("closedRewa", "iklan rewa closed");
		}
	})
	.build();
ads.load();

//for AppLovin
ads = new KafaAds.With(context)
	.setAd(new Ad(AdName.APPLOVIN, AdType.REWARD, KafaAds.getAds().get_backup_reward()))
	.setOnMaxRewardAdLoadListener(new OnMaxRewardAdLoadListener() {
		@Override
		public void onAdExpanded() {

		}

		@Override
		public void onAdCollapsed() {

		}

		@Override
		public void onAdLoaded() {
			ads.showMaxReward();
			Log.d("showReward", "iklan max reward muncul");
		}

		@Override
		public void onAdDisplayed() {

		}

		@Override
		public void onAdHidden() {
			Log.d("closedInter", "iklan max reward closed");
		}

		@Override
		public void onAdClicked() {

		}

		@Override
		public void onAdFailedToLoad() {

		}

		@Override
		public void onAdDisplayFailed() {

		}
	})
	.build();
ads.load();

//for UnityAds
IUnityAdsShowListener showListener;
showListener = new IUnityAdsShowListener() {
	@Override
	public void onUnityAdsShowFailure(String placementId, UnityAdsShowError unityAdsShowError, String message) {

	}

	@Override
	public void onUnityAdsShowStart(String placementId) {
		Log.d("showReward", "iklan rewa muncul " + placementId);
	}

	@Override
	public void onUnityAdsShowClick(String placementId) {
		Log.d("showClick", "iklan rewa klik " + placementId);
	}

	@Override
	public void onUnityAdsShowComplete(String placementId) {
		Log.d("showComplete", "iklan rewa complete " + placementId);
	}
};
ads = new KafaAds(context);
ads.unityShowReward(KafaAds.getAds().get_backup_reward(), showListener);
```
##### Native
This example to display NativeAd
```java
/* NativeAd
 * 
 */
//for Admob
NativeTemplateStyle styles = new
	NativeTemplateStyle.Builder().withMainBackgroundColor(null).build();
TemplateView templateView = activity.findViewById(R.id.nativeTemplateView);
templateView.setVisibility(View.VISIBLE);
templateView.setStyles(styles);
ads = new KafaAds(context);
ads.showNative(AdName.ADMOB, KafaAds.getAds().get_native(), templateView);

//for AppLovin
FrameLayout frameLayout = activity.findViewById(R.id.max_native_ad);
frameLayout.setVisibility(View.VISIBLE);
ads = new KafaAds(context);
ads.showMaxNativeTemplate(frameLayout, KafaAds.getAds().get_backup_native()); 
``` 
##### OpenApp
This example to display OpenAppAd
```java
/* OpenAppAd
 * 
 */
KafaAds.initOpenAd(this); 
``` 
I will continue to develop this library and I will be very happy if anyone wants to help develop this library.

Thank you for using this library.

You can also help me with some donations to make me more enthusiastic about developing this library.
please click [Buy me a coffee](https://www.buymeacoffee.com/spylight)

## Authors
Made with ❤ by [@spylight-zz](https://www.github.com/spylight-zz)

[Buy me a coffee](https://www.buymeacoffee.com/spylight)















