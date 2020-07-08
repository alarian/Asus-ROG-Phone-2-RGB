package terminal_heat_sink.asusrogphone2rgb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class AnimationsActivity extends Fragment {
    public AnimationsActivity() {
        // Required empty public constructor
    }
    private int current_selected = 0;
    private String current_selected_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.current_selected";
    private String notifications_on_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.notifications_on";
    private String notifications_animation_on_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.notifications_animation";
    private String notifications_second_led_on_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.notifications_second_led";
    private String use_notifications_second_led_only_on_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.notifications_second_led_use_only";

    private String use_notifications_timeout_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.use_notifications_timeout_shared_preference_key";
    private String notifications_timeout_seconds_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.notifications_timeout_seconds_shared_preference_key";
    private String notifications_timeout_progress_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.notifications_timeout_progress_shared_preference_key";



    private LinearLayout notification_settings_ll;
    private Button open_settings;
    private Switch switch_enable_notifications;
    private Spinner notificationAnimationSelector;
    private Switch switch_enable_second_led_notifications;
    private Switch switch_use_second_led_for_notifications_only;
    private Button button_select_apps;
    private SeekBar timeout_seekbar;
    private TextView timeout_text;
    private ScrollView scrollView;

    private boolean easter_egg_clicked = false;

    private String[][] animation_options = {
            {"0","off"},
            {"1","solid one colour"},
            {"2","breathing one colour"},
            {"3","blink"},
            {"4","rainbow"},
            {"5","another rainbow?"},
            {"6","rainbow breathe"},
            {"7","somekind of thunder"},
            {"8","thunder rainbow"},
            {"9","quick two flashes"},
            {"10","quick two flashes rainbow"},
            {"11","breathe rainbow"},
            {"12","some strange breathe rainbow"},
            {"13","slow glitchy rainbow"},
            {"14","yellow light? rofl"},
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_animations, container, false);


        LinearLayout animations_linear_layout = (LinearLayout) root.findViewById(R.id.animations_linear_layout);
        scrollView = (ScrollView) root.findViewById(R.id.animations_scroll_layout);

        ImageView easterEgg = (ImageView) root.findViewById(R.id.easteregg);

        easterEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easter_egg_clicked = !easter_egg_clicked;
                easter_egg();
            }
        });

        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);





        current_selected = prefs.getInt(current_selected_shared_preference_key,0);
        if(current_selected == 0){
            if(savedInstanceState != null){
                current_selected = savedInstanceState.getInt("current_selected");
            }else{
                current_selected = 0;
            }
        }


        create_animation_switches(animations_linear_layout,root);

        create_notification_settings(animations_linear_layout, prefs);
        scrollView.smoothScrollTo(0,0);

        return root;
    }

    private void create_animation_switches(LinearLayout animations_linear_layout, View root){

        final Switch[] switches = new Switch[animation_options.length];

        for (int i = 0; i < animation_options.length; i++) {

            Switch sw = new Switch(getActivity().getApplicationContext());
            sw.setThumbDrawable(getResources().getDrawable(R.drawable.asus_rog_logo_scaled));
            sw.setId(i);
            final int id_ = sw.getId();
            if(id_ == current_selected){
                sw.setChecked(true);
                sw.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
                sw.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
                sw.setTextColor(getResources().getColor(R.color.colorON));
            }else{
                sw.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                sw.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                sw.setTextColor(getResources().getColor(R.color.colorOFF));
            }
            sw.setText(animation_options[i][1]);
            //timeout_seekbar.setThumb(getResources().getDrawable(R.drawable.asus_rog_logo_scaled));
            switches[i] = sw;
            animations_linear_layout.addView(sw);
            sw = ((Switch) root.findViewById(id_));
            sw.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    boolean all_off = true;
                    for (int i = 0; i < switches.length; i++) {
                        if(i == id_){
                            if(switches[i].isChecked()){
                                current_selected = i;
                                SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                                        "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
                                prefs.edit().putInt(current_selected_shared_preference_key, current_selected).apply();
                                all_off = false;
                                switches[i].setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
                                switches[i].setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
                                switches[i].setTextColor(getResources().getColor(R.color.colorON));
                            }else{
                                switches[i].setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                                switches[i].setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                                switches[i].setTextColor(getResources().getColor(R.color.colorOFF));
                            }
                        }else{
                            switches[i].setChecked(false);
                            switches[i].setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                            switches[i].setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                            switches[i].setTextColor(getResources().getColor(R.color.colorOFF));
                        }
                    }

                    if(all_off){
                        current_selected = 0;
                        switches[0].setChecked(true);
                        switches[0].setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
                        switches[0].setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
                        switches[0].setTextColor(getResources().getColor(R.color.colorON));
                        SystemWriter.write_animation(0,getActivity().getApplicationContext());
                    }else{
                        //set effect
                        SystemWriter.write_animation(id_,getActivity().getApplicationContext());
                    }

                }
            });
        }
    }


    private void create_notification_settings(LinearLayout animations_linear_layout, SharedPreferences prefs){
        notification_settings_ll = new LinearLayout(getActivity().getApplicationContext());
        notification_settings_ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        notification_settings_ll.setOrientation(LinearLayout.VERTICAL);
        notification_settings_ll.setGravity(Gravity.FILL_VERTICAL);
        notification_settings_ll.setBackgroundColor(getResources().getColor(R.color.seperator));
        notification_settings_ll.setPadding(0,20,0,20);


        final TextView custom_text_view = new TextView(getActivity().getApplicationContext());
        LinearLayout.LayoutParams custom_text_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        custom_text_view_params.setMargins(0,0,0,20);
        custom_text_view.setLayoutParams(custom_text_view_params);
        custom_text_view.setTextColor(getResources().getColor(R.color.colorText));
        custom_text_view.setText("Notification Settings");
        //custom_text_view.setTextSize(custom_text_view.getTextSize()+1);
        custom_text_view.setTypeface(null, Typeface.BOLD);
        custom_text_view.setBackgroundColor(getResources().getColor(R.color.seperator));
        custom_text_view.setGravity(Gravity.CENTER_HORIZONTAL);


        notification_settings_ll.addView(custom_text_view);

        // react to notifications
        switch_enable_notifications = new Switch(getActivity().getApplicationContext());
        switch_enable_notifications.setText("React To Notifications");
        switch_enable_notifications.setThumbDrawable(getResources().getDrawable(R.drawable.asus_rog_logo_scaled));


        boolean notifications_enabled = prefs.getBoolean(notifications_on_shared_preference_key,false);

        if(notifications_enabled){
            switch_enable_notifications.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
            switch_enable_notifications.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
            switch_enable_notifications.setTextColor(getResources().getColor(R.color.colorON));
            switch_enable_notifications.setChecked(true);
        }else{
            switch_enable_notifications.setChecked(false);
            switch_enable_notifications.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
            switch_enable_notifications.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
            switch_enable_notifications.setTextColor(getResources().getColor(R.color.colorOFF));
        }

        switch_enable_notifications.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                        "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
                boolean notifications_enabled = prefs.getBoolean(notifications_on_shared_preference_key,false);
                Switch s = (Switch) view;
                if(notifications_enabled){
                    s.setChecked(false);
                    s.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                    s.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                    s.setTextColor(getResources().getColor(R.color.colorOFF));
                    prefs.edit().putBoolean(notifications_on_shared_preference_key, false).apply();
                }else{
                    s.setChecked(true);
                    s.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
                    s.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
                    s.setTextColor(getResources().getColor(R.color.colorON));
                    prefs.edit().putBoolean(notifications_on_shared_preference_key, true).apply();

                    Intent notification_intent = new Intent(getActivity().getApplicationContext(), NotificationService.class);
                    getActivity().getApplicationContext().startService(notification_intent);
                }

            }
        });

        notification_settings_ll.addView(switch_enable_notifications);

        // use second led also for notifications
        switch_enable_second_led_notifications = new Switch(getActivity().getApplicationContext());
        switch_enable_second_led_notifications.setText("Use second led for notifications also");
        switch_enable_second_led_notifications.setThumbDrawable(getResources().getDrawable(R.drawable.asus_rog_logo_scaled));

        boolean notifications_second_led_enabled = prefs.getBoolean(notifications_second_led_on_shared_preference_key,false);

        if(notifications_second_led_enabled){
            switch_enable_second_led_notifications.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
            switch_enable_second_led_notifications.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
            switch_enable_second_led_notifications.setTextColor(getResources().getColor(R.color.colorON));
            switch_enable_second_led_notifications.setChecked(true);
        }else{
            switch_enable_second_led_notifications.setChecked(false);
            switch_enable_second_led_notifications.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
            switch_enable_second_led_notifications.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
            switch_enable_second_led_notifications.setTextColor(getResources().getColor(R.color.colorOFF));
        }

        switch_enable_second_led_notifications.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                        "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
                boolean notifications_second_led_enabled = prefs.getBoolean(notifications_second_led_on_shared_preference_key,false);
                Switch s = (Switch) view;
                if(notifications_second_led_enabled){
                    s.setChecked(false);
                    s.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                    s.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                    s.setTextColor(getResources().getColor(R.color.colorOFF));
                    prefs.edit().putBoolean(notifications_second_led_on_shared_preference_key, false).apply();
                }else{
                    s.setChecked(true);
                    s.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
                    s.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
                    s.setTextColor(getResources().getColor(R.color.colorON));
                    prefs.edit().putBoolean(notifications_second_led_on_shared_preference_key, true).apply();

                }

            }
        });

        notification_settings_ll.addView(switch_enable_second_led_notifications);


        // use second led for notifications only
        switch_use_second_led_for_notifications_only = new Switch(getActivity().getApplicationContext());
        switch_use_second_led_for_notifications_only.setText("Use Only the second led for notifications");
        switch_use_second_led_for_notifications_only.setThumbDrawable(getResources().getDrawable(R.drawable.asus_rog_logo_scaled));
        boolean notifications_second_led_enabled_only = prefs.getBoolean(use_notifications_second_led_only_on_shared_preference_key,false);

        if(notifications_second_led_enabled_only){
            switch_use_second_led_for_notifications_only.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
            switch_use_second_led_for_notifications_only.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
            switch_use_second_led_for_notifications_only.setTextColor(getResources().getColor(R.color.colorON));
            switch_use_second_led_for_notifications_only.setChecked(true);
        }else{
            switch_use_second_led_for_notifications_only.setChecked(false);
            switch_use_second_led_for_notifications_only.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
            switch_use_second_led_for_notifications_only.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
            switch_use_second_led_for_notifications_only.setTextColor(getResources().getColor(R.color.colorOFF));
        }

        switch_use_second_led_for_notifications_only.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                        "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
                boolean notifications_second_led_enabled_only = prefs.getBoolean(use_notifications_second_led_only_on_shared_preference_key,false);
                Switch s = (Switch) view;
                if(notifications_second_led_enabled_only){
                    s.setChecked(false);
                    s.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                    s.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                    s.setTextColor(getResources().getColor(R.color.colorOFF));
                    prefs.edit().putBoolean(use_notifications_second_led_only_on_shared_preference_key, false).apply();
                }else{
                    s.setChecked(true);
                    s.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
                    s.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
                    s.setTextColor(getResources().getColor(R.color.colorON));
                    prefs.edit().putBoolean(use_notifications_second_led_only_on_shared_preference_key, true).apply();
                }

            }
        });

        notification_settings_ll.addView(switch_use_second_led_for_notifications_only);

        // open settings to enable notification access
        open_settings = new Button(getActivity().getApplicationContext());
        open_settings.setText("Click to enable App to read notifications in settings");
        open_settings.setTextColor(getResources().getColor(R.color.colorText));
        open_settings.setBackgroundColor(getResources().getColor(R.color.colorButton));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,10);
        open_settings.setLayoutParams(params);

        open_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS" ) ;
                startActivity(intent);
            }});

        notification_settings_ll.addView(open_settings);

        // select which apps to use for notifications
        button_select_apps = new Button(getActivity().getApplicationContext());
        button_select_apps.setText("Select which apps trigger notifications");
        button_select_apps.setTextColor(getResources().getColor(R.color.colorText));
        button_select_apps.setBackgroundColor(getResources().getColor(R.color.colorButton));

        button_select_apps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent app_selector = new Intent(getActivity().getApplicationContext(), AppSelector.class);
                startActivity(app_selector);
            }});

        notification_settings_ll.addView(button_select_apps);

        // which animation to use for notifications
        notificationAnimationSelector = new Spinner(getActivity().getApplicationContext());
        String[] animation_items = getResources().getStringArray(R.array.notification_animations);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, animation_items);


        adapter.setDropDownViewResource(R.layout.spinner_text);

        notificationAnimationSelector.setAdapter(adapter);

        notificationAnimationSelector.setPrompt("Select Animation to use for notifications");
        //notificationAnimationSelector.setBackgroundColor(getResources().getColor(R.color.colorBG));

        int notifications_animation = prefs.getInt(notifications_animation_on_shared_preference_key,1);
        notificationAnimationSelector.setSelection(notifications_animation-1,true);


        notificationAnimationSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int animation_mode = i+1;
                    if(animation_mode >= 14){
                        Log.i("Notification_animation_selector:","custom animation selected "+animation_mode);
                        animation_mode = 20 + (animation_mode-14);
                    }else{
                        Log.i("Notification_animation_selector:","animation selected "+animation_options[animation_mode][1]);
                    }
                    TextView selected = ((TextView) adapterView.getChildAt(0));
                    selected.setTextColor(getResources().getColor(R.color.colorText));
                    selected.setText("selected animation: "+selected.getText());

                    SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                        "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
                    prefs.edit().putInt(notifications_animation_on_shared_preference_key, animation_mode).apply();


                //((TextView) adapterView.getChildAt(0)).setTextSize(5);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        notification_settings_ll.addView(notificationAnimationSelector);


        Switch switch_timeout = new Switch(getActivity().getApplicationContext());
        switch_timeout.setText("Notification timeout");
        switch_timeout.setThumbDrawable(getResources().getDrawable(R.drawable.asus_rog_logo_scaled));

        boolean use_timeout = prefs.getBoolean(use_notifications_timeout_shared_preference_key,false);
        notification_settings_ll.addView(switch_timeout);
        if(use_timeout){
            switch_timeout.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
            switch_timeout.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
            switch_timeout.setTextColor(getResources().getColor(R.color.colorON));
            switch_timeout.setChecked(true);
            create_timeout_settings(prefs);
        }else{
            switch_timeout.setChecked(false);
            switch_timeout.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
            switch_timeout.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
            switch_timeout.setTextColor(getResources().getColor(R.color.colorOFF));
        }

        switch_timeout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                        "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
                boolean use_timeout = prefs.getBoolean(use_notifications_timeout_shared_preference_key,false);
                Switch s = (Switch) view;
                if(use_timeout){
                    s.setChecked(false);
                    s.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                    s.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOFF)));
                    s.setTextColor(getResources().getColor(R.color.colorOFF));
                    prefs.edit().putBoolean(use_notifications_timeout_shared_preference_key, false).apply();
                    notification_settings_ll.removeView(timeout_seekbar);
                    notification_settings_ll.removeView(timeout_text);

                }else{
                    s.setChecked(true);
                    s.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));
                    s.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorThumbOn)));
                    s.setTextColor(getResources().getColor(R.color.colorON));
                    prefs.edit().putBoolean(use_notifications_timeout_shared_preference_key, true).apply();
                    create_timeout_settings(prefs);
                }

            }
        });

        animations_linear_layout.addView(notification_settings_ll);
    }


    private void create_timeout_settings(SharedPreferences prefs){

        int timeout_seconds = prefs.getInt(notifications_timeout_seconds_shared_preference_key,60*30);// 30 mins
        int progress = prefs.getInt(notifications_timeout_progress_shared_preference_key,20);

        timeout_text = new TextView(getActivity().getApplicationContext());
        timeout_text.setTextColor(getResources().getColor(R.color.colorText));


        timeout_seekbar = new SeekBar(getActivity().getApplicationContext());
        timeout_seekbar.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));

        timeout_seekbar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON)));


        timeout_seekbar.setThumb(getResources().getDrawable(R.drawable.asus_rog_logo_scaled));
        timeout_seekbar.setMax(1000);
        timeout_seekbar.setMin(10);

        timeout_seekbar.setProgress(progress);
        timeout_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                        "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);

                prefs.edit().putInt(notifications_timeout_progress_shared_preference_key,i).apply();

                int input = (int)Math.pow(1.0105,i)+19;

                prefs.edit().putInt(notifications_timeout_seconds_shared_preference_key,input).apply();
                timeout_text.setText(format_timeout(input));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        timeout_text.setText(format_timeout(timeout_seconds));
        notification_settings_ll.addView(timeout_text);
        notification_settings_ll.addView(timeout_seekbar);
    }

    private String format_timeout(int time) {
        String output = "Notifications will timeout after";
        int total_time = time;
        if (total_time >= 60 * 60) { // greater than an hour
            int hours = 0;
            while (total_time >= 60 * 60) {
                hours++;
                total_time = total_time - 60 * 60;
            }
            output += " " + hours + " hours ";
        }

        if (total_time >= 60) { // minutes
            int minutes = 0;
            while (total_time >= 60) {
                minutes++;
                total_time = total_time - 60;
            }
            output += " " + minutes + " minutes ";
        }

        //seconds remaning
        if (total_time > 0)
            output += " " + total_time + " seconds ";

        Log.i("AsusRogPhone2RGBTimeoutSelected","milli:"+time+" "+output);

        return output;
    }

    private void easter_egg(){
        scrollView.smoothScrollTo(0,0);

        int[] colors1 = {Color.parseColor("#000000"), Color.parseColor("#0f9b0f")};
        GradientDrawable frame1 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,colors1);
        GradientDrawable frame11 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,colors1);

        int[] colors2 = {Color.parseColor("#DA22FF"), Color.parseColor("#9733EE")};
        GradientDrawable frame2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors2);

        int[] colors3 = {Color.parseColor("#7b4397"), Color.parseColor("#7b4397")};
        GradientDrawable frame3 = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,colors3);

        int[] colors4 = {Color.parseColor("#a8ff78"), Color.parseColor("#78ffd6")};
        GradientDrawable frame4 = new GradientDrawable(GradientDrawable.Orientation.BL_TR,colors4);

        int[] colors5 = {Color.parseColor("#0F2027"), Color.parseColor("#2C5364")};
        GradientDrawable frame5 = new GradientDrawable(GradientDrawable.Orientation.TR_BL,colors5);

        int[] colors6 = {Color.parseColor("#e1eec3"), Color.parseColor("#f05053")};
        GradientDrawable frame6 = new GradientDrawable(GradientDrawable.Orientation.BR_TL,colors6);

        int[] colors7 = {Color.parseColor("#2980B9"), Color.parseColor("#FFFFFF")};
        GradientDrawable frame7 = new GradientDrawable(GradientDrawable.Orientation.TL_BR,colors7);
        GradientDrawable frame77 = new GradientDrawable(GradientDrawable.Orientation.TL_BR,colors7);


        int[] colors8 = {Color.parseColor("#8E0E00"), Color.parseColor("#1F1C18")};
        GradientDrawable frame8 = new GradientDrawable(GradientDrawable.Orientation.BL_TR,colors8);

        int frame_seconds = 3000;

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(frame1,frame_seconds);
        animationDrawable.addFrame(frame2,frame_seconds);
        animationDrawable.addFrame(frame3,frame_seconds);
        animationDrawable.addFrame(frame4,frame_seconds);
        animationDrawable.addFrame(frame5,frame_seconds);
        animationDrawable.addFrame(frame6,frame_seconds);
        animationDrawable.addFrame(frame7,frame_seconds);

        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(1500);

        scrollView.setBackground(animationDrawable);

        int frame_seconds1 = 3000;
        AnimationDrawable animationDrawable1 = new AnimationDrawable();
        animationDrawable1.addFrame(frame77,frame_seconds1);
        animationDrawable1.addFrame(frame6,frame_seconds1);
        animationDrawable1.addFrame(frame5,frame_seconds1);
        animationDrawable1.addFrame(frame8,frame_seconds1);
        animationDrawable1.addFrame(frame3,frame_seconds1);
        animationDrawable1.addFrame(frame2,frame_seconds1);
        animationDrawable1.addFrame(frame11,frame_seconds1);
        animationDrawable1.setEnterFadeDuration(1500);
        animationDrawable1.setExitFadeDuration(1500);

        notification_settings_ll.setBackground(animationDrawable1);
        if(easter_egg_clicked){
            animationDrawable.start();
            animationDrawable1.start();
        }else{
            animationDrawable.stop();
            animationDrawable1.stop();
        }


    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("current_selected", current_selected);
    }

}