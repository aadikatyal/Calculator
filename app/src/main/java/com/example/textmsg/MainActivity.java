package com.example.textmsg;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.TextView;

import static android.telephony.SmsMessage.createFromPdu;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_SEND_SMS = 0;
    private String receivingText, reply;
    private int state = 1;
    private TextView tvState, tvAddress, tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvState = findViewById(R.id.tvState);
        tvAddress = findViewById(R.id.tvAddress);
        tvText = findViewById(R.id.tvText);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE}, REQUEST_SEND_SMS);
        }

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context context, Intent intent)
            {
                String address = "";
                Bundle bundle = intent.getExtras();
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] smsMessage = new SmsMessage[pdus.length];

                for(int i = 0; i < pdus.length; i++)
                {
                    smsMessage[i] = createFromPdu(((byte[]) pdus[i]), (String) bundle.get("format"));
                    address = smsMessage[i].getDisplayOriginatingAddress();
                    tvAddress.setText(address);
                    receivingText = smsMessage[i].getDisplayMessageBody();
                    tvText.setText(receivingText);
                }

                switch (state)
                {
                    case 1:
                    {
                        if (receivingText.toLowerCase().contains("hi") || receivingText.toLowerCase().contains("hello") || receivingText.toLowerCase().contains("hey"))
                        {
                            int message = (int)(Math.random() * 4) + 1;
                            if(message == 1)
                            {
                                reply = "Hello, I am Aadi, your virtual assistant. Ask me how I am doing!";
                            }
                            else if(message == 2)
                            {
                                reply = "Greetings, my name am Aadi, the virtual assistant. Please ask me how I am doing!";
                            }
                            else if(message == 3)
                            {
                                reply = "Welcome to Aadi, your personal virtual assistant. Please ask me how I am doing!";
                            }
                            else if(message == 4)
                            {
                                reply = "This is Aadi, aka the virtual assistant. Please ask me how I am doing!";
                            }

                            sendMessage(reply, address);
                            tvState.setText(String.valueOf(state));
                            state++;
                        }
                        else
                        {
                            reply = "Invalid response. Please enter your response again.";

                            sendMessage(reply, address);
                        }
                        break;
                    }

                    case 2:
                    {
                        if (receivingText.toLowerCase().contains("how are you") || receivingText.toLowerCase().contains("day") || receivingText.toLowerCase().contains("feeling"))
                        {
                            int message = (int)(Math.random() * 4) + 1;
                            if(message == 1)
                            {
                                reply = "I am great. The weather outside is great. How are you?";
                            }
                            else if(message == 2)
                            {
                                reply = "Thank you for asking, I am having a splendid day. Now, how are you?";
                            }
                            else if(message == 3)
                            {
                                reply = "Why thank you for looking out for me! I am wonderful, how are you good sir?";
                            }
                            else if(message == 4)
                            {
                                reply = "It is a great day for assistant Aadi. How about you?";
                            }

                            sendMessage(reply, address);
                            tvState.setText(String.valueOf(state));
                            state++;
                        }
                        else
                        {
                            reply = "Invalid response. Please enter your response again.";

                            sendMessage(reply, address);
                        }
                        break;
                    }

                    case 3:
                    {
                        if (receivingText.toLowerCase().contains("great") || receivingText.toLowerCase().contains("amazing") || receivingText.toLowerCase().contains("good"))
                        {
                            int message = (int)(Math.random() * 4) + 1;
                            if(message == 1)
                            {
                                reply = "That is great. Do you have plans for today?";
                            }
                            else if(message == 2)
                            {
                                reply = "Splendid! What are your plans for today?";
                            }
                            else if(message == 3)
                            {
                                reply = "Glad you're doing good. Any plans for today?";
                            }
                            else if(message == 4)
                            {
                                reply = "Amazing, glad your day is going good. What are you doing today?";
                            }

                            sendMessage(reply, address);
                            tvState.setText(String.valueOf(state));
                            state++;
                        }
                        else
                        {
                            reply = "Invalid response. Please enter your response again.";

                            sendMessage(reply, address);
                        }
                        break;
                    }

                    case 4:
                    {
                        if (receivingText.toLowerCase().contains("gym") || receivingText.toLowerCase().contains("school") || receivingText.toLowerCase().contains("food"))
                        {
                            int message = (int)(Math.random() * 4) + 1;
                            if(message == 1)
                            {
                                reply = "Well, hope you enjoy your plans!";
                            }
                            else if(message == 2)
                            {
                                reply = "Cool, have fun with your day!";
                            }
                            else if(message == 3)
                            {
                                reply = "That sounds fun, I'll let you get to it";
                            }
                            else if(message == 4)
                            {
                                reply = "Splendid, hope you have an awesome day!";
                            }

                            sendMessage(reply, address);
                            tvState.setText(String.valueOf(state));
                            state++;
                        }
                        else
                        {
                            reply = "Invalid response. Please enter your response again.";

                            sendMessage(reply, address);
                        }
                        break;
                    }

                    default:
                    {
                        break;
                    }
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    public void sendMessage(final String replyText, final String address)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(address, null, replyText, null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_SEND_SMS:
            {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE}, REQUEST_SEND_SMS);
                }
            }
            break;

            default:
            {
                break;
            }
        }
    }
}