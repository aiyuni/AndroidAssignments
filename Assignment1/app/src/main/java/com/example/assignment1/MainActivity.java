package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.toolbox.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private String url;
    private JSONObject apiResponse;
    private JSONArray bookList;
    private ArrayAdapter<String> adapterString;
    private ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "https://www.googleapis.com/books/v1/volumes?q=harry+potter";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = null;

        try {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("the response is: " + response.toString());
                    apiResponse = response;
                    populateMainPage();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("something went wrong with volley: " + error.toString());
                }
            });
        } catch (Exception e) {
            System.out.println("something went wrong with jsonArrayRequest: " + e.toString());
        }

        requestQueue.add(jsonObjectRequest);
    }

    public void populateMainPage(){

        try {
            bookList = apiResponse.getJSONArray("items");
        } catch (Exception e){
            Log.e("conversion failed: ", e.toString());
        }

        List<String> bookTitleList = new ArrayList<String>();
        List<String> bookIdList = new ArrayList<>();


        for (int i = 0; i < bookList.length(); i++){
            try {
                bookTitleList.add(bookList.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString());
                bookIdList.add(bookList.getJSONObject(i).get("id").toString());
            } catch (Exception e){
                Log.e("for loop fail", e.toString());
            }
        }

        System.out.println("bookTitleList is: " + bookTitleList);

        adapterString = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookTitleList);
        listView1 = findViewById(R.id.list1);
        listView1.setAdapter(adapterString);

        final List<String> bookIdListFinal = bookIdList;

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, BookDetails.class);
                intent.putExtra("bookTitle", selectedItem);
                intent.putExtra("bookId", bookIdListFinal.get(position));

                startActivity(intent);
            }
        });
    }


}
