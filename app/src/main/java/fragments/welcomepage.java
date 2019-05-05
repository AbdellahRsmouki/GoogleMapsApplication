package fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsmouki.zed.tp3.R;
import com.rsmouki.zed.tp3.db.User;


public class welcomepage extends Fragment {


    private  static final String TAG="WelcomeActivity";


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_welcome, container, false);


        TextView msg = (TextView) view.findViewById(R.id.Message);


        String message = "Hello "+User.mainUser.getLogin()+" "+ getResources().getString(R.string.introduction_sentence);
        msg.setText(message);

        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Handling input...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 1000);

        
        return view;
    }
}