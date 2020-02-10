package ir.vira.ServerTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.vira.BuildConfig;
import ir.vira.CustomToast.PersianToast;
import ir.vira.R;
import ir.vira.RoomDatabase.Database.DatabaseGhazal;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Books;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.RoomDatabase.Entities.Poets;

public class ServerTransaction {

    private static RequestQueue requestQueue;
    private static String staticUrl;
    private Context context;
    private boolean isSuccessful = false;

    public ServerTransaction(Context context) {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
            staticUrl = context.getResources().getString(R.string.staticUrl);
        }
        this.context = context;
    }

    public void getDataFromServer(final Button button , final SpinKitView spinKitView , final Thread thread){
        Response.Listener<JSONObject> jsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isSuccessful = true;
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    JSONArray jsonArrayBooks = response.getJSONArray("books");
                    JSONArray jsonArrayPoets = response.getJSONArray("poets");
                    JSONArray jsonArrayPoems = response.getJSONArray("poems");
                    List<Books> books = Arrays.asList(gson.fromJson(jsonArrayBooks.toString(),Books[].class));
                    List<Poets> poets = Arrays.asList(gson.fromJson(jsonArrayPoets.toString(),Poets[].class));
                    List<Poems> poems = Arrays.asList(gson.fromJson(jsonArrayPoems.toString(),Poems[].class));
                    DatabaseTransaction databaseTransaction = new DatabaseTransaction(context);
                    databaseTransaction.insertIntoBooks(books);
                    databaseTransaction.insertIntoPoems(poems);
                    databaseTransaction.insertIntoPoets(poets);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("pref_cached" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isDataCached" , true);
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PersianToast persianToast = new PersianToast(context);
                persianToast.makeText("مشکل در ارتباط با سرور.",R.string.iran_sans_bold, Toast.LENGTH_SHORT);
                spinKitView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
            }
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,staticUrl,null,jsonObjectListener,errorListener){
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String , String> header = new HashMap<>();
                header.put("api-key", BuildConfig.API_KEY);
                return header;
            }
        };
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<JsonObjectRequest>() {
            @Override
            public void onRequestFinished(Request<JsonObjectRequest> request) {
                if (isSuccessful)
                    thread.start();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
