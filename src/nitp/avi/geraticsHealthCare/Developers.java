package nitp.avi.geraticsHealthCare;

import nitp.avadhesh.corona.R;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Developers extends Fragment {
	Button abhi,avadh,shubh,vivek,sunil;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.developerfrag, container, false);
		abhi=(Button) rootView.findViewById(R.id.button10);
		avadh=(Button) rootView.findViewById(R.id.button11);
		shubh=(Button) rootView.findViewById(R.id.button14);
		vivek=(Button) rootView.findViewById(R.id.button13);
		sunil=(Button) rootView.findViewById(R.id.button12);
		
		abhi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url="https://in.linkedin.com/in/kumar-abhishek-6bb1b737";
				 Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));  
	             startActivity(intent);  
				
			}
		});
avadh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url="https://in.linkedin.com/in/avadhesh-singh-14985280";
				 Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));  
	             startActivity(intent);  
			}
		});
sunil.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String url="https://in.linkedin.com/in/sunil-kumar-gupta-ba8932115";
		 Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));  
        startActivity(intent);  
	}
});
vivek.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String url="https://in.linkedin.com/in/vivek-raj-06babaa4";
		 Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));  
        startActivity(intent);  
	}
});
shubh.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String url="https://in.linkedin.com/in/shubhnkar-upadhyay-91bb2474";
		 Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));  
        startActivity(intent);  
	}
});
		
	    return rootView;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
}
