package id.pilihandana.rp.com.utils;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class KotlinUtlis {
    public static void getSplash(Activity context){
        Branch.sessionBuilder(context).withCallback(branchReferralInitListener).withData(context.getIntent() != null ? context.getIntent().getData() : null).init();
    }

    public static Branch.BranchReferralInitListener branchReferralInitListener = new Branch.BranchReferralInitListener() {
        @Override
        public void onInitFinished(JSONObject linkProperties, BranchError error) {

        }
    };
}
