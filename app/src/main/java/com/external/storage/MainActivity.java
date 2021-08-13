package com.external.storage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.external.storage.utilities.ExternalPrivateFileUtils;
import com.external.storage.utilities.StorageUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText editText;
    private TextView textView, filePathTextView;
    private Button writeButton, readButton;

    private String directoryName            = "AppName";
    private String fileExtension            = "txt";
    private String fileName                 = "externalFile";

    File directory = null;
    File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        initializeEvent();
    }

    private void initializeView() {
        editText            = findViewById(R.id.editText);
        textView            = findViewById(R.id.textView);
        writeButton         = findViewById(R.id.writeButton);
        readButton          = findViewById(R.id.readButton);
        filePathTextView    = findViewById(R.id.filePathTextView);
    }

    private void initializeEvent() {
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StorageUtils.isExternalStorageAvailableAndWriteable())
                {
                    directory = ExternalPrivateFileUtils.createDirectory(MainActivity.this, directoryName);
                    file = ExternalPrivateFileUtils.createFile(directory, fileExtension, fileName);
                    //file = ExternalPrivateFileUtils.createFileInsideCacheDirectory(MainActivity.this, fileExtension, fileName);

                    String dynamicData = editText.getText().toString();
                    String staticData = "username password";
                    writeFile(file,dynamicData);
                }
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StorageUtils.isExternalStorageAvailableAndWriteable())
                {
                    StringBuilder stringBuffer = readFile(file);

                    if (stringBuffer.length() > 0)
                    {
                        String username = stringBuffer.substring(0,stringBuffer.indexOf(" "));
                        String password = stringBuffer.substring(stringBuffer.indexOf(" ")+1);

                        textView.setText("username : "+username+"\n"+"password : "+password);

                        filePathTextView.setText(""+file);

                        Toast.makeText(getApplicationContext(),"READ FILE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void writeFile(File file, String data){
        PrintWriter printWriter;

        try
        {
            printWriter = new PrintWriter(file);
            printWriter.println(data);
            printWriter.flush();
            printWriter.close();

            Toast.makeText(getApplicationContext(), "WRITE FILE", Toast.LENGTH_SHORT).show();
        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }
    }

    public StringBuilder readFile(File file){
        FileReader fileReader;

        StringBuilder stringBuffer = new StringBuilder();
        String receiveString;

        try
        {
            if (file != null)
            {
                fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                receiveString = bufferedReader.readLine();
                while (receiveString!=null){
                    stringBuffer.append(receiveString);
                    receiveString = bufferedReader.readLine();
                }

                bufferedReader.close();
                fileReader.close();
            }
        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }

        return stringBuffer;
    }
}