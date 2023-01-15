package com.example.RobotControl;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.RobotControl.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public static int port;
    public static String ip;

    private enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public static void send(String ip, int port, byte[] data) throws IOException {
        InetAddress address = InetAddress.getByName(ip);
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        socket.close();
    }

    public static void encode(Direction dir) throws IOException {
        switch (dir) {
            case LEFT:
                send(ip, port, "LEFT".getBytes(StandardCharsets.US_ASCII));
                break;
            case UP:
                send(ip, port, "UP".getBytes(StandardCharsets.US_ASCII));
                break;
            case DOWN:
                send(ip, port, "DOWN".getBytes(StandardCharsets.US_ASCII));
                break;
            case RIGHT:
                send(ip, port, "RIGHT".getBytes(StandardCharsets.US_ASCII));
                break;
            default:
                break;

        }
    }


    Button b_forward;
    Button b_back;
    Button b_right;
    Button b_left;
    Button b_enter;

    protected EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);

        b_forward = findViewById(R.id.b_forward);
        b_back = findViewById(R.id.b_back);
        b_left = findViewById(R.id.b_left);
        b_right = findViewById(R.id.b_right);
        b_enter = findViewById(R.id.b_enter);

        editText = (EditText) findViewById(R.id.edit_text);

        b_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread udpSend = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MainActivity.encode(Direction.UP);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                });
                udpSend.start();
            }
        });
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread udpSend = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MainActivity.encode(Direction.DOWN);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                });
                udpSend.start();
            }
        });
        b_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread udpSend = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MainActivity.encode(Direction.RIGHT);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                });
                udpSend.start();
            }
        });
        b_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread udpSend = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MainActivity.encode(Direction.LEFT);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                });
                udpSend.start();
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do something before the text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do something while the text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Split the string at the ":" character to get the ip and port values
                String[] values = s.toString().split(":");
                if (values.length != 2) {
                    // Throw an exception if the string is not in the correct format
//                    throw new Exception("Invalid IP and port format. Expected format: ip:port");
                    return;
                }

                // Assign the values to the appropriate variables
                MainActivity.ip = values[0];
                try{
                    MainActivity.port = Integer.parseInt(values[1]);
                }catch(Exception e){

                }
            }
        });

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

       /* binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}