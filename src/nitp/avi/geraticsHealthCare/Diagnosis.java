package nitp.avi.geraticsHealthCare;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import nitp.avadhesh.corona.R;
import nitp.avi.geraticsHealthCare.EnterSymptoms.ExecuteTask;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Diagnosis extends Fragment {
	View rootView;
	
	ImageButton speak;
    ListView options;
    ArrayList<String> results;
    MultiAutoCompleteTextView text1;
    String[] list;
    Button submit;
    String[] value ;
    
    // flag for Internet connection status
    Boolean isInternetPresent = false; 
    // Connection detector class
    ConnectionDetector cd;
  
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 rootView = inflater.inflate(R.layout.diagnosisfragment, container, false);
		
		  text1=(MultiAutoCompleteTextView)rootView.findViewById(R.id.multiAutoCompleteTextView1);
			speak = (ImageButton)rootView. findViewById(R.id.imageButton1);
	        options = (ListView)rootView. findViewById(R.id.listView1);
	        submit=(Button)rootView. findViewById(R.id.button1);
	        
	        list= getResources().getStringArray(R.array.symptoms);
	        ArrayAdapter <String>  adapter1 = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,list);
	       text1.setThreshold(1);
	       text1.setAdapter(adapter1);
	        text1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer()); 
	        
	        ///////////for checking internet connectivity status...../////
	        cd = new ConnectionDetector(rootView.getContext());
	        isInternetPresent = cd.isConnectingToInternet();
	        if(! isInternetPresent){
	        	Toast.makeText(rootView.getContext(),"Sorry..!!No internet connection.This feature will not work.",Toast.LENGTH_SHORT).show();
	        }
		 
	      //submit button work///////////////////////////////////////
	        submit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try{
						  String delims = ",";
						  String t=text1.getText().toString();
						  StringTokenizer st = new StringTokenizer(t);  
						  value = t.split(delims);					  
						//Toast.makeText(rootView.getContext(), String.valueOf(value.length), Toast.LENGTH_SHORT).show();					
						 new ExecuteTask().execute(t.trim());
				}catch(Exception e){ Toast.makeText(rootView.getContext(), "Something is wrong. Try Again.", Toast.LENGTH_SHORT).show();}
				
					
				}
			});
		 
	      ///input from microphone////////////////////////////////	        
	        speak.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	                // This are the intents needed to start the Voice recognizer
	                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
	                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");	 
	                startActivityForResult(i, 1010);
	            }
	        });
	 options.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			   String selected =(String) (options.getItemAtPosition(position));
	           text1.append(selected+",");
	          options.setVisibility(options.INVISIBLE); 	        

		}
	});
      
	 if (savedInstanceState != null) {
         results = savedInstanceState.getStringArrayList("results");

         if (results != null)
             options.setAdapter(new ArrayAdapter<String>(rootView.getContext(),
                     android.R.layout.simple_list_item_1, results));
         }
     	
	    return rootView;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        // retrieves data from the VoiceRecognizer
        if (requestCode == 1010 && resultCode == Activity.RESULT_OK) {
            results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter2=      new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_list_item_1, results);
            options.setAdapter(adapter2);
             
        }
 
        super.onActivityResult(requestCode, resultCode, data);
    }
	
	
	 @Override
	    public void onSaveInstanceState(Bundle outState) {
	        // This should save all the data so that when the phone changes
	        // orientation the data is saved
	        super.onSaveInstanceState(outState);
	      //  outState.putStringArrayList("results", results);
	    }
	    
	
	 // webservice rest api code
    class ExecuteTask extends AsyncTask<String, Integer, String>
    {

		@Override
		protected String doInBackground(String... params) {
			
			String res=PostData(params);
			
			return res;
		}
		
		@Override
		protected void onPostExecute(String result) {
		
		//progess_msz.setVisibility(View.GONE);
		//Toast.makeText(getApplicationContext(), result, 3000).show();
		 StringTokenizer st = new StringTokenizer(result);  
		  String[] dname = result.split(",");
		  List asList = Arrays.asList(dname);		 
          Set<String> mySet = new HashSet<String>(asList);
          String []k=new String[mySet.size()];
          int j=0;
          
          for(int i=(value.length-1);i>0;i--){
          for(String s: mySet){          
           if(i==Collections.frequency(asList,s)) {
        	  
        	   if(i>1&&i==(value.length-1)){     k[j]=s+"**"; j++; }
        	   else if(i>1&&i<(value.length-1)){     k[j]=s+"*"; j++;}
        	   else{   k[j]=s; j++; }
           
           }}
          }
          
		  ShareData.data().webResultDisease=k;
		  ShareData.data().Diseasename="Hypertension";
		  
		 Intent intent=new Intent(getActivity(),Diagnosis_Result.class);
		  startActivity(intent);
		  
		}
    	
    }

public String PostData(String[] valuse) {
	String s="";
	try
	{
	HttpClient httpClient=new DefaultHttpClient();
	HttpPost httpPost=new HttpPost(ShareData.data().hostAddress+"/GeriatricsWebApp/web/andiProcess.jsp");
	
	List<NameValuePair> list=new ArrayList<NameValuePair>();
	list.add(new BasicNameValuePair("name", valuse[0]));

	httpPost.setEntity(new UrlEncodedFormEntity(list));
    HttpResponse httpResponse=	httpClient.execute(httpPost);

    HttpEntity httpEntity=httpResponse.getEntity();
    s= readResponse(httpResponse);

	}
	catch(Exception exception) 	{}
	return s;

	
}
public String readResponse(HttpResponse res) {
	InputStream is=null; 
	String return_text="";
	try {
		is=res.getEntity().getContent();
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
		String line="";
		StringBuffer sb=new StringBuffer();
		while ((line=bufferedReader.readLine())!=null)
		{
		sb.append(line);
		}
		return_text=sb.toString();
	} catch (Exception e)
	{
		
	}
	return return_text;
	
}
    
}
