package com.jgzuke.intoxicmateandroid.uber;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jgzuke.intoxicmateandroid.R;
import com.jgzuke.intoxicmateandroid.api.UberAPIClient;
import com.jgzuke.intoxicmateandroid.api.UberCallback;
import com.jgzuke.intoxicmateandroid.model.Request;
import com.jgzuke.intoxicmateandroid.model.ProductList;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;


public class EndpointActivity extends ActionBarActivity {

    public static void start(Context context, int position, String accessToken, String tokenType) {
        Intent intent = new Intent(context, EndpointActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("access_token", accessToken);
        intent.putExtra("token_type", tokenType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int position = getIntent().getIntExtra("position", 0);
        switch (position) {
            case 1:

                UberAPIClient.getUberV1APIClient().getProducts(getAccessToken(),
                        Constants.START_LATITUDE,
                        Constants.START_LONGITUDE,
                        new UberCallback<ProductList>() {
                            @Override
                            public void success(ProductList productList, Response response) {

                                UberAPIClient.getUberV1APIClient().postRequest(getAccessToken(),
                                        productList.getProducts().get(0).getProductId(),
                                        Constants.START_LATITUDE,
                                        Constants.START_LONGITUDE,
                                        Constants.END_LATITUDE,
                                        Constants.END_LONGITUDE,
                                        new UberCallback<Request>() {
                                            @Override
                                            public void success(Request request, Response response) {
                                                setupListAdapter("request", request.toString());
                                            }
                                        });
                                setupListAdapter("products", productList.toString());
                            }
                        });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupListAdapter(String endpoint, String response) {
        List<String> options = new ArrayList<String>();
        options.add(getString(R.string.endpoint_list_header_text, endpoint));
        options.add(getString(R.string.endpoint_list_result_text, endpoint));
        options.add(response);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, options));
    }


    private String getAccessToken() {
        return getIntent().getStringExtra("token_type") + " " + getIntent().getStringExtra("access_token");
    }
}
