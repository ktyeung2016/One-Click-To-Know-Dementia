package hk.org.jccpa.dementia;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Fbshare extends Activity {

	private UiLifecycleHelper uiHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fbshare);



		if (FacebookDialog.canPresentShareDialog(getApplicationContext(), 
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			//			OpenGraphObject product = OpenGraphObject.Factory.createForPost("product");
			//			product.setProperty("title", "myTitle");
			//			product.setProperty("description", "myDescription");
			//
			//			OpenGraphAction action = GraphObject.Factory.create(OpenGraphAction.class);
			//			action.setType("dementia:feed"); //Required for OpenGraphActionDialogBuilder
			//			action.setProperty("product", product);
			//			//action.setProperty("explicitly_shared","true");                
			//
			//
			//			List<Bitmap> images = new ArrayList<Bitmap>();
			//			images.add(Global.bm);
			//
			//			FacebookDialog fbDialog = new FacebookDialog.OpenGraphActionDialogBuilder(this, action, "product")
			//			.setImageAttachmentsForAction(images)
			//			.build(); 
			//			//			fbDialog.present();
			//			fbDialog.present();//);uiHelper.trackPendingDialogCall(
			//			if(Global.bm!=null){
			//				ArrayList<Bitmap> temp=new ArrayList<Bitmap>();
			//				temp.add(Global.bm);
			//				FacebookDialog shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(this, action, "ecthk")
			////				.setImageAttachmentsForAction(temp, true)
			//
			//				.build();
			//				uiHelper.trackPendingDialogCall(shareDialog.present());
			//			}

			//			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this).setApplicationName("Dementia") 
			//					.setLink("http://www.ecthk.net").setName("econnective").setCaption("yoyo").setDescription("heyhey")
			//					.build();
			//			uiHelper.trackPendingDialogCall(shareDialog.present());
			//			OpenGraphObject recipe = OpenGraphObject.Factory.createForPost("dementia:recipe");
			//			recipe.setProperty("title", "Shrimp Curry");
			////			recipe.setProperty("image", "https://example.com/cooking-app/meal/Shrimp-Curry.html");
			//			recipe.setProperty("description", "...recipe text...");
			//
			//			Bitmap images =Global.bm;
			//			List<Bitmap> images1 = new ArrayList<Bitmap>();
			//			images1.add(images);
			//
			//			OpenGraphAction action = GraphObject.Factory.create(OpenGraphAction.class);
			//			action.setProperty("recipe", recipe);
			//			action.setType("message");
			//			FacebookDialog shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(this, action, "recipe")
			////			    .setImageAttachmentsForAction( images1, true)
			//			    .build();
			//			uiHelper.trackPendingDialogCall(shareDialog.present());
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
			.setLink("https://www.adcarer.com/index.php").setName("「腦退化一按知」").setCaption("JCCAP").setDescription(Global.sharemsg).setPicture(Global.link+"ic_launcher.png")
			.build();
			uiHelper.trackPendingDialogCall(shareDialog.present());
		} else {
			if (Session.getActiveSession() == null || !Session.getActiveSession().isOpened()) {
				Session.openActiveSession(Fbshare.this, true, callback);
			} else { 
				publishFeedDialog(); 
			} 
		}
	}
	private Session.StatusCallback callback = new Session.StatusCallback() {

		@Override 
		public void call(Session session, SessionState state, Exception exception) {

			if (state.isOpened() ) {
				publishFeedDialog(); 
			} 
		} 
	}; 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
			@Override
			public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
				Log.e("Activity", String.format("Error: %s", error.toString()));
			}

			@Override
			public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
				Log.i("Activity", "Success!");
				finish();
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	private void publishFeedDialog() {
		Bundle params = new Bundle();

		//		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
		//		.setLink("https://www.adcarer.com/index.php").setName("「腦退化一按知」")
		//		.setCaption("JCCAP").setDescription(Global.sharemsg).setPicture(Global.link+"ic_launcher.png")
		//		.build();

		params.putString("name", "「腦退化一按知」");
		params.putString("caption", "JCCAP");
		params.putString("description", Global.sharemsg);
		params.putString("link", "https://www.adcarer.com/index.php");
		params.putString("picture", Global.link+"ic_launcher.png");

		WebDialog feedDialog = (
				new WebDialog.FeedDialogBuilder(Fbshare.this,
						Session.getActiveSession(),
						params))
						.setOnCompleteListener(new OnCompleteListener() {



							@Override
							public void onComplete(Bundle values, FacebookException error) {
								// TODO Auto-generated method stub
								if (error == null) {
									// When the story is posted, echo the success
									// and the post Id.
									final String postId = values.getString("post_id");
									if (postId != null) {
										Toast.makeText(Fbshare.this,
												"Posted story, id: "+postId,
												Toast.LENGTH_SHORT).show();
										finish();
									} else {
										// User clicked the Cancel button
										Toast.makeText(Fbshare.this.getApplicationContext(), 
												"Publish cancelled", 
												Toast.LENGTH_SHORT).show();
										finish();
									}
								} else if (error instanceof FacebookOperationCanceledException) {
									// User clicked the "x" button
									Toast.makeText(Fbshare.this.getApplicationContext(), 
											"Publish cancelled", 
											Toast.LENGTH_SHORT).show();
									finish();
								} else {
									// Generic, ex: network error
									Toast.makeText(Fbshare.this.getApplicationContext(), 
											"Error posting story", 
											Toast.LENGTH_SHORT).show();
									finish();
								}
							}

						})
						.build();
		feedDialog.show();
	}
}