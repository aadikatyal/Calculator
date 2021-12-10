package com.example.lvproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static final String ALBUM_KEY = "ALBUM_KEY";
    public static final String STRING_KEY = "STRING_KEY";
    public static final String LEFT_KEY = "LEFT_KEY";
    public static final String RIGHT_KEY = "RIGHT_KEY";

    private ListView listView;
    private ArrayList<Album> albumList;
    private int currentPosition;
    private ArrayAdapter<Album> listAdapter;
    private TextView tvLeft, tvRight, tvDesc;
    private String currentDesc = "", currentLeftText = "", currentRightText = "";

    private Button btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHelp = findViewById(R.id.btnHelp);
        instructions();

        albumList = new ArrayList<Album>();
        listView = findViewById(R.id.id_lv);
        tvDesc = findViewById(R.id.tvDesc);
        tvLeft = findViewById(R.id.tvLeft);
        tvRight = findViewById(R.id.tvRight);

        if(savedInstanceState == null)
        {
            albumDeclarations();
        }
        else
        {
            currentDesc = savedInstanceState.getString(STRING_KEY);
            currentLeftText = savedInstanceState.getString(LEFT_KEY);
            currentRightText = savedInstanceState.getString(RIGHT_KEY);

            if(tvDesc != null)
            {
                tvDesc.setText(currentDesc);
            }
            tvLeft.setText(currentLeftText);
            tvRight.setText(currentRightText);
            albumList = (ArrayList<Album>) savedInstanceState.getSerializable(ALBUM_KEY);
        }

        listAdapter = new CustomAdapter(this, R.layout.adapter_layout, albumList);
        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onResume()
    {
        listView.setSelection(currentPosition);
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(STRING_KEY, currentDesc);
        outState.putString(LEFT_KEY, currentLeftText);
        outState.putString(RIGHT_KEY, currentRightText);
        outState.putSerializable(ALBUM_KEY, albumList);
    }

    public class CustomAdapter extends ArrayAdapter<Album>
    {
        private Context mainContext;
        private int xml;
        private List<Album> list;
        private final int orientation = getResources().getConfiguration().orientation;

        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Album> objects)
        {
            super(context, resource, objects);
            mainContext = context;
            xml = resource;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mainContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            currentPosition = position;
            final View adapterLayout = layoutInflater.inflate(xml, null);

            Button btn = adapterLayout.findViewById(R.id.btn_adapter);
            btn.setFocusable(false);

            TextView tv = adapterLayout.findViewById(R.id.tv_adapter);
            tv.setText(list.get(position).getName());
            ImageView iv = adapterLayout.findViewById(R.id.iv_adapter);
            iv.setImageResource(list.get(position).getIv());

            btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    albumList.remove(position);
                    currentDesc = "";
                    currentLeftText = "";
                    currentRightText = "";
                    if(tvDesc != null)
                    {
                        tvDesc.setText(currentDesc);
                    }
                    tvLeft.setText(currentLeftText);
                    tvRight.setText(currentRightText);

                    notifyDataSetChanged();
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    currentDesc = list.get(position).getDescription();
                    currentLeftText = list.get(position).getYear();
                    currentRightText = list.get(position).getLength();

                    tvLeft.setText(albumList.get(position).getYear());
                    tvRight.setText(albumList.get(position).getLength());

                    if(orientation == Configuration.ORIENTATION_LANDSCAPE)
                    {
                        tvDesc.setText(currentDesc);
                    }
                }
            });
            return adapterLayout;
        }
    }

    public void albumDeclarations()
    {
        albumList.add(new Album("Owl Pharaoh", R.drawable.owl, "Owl Pharaoh is Travi$ Scott's debut mixtape. As a young rapper from Houston, Texas, Travis is just beginning his hip-hop career. The mixtape's best song, \"Upper Echelon ,\" which contains features from T.I. and 2 Chainz, was later publicly released on all platforms.", "2013", "51:34"));
        albumList.add(new Album("Days Before Rodeo", R.drawable.dbr, "Days Before Rodeo is Travi$ Scott's second mixtape, released as a prelude for his upcoming studio album, Rodeo. Contains many of his best songs, including Skyfall, DYSTI, and Mamacita. Contains features from the best hip-hop artists, including Young Thug and Migos. An underrated album, as many fans have not yet listened to DBR.", "2014", "50:02"));
        albumList.add(new Album("Rodeo", R.drawable.rodeo, "Travis Scott's first released album, Rodeo, was a huge hit. From talking about his childhood days on songs like \"Oh My Dis Side,\" to talking about his dark family past in \"90210,\" Scott really shows his true colors. Established his role as an autotune king", "2015", "65:26"));
        albumList.add(new Album("Birds in the Trap Sing McKnight", R.drawable.birds, "Scott's second studio album, ften referred to as \"Birds\", contains nearly 5 of Scott's top 10 songs till date. With features from Kendrick Lamar on the #1 song \"goosebumps,\" Young Thug on \"pick up the phone,\" and Kid Cudi on \"through the late night,\" Travis really shares his signature in this sophomore masterpiece." , "2016", "53:38"));
        albumList.add(new Album("Huncho Jack, Jack Huncho", R.drawable.huncho, "Huncho Jack, Jack Huncho is a collaborative album: Travis Scott and Quavo Huncho. The hip-hop duo originated in 2017. After the song \"Oh My Dis Side\" in Rodeo, the duo took off. \"Eye 2 Eye\" is the albums most popular song.", "2017", "41:34"));
        albumList.add(new Album("ASTROWORLD", R.drawable.astro, "ASTROWORLD is considered Travis Scott's largest production till date. A Grammy nominated album, Travis shares all styles of his music. \"SICKO MODE,\" is the most decorated song, which just received a 10x platinum award. Songs range from \"rage\" to more calm songs, such as \"5% TINT\" and \"COFFEE BEAN\".", "2018", "58:33"));
        albumList.add(new Album("JackBoys", R.drawable.jackboys, "Scott's most recent collaborative studio album with features of new Cactus Jack members (his record label). Songs like \"Gatti\" with Pop Smoke, \"Out West\" with Young Thug, and \"Gang Gang\" with Sheck Wes all charted. Travis Scott brings in new rappers, such as Don Toliver, acting as a leader to promote new members of the rap game.", "2019", "21:23"));
    }

    public void instructions()
    {
        btnHelp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Instructions");
                builder.setMessage("Click on any row to see the year and length of the album. Rotate the device to read a short description. Click on the X button to delete the album from the list.");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }
}