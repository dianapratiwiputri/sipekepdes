package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Berita extends AppCompatActivity {
    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<PostNews>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<PostNews>>() {
            @Override
            public void onResponse(Call<List<PostNews>> call, Response<List<PostNews>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                List<PostNews> posts = response.body();

                for (PostNews post : posts){
                    String content = "";
                    content += "ID: "+post.getId()+"\n";
                    content += "User ID: "+post.getUserId()+"\n";
                    content += "Tittle: "+post.getTitle()+"\n";
                    content += "Text: "+post.getText()+"\n\n";

                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<PostNews>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
