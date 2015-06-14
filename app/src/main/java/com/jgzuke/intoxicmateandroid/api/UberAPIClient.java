package com.jgzuke.intoxicmateandroid.api;

import com.jgzuke.intoxicmateandroid.BuildConfig;
import com.jgzuke.intoxicmateandroid.uber.Constants;
import com.jgzuke.intoxicmateandroid.model.PriceEstimateList;
import com.jgzuke.intoxicmateandroid.model.ProductList;
import com.jgzuke.intoxicmateandroid.model.TimeEstimateList;
import com.jgzuke.intoxicmateandroid.model.Request;

import retrofit.Callback;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Header;
import retrofit.http.Query;

public class UberAPIClient {

    private static UberAPIInterface sUberAPIService;
    private static UberEndPoint sEndPoint = new UberEndPoint(Constants.BASE_UBER_URL_V1, Constants.BASE_UBER_URL_V1_1);

    private static UberAPIInterface getUberAPIClient() {
        if (sUberAPIService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(sEndPoint)
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .build();

            sUberAPIService = restAdapter.create(UberAPIInterface.class);
        }

        return sUberAPIService;
    }

    public static UberAPIInterface getUberV1APIClient() {
        sEndPoint.setVersion(false);
        return getUberAPIClient();
    }

    public static UberAPIInterface getUberV1_1APIClient() {
        sEndPoint.setVersion(true);
        return getUberAPIClient();
    }

    public interface UberAPIInterface {

        /**
         * The Products endpoint returns information about the Uber products offered at a given
         * location. The response includes the display name and other details about each product,
         * and lists the products in the proper display order.
         *
         * @param authToken OAuth 2.0 bearer token with the profile scope.
         * @param latitude  Latitude component of location.
         * @param longitude Longitude component of location.
         * @param callback
         */
        @GET("/products")
        void getProducts(@Header("Authorization") String authToken,
                         @Query("latitude") double latitude,
                         @Query("longitude") double longitude,
                         Callback<ProductList> callback);

        /**
         * The Time Estimates endpoint returns ETAs for all products offered at a given location,
         * with the responses expressed as integers in seconds. We recommend that this endpoint be
         * called every minute to provide the most accurate, up-to-date ETAs.
         *
         * @param authToken      OAuth 2.0 bearer token or server_token
         * @param startLatitude  Latitude component.
         * @param startLongitude Longitude component.
         * @param callback
         */
        @GET("/estimates/time")
        void getTimeEstimates(@Header("Authorization") String authToken,
                              @Query("start_latitude") double startLatitude,
                              @Query("start_longitude") double startLongitude,
                              Callback<TimeEstimateList> callback);

        /**
         * The Price Estimates endpoint returns an estimated price range for each product offered
         * at a given location. The price estimate is provided as a formatted string with the full
         * price range and the localized currency symbol.
         * <p/>
         * The response also includes low and high estimates, and the ISO 4217 currency code for
         * situations requiring currency conversion. When surge is active for a particular product,
         * its surge_multiplier will be greater than 1, but the price estimate already factors in
         * this multiplier.
         *
         * @param authToken      OAuth 2.0 bearer token or server_token
         * @param startLatitude  Latitude component of start location.
         * @param startLongitude Longitude component of start location.
         * @param endLatitude    Longitude component of start location.
         * @param endLongitude   Longitude component of end location.
         * @param callback
         */
        @GET("/estimates/price")
        void getPriceEstimates(@Header("Authorization") String authToken,
                               @Query("start_latitude") double startLatitude,
                               @Query("start_longitude") double startLongitude,
                               @Query("end_latitude") double endLatitude,
                               @Query("end_longitude") double endLongitude,
                               Callback<PriceEstimateList> callback);

        //Posting code
        @POST("/requests")
        void postRequest(      @Header("Authorization") String authToken,
                               @Query("product_id") String productID,
                               @Query("start_latitude") double startLatitude,
                               @Query("start_longitude") double startLongitude,
                               @Query("end_latitude") double endLatitude,
                               @Query("end_longitude") double endLongitude,
                               Callback<Request> callback);
        


    private static class UberEndPoint implements Endpoint {

        private final String apiUrlV1, apiUrlV11;
        private boolean useV11 = false;

        private UberEndPoint(String apiUrlV1, String apiUrlV11) {
            this.apiUrlV1 = apiUrlV1;
            this.apiUrlV11 = apiUrlV11;
        }

        public void setVersion(boolean useV11) {
            this.useV11 = useV11;
        }

        @Override
        public String getUrl() {
            return useV11 ? apiUrlV11 : apiUrlV1;
        }

        @Override
        public String getName() {
            return "default";
        }
    }
}
