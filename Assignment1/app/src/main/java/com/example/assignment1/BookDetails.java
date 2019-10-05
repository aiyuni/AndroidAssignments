package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookDetails extends AppCompatActivity {

    public static String baseUrl = "https://www.googleapis.com/books/v1/volumes/" ;
    private String url;
    private JSONObject volumeInfo;
    private String imageUrl;
    private JSONObject apiResponse;
    private ImageButton bookImage;
    private TextView titleText;
    private TextView authorsText;
    private TextView publisherText;
    private TextView publisherDateText;
    private TextView descriptionText;
    private TextView isbnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Bundle extras = getIntent().getExtras();

        if (extras == null){
            System.out.println("no value passed??!");
        }
        else {
            System.out.println("the book is: " + extras.getString("bookTitle"));
            System.out.println("the book id is: " + extras.get("bookId"));
            getBookDetails(extras.getString("bookId"));
        }
    }

    private void getBookDetails(String bookId){

        url = baseUrl + bookId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = null;

        try {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("the response in book details is: " + response.toString());
                    apiResponse = response;
                    populateBookDetailsPage();
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

    private void populateBookDetailsPage(){
        bookImage = (ImageButton)findViewById(R.id.bookImageButton);
        titleText = findViewById(R.id.bookTitleText);
        authorsText = findViewById(R.id.bookAuthorsText);
        publisherText = findViewById(R.id.publisherText);
        publisherDateText = findViewById(R.id.publishDateText);
        descriptionText = findViewById(R.id.descriptionText);
        isbnText = findViewById(R.id.isbnText);

        //apiResponse:{"kind":"books#volume","id":"7HgwCgAAQBAJ","etag":"xtR5tHBp+GE","selfLink":"https:\/\/www.googleapis.com\/books\/v1\/volumes\/7HgwCgAAQBAJ","volumeInfo":{"title":"Harry Potter and the Classical World","subtitle":"Greek and Roman Allusions in J.K. Rowling's Modern Epic","authors":[

        try {
            volumeInfo = apiResponse.getJSONObject("volumeInfo");

            /*Get image */
            imageUrl = volumeInfo.getJSONObject("imageLinks").get("smallThumbnail").toString();
            if (imageUrl.contains("http://")){
                imageUrl = imageUrl.replace("http://", "https://");
            }
            Picasso.get().load(imageUrl).into(bookImage);

            /*Get title*/
            titleText.setText("Title: " + volumeInfo.get("title").toString());

            /*Get author(s) */
            List<String> authorList = new ArrayList<String>();
            authorsText.setText("Author(s): ");
            JSONArray authorsJSONArray = volumeInfo.getJSONArray("authors");
            for (int i = 0; i < authorsJSONArray.length(); i++){
                authorList.add(authorsJSONArray.get(i).toString());
                authorsText.append(authorList.get(i));
                if (!(i == authorsJSONArray.length() - 1)){
                    authorsText.append(", ");
                }
            }

            /*Get publisher */
            publisherText.setText("Publisher: " + volumeInfo.get("publisher").toString());

            /*Get publishing date */
            publisherDateText.setText("Publish Date: " + volumeInfo.get("publishedDate").toString());

            /*Get description */
            try {
                String rawDescription = volumeInfo.get("description").toString();
                descriptionText.setText("Book Description: " + Html.fromHtml(rawDescription).toString());
                descriptionText.setMovementMethod(new ScrollingMovementMethod());
            } catch (JSONException e){
                descriptionText.setText("Book Description: No description found.");
            }

            /*get ISBN10 number */
            isbnText.setText("ISBN10: " + volumeInfo.getJSONArray("industryIdentifiers").getJSONObject(0).get("identifier"));

        } catch (Exception e){
            System.out.println("Something went wrong: " + e.toString());
        }

    }
}
