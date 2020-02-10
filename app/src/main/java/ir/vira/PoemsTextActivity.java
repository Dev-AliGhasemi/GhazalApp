package ir.vira;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.LayoutDirection;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import ir.vira.Adapters.AdapterSpinner;
import ir.vira.CustomToast.PersianToast;
import ir.vira.DownlodManager.DownloadOperation;
import ir.vira.MediaController.MediaController;
import ir.vira.Models.ModelFonts;
import ir.vira.Network.NetworkState;
import ir.vira.Receivers.ConnectivityBroadReceiver;
import ir.vira.Receivers.DownloadBroadCastReceivers;
import ir.vira.RoomDatabase.DatabaseTransaction.DatabaseTransaction;
import ir.vira.RoomDatabase.Entities.Poems;
import ir.vira.Utils.Utils;

public class PoemsTextActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView textViewTitle, textViewMainText, textViewTime, textViewTitleToolbar;
    private Toolbar toolbar;
    private Spinner spinner;
    private ImageView imageViewDownload, imageViewBookmark, imageViewReplay, imageViewPlay;
    private SeekBar seekBar;
    private Utils utils;
    private Poems poems;
    private static final int MAX_SIZE_FONT = 50, MIN_SIZE_FONT = 20;
    private static int font, fontSizeTitle, fontSizeMain;
    private static boolean isLightMode;
    private CardView cardView;
    private AdapterSpinner adapterSpinner;
    private MediaController mediaController;
    private boolean isPLay = false;
    private DatabaseTransaction databaseTransaction;
    private DownloadOperation downloadOperation;
    private DownloadBroadCastReceivers downloadBroadCastReceivers;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSettingCacheAndCheckIsLightMode()) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
            setContentView(R.layout.activity_poems_text_light);
        } else {
            getWindow().setStatusBarColor(Color.parseColor("#777777"));
            setContentView(R.layout.activity_poems_text_dark);
        }
        poems = (Poems) getIntent().getSerializableExtra("poem");
        activityInit();
        mediaController = new MediaController(imageViewPlay, seekBar, textViewTime);
        ConnectivityBroadReceiver connectivityBroadReceiver = new ConnectivityBroadReceiver(mediaController);
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityBroadReceiver, intentFilter);
        downloadOperation = new DownloadOperation(poems.getVoice(), poems, this, imageViewDownload);
        downloadBroadCastReceivers = new DownloadBroadCastReceivers(this, poems, imageViewDownload);
        registerReceiver(downloadBroadCastReceivers, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private boolean getSettingCacheAndCheckIsLightMode() {
        SharedPreferences sharedPreferences = getSharedPreferences("pref_cached", MODE_PRIVATE);
        isLightMode = sharedPreferences.getBoolean("isLightMode", true);
        font = sharedPreferences.getInt("font", 0);
        fontSizeMain = sharedPreferences.getInt("fontSizeMain", 30);
        fontSizeTitle = sharedPreferences.getInt("fontSizeTitle", 35);
        return isLightMode;
    }

    private void activityInit() {
        utils = new Utils(this);
        utils.changeLayoutDirection(LayoutDirection.LTR);
        databaseTransaction = new DatabaseTransaction(this);
        toolbar = findViewById(R.id.poems_text_toolbar);
        textViewTime = findViewById(R.id.poems_text_times);
        imageViewBookmark = findViewById(R.id.poems_text_image_bookmark);
        imageViewDownload = findViewById(R.id.poems_text_image_download);
        if (downloadBroadCastReceivers != null) {
            downloadBroadCastReceivers.setImageViewDownload(imageViewDownload);
        }
        imageViewPlay = findViewById(R.id.poems_text_image_play);
        imageViewReplay = findViewById(R.id.poems_text_image_reply);
        cardView = findViewById(R.id.poems_text_card_control);
        seekBar = findViewById(R.id.poems_text_seek_bar);
        textViewTitleToolbar = toolbar.findViewById(R.id.toolbar_book_poet_name);
        textViewTitleToolbar.setVisibility(View.GONE);
        textViewTitle = findViewById(R.id.poems_text_title);
        textViewMainText = findViewById(R.id.poems_text_main_text);
        textViewMainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizeMain);
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSizeTitle);
        spinner = findViewById(R.id.poems_text_container_setting_pen);
        spinner.setOnItemSelectedListener(this);
        utils.setFonts(textViewMainText, R.string.iran_sans_bold);
        utils.setFonts(textViewTitle, R.string.iran_sans_bold);
        textViewTitle.setText(poems.getName());
        textViewMainText.setText(poems.getText());
        if (isLightMode)
            utils.setToolbar(toolbar, "", true, true, this, LayoutDirection.LTR, R.drawable.ic_arrow_dark);
        else
            utils.setToolbar(toolbar, "", true, true, this, LayoutDirection.LTR, R.drawable.ic_arrow_light);

        if (adapterSpinner == null) {
            ArrayList<ModelFonts> modelFonts = new ArrayList<>();
            ModelFonts sansFont = new ModelFonts("سنس", R.mipmap.sample_sans);
            ModelFonts nazaninFont = new ModelFonts("نازنین", R.mipmap.sample_nazanin);
            ModelFonts nastalighFont = new ModelFonts("نستعلیق", R.mipmap.sample_nastaligh);
            modelFonts.add(sansFont);
            modelFonts.add(nazaninFont);
            modelFonts.add(nastalighFont);
            AdapterSpinner adapterSpinner = new AdapterSpinner(this, 0, modelFonts, isLightMode);
            spinner.setAdapter(adapterSpinner);
        }
        if (poems.getVoice().length() != 0) {
            if (poems.getAddressDownloadedVoice() != null)
                imageViewDownload.setImageResource(R.drawable.ic_check_circle);
        } else
            cardView.setVisibility(View.GONE);
        if (poems.isBookmark())
            imageViewBookmark.setImageResource(R.drawable.ic_bookmark);

    }

    public void poemsTextClickListener(View view) {
        switch (view.getId()) {
            case R.id.poems_text_container_setting_decrease:
                if (textViewMainText.getTextSize() >= MIN_SIZE_FONT) {
                    textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textViewTitle.getTextSize() - 2);
                    textViewMainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textViewMainText.getTextSize() - 2);
                    fontSizeTitle = (int) textViewTitle.getTextSize();
                    fontSizeMain = (int) textViewMainText.getTextSize();
                }
                break;
            case R.id.poems_text_container_setting_increase:
                if (textViewMainText.getTextSize() <= MAX_SIZE_FONT) {
                    textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textViewTitle.getTextSize() + 2);
                    textViewMainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textViewMainText.getTextSize() + 2);
                    fontSizeTitle = (int) textViewTitle.getTextSize();
                    fontSizeMain = (int) textViewMainText.getTextSize();
                }
                break;
            case R.id.poems_text_container_setting_mode:
                mediaController.replay();
                mediaController.stopMusic();
                isPLay = false;
                if (isLightMode) {
                    isLightMode = false;
                    getWindow().setStatusBarColor(Color.parseColor("#777777"));
                    setContentView(R.layout.activity_poems_text_dark);
                    activityInit();
                } else {
                    isLightMode = true;
                    getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
                    setContentView(R.layout.activity_poems_text_light);
                    activityInit();
                }
                break;
            case R.id.poems_text_image_download:
                downloadVoice();
                break;
            case R.id.poems_text_image_bookmark:
                if (poems.isBookmark()) {
                    databaseTransaction.updateBookmark(poems, false);
                    poems.setBookmark(false);
                    imageViewBookmark.setImageResource(R.drawable.ic_bookmark_border);
                } else {
                    databaseTransaction.updateBookmark(poems, true);
                    poems.setBookmark(true);
                    imageViewBookmark.setImageResource(R.drawable.ic_bookmark);
                }
                break;
            case R.id.poems_text_image_play:
                PersianToast persianToast = new PersianToast(this);
                mediaController.setImageView(imageViewPlay);
                mediaController.setSeekBar(seekBar);
                mediaController.setTextView(textViewTime);
                File fileDir = new File(poems.getVoice());
                fileDir = new File(Environment.getExternalStorageDirectory(), "Ghazal/" + fileDir.getName());
                if (fileDir.exists()) {
                    poems.setAddressDownloadedVoice(fileDir.getPath());
                    imageViewDownload.setImageResource(R.drawable.ic_check_circle);
                    databaseTransaction.updateAddressesDownload(poems);
                }
                if (poems.getAddressDownloadedVoice() == null) {
                    if (NetworkState.isNetworkActive(this)) {
                        if (!isPLay) {
                            mediaController.startMusic(poems.getVoice(), this);
                            isPLay = true;
                        } else {
                            mediaController.stopMusic();
                            isPLay = false;
                        }
                    } else {
                        persianToast.makeText("شما به اینترنت متصل نیستید.", R.string.iran_sans_bold, Toast.LENGTH_LONG);
                    }
                } else {
                    if (utils.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, "لطفا مجوز دسترسی نوشتن در حافظه را فعال کنید.", REQUEST_CODE_READ_EXTERNAL_STORAGE)) {
                        File file = new File(poems.getAddressDownloadedVoice());
                        if (file.exists()) {
                            if (!isPLay) {
                                mediaController.startMusic(poems.getAddressDownloadedVoice(), this);
                                isPLay = true;
                            } else {
                                mediaController.stopMusic();
                                isPLay = false;
                            }
                        } else {
                            persianToast.makeText("این فایل مختوش شده است.", R.string.iran_sans_bold, Toast.LENGTH_LONG);
                            imageViewDownload.setImageResource(R.drawable.ic_file_download);
                            poems.setAddressDownloadedVoice(null);
                            databaseTransaction.updateAddressesDownload(poems);
                        }
                    }
                }
                break;
            case R.id.poems_text_image_reply:
                mediaController.replay();
                break;
        }
    }

    private void downloadVoice() {
        PersianToast persianToast = new PersianToast(this);
        File file = new File(poems.getVoice());
        file = new File(Environment.getExternalStorageDirectory(), "Ghazal/" + file.getName());
        if (file.exists()) {
            if (poems.getAddressDownloadedVoice() == null) {
                poems.setAddressDownloadedVoice(file.getPath());
                databaseTransaction.updateAddressesDownload(poems);
            }
            imageViewDownload.setImageResource(R.drawable.ic_check_circle);
            persianToast.makeText("شما قبلا این فایل را دانلود کردید.", R.string.iran_sans_bold, Toast.LENGTH_LONG);
        } else {
            downloadOperation.doDownload();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaController.stopMusic();
        SharedPreferences sharedPreferences = getSharedPreferences("pref_cached", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLightMode", isLightMode);
        editor.putInt("font", font);
        editor.putInt("fontSizeMain", fontSizeMain);
        editor.putInt("fontSizeTitle", fontSizeTitle);
        editor.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                utils.setFonts(textViewMainText, R.string.iran_sans_bold);
                utils.setFonts(textViewTitle, R.string.iran_sans_bold);
                break;
            case 1:
                utils.setFonts(textViewTitle, R.string.b_nazanin);
                utils.setFonts(textViewMainText, R.string.b_nazanin);
                break;
            case 2:
                utils.setFonts(textViewMainText, R.string.nastaligh);
                utils.setFonts(textViewTitle, R.string.nastaligh);
                break;
        }
        font = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
