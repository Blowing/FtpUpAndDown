package com.wujie.ftpupanddown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ftpServerEt, ftpPortEt, ftpAccountEt, ftpPassword;
    private EditText ftpfilepathEt, filePathEt;
    private Button uploadBtn;
    private TextView resultTv;

    private String ip, port, account, password, savePath, filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ftpServerEt = (EditText) findViewById(R.id.server_ip_et);
        ftpPortEt = (EditText) findViewById(R.id.server_port_et);
        ftpAccountEt = (EditText) findViewById(R.id.ftp_username_et);
        ftpPassword = (EditText) findViewById(R.id.ftp_password_et);
        ftpfilepathEt = (EditText) findViewById(R.id.ftp_file_path_et);
        filePathEt = (EditText) findViewById(R.id.file_path_et);

        uploadBtn = (Button) findViewById(R.id.btn_upload);
        uploadBtn.setOnClickListener(this);

        resultTv = (TextView) findViewById(R.id.result);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload:
                getInput();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ftpUpLoad();
                    }
                }).start();

                break;
        }
    }

    private void getInput() {
        ip = ftpServerEt.getText().toString().trim();
        port = ftpPortEt.getText().toString().trim();
        account = ftpAccountEt.getText().toString().trim();
        password = ftpPassword.getText().toString().trim();
        savePath = ftpfilepathEt.getText().toString().trim();
//         ip = "192.168.1.135";
//         port = "21";
//         account = "wujie";
//        password = "wujie283315";
//        savePath = "D:\\测试";
        filePath = filePathEt.getText().toString().trim();
    }

    private void ftpUpLoad() {
        try {
            FileInputStream in = new FileInputStream(new File(filePath));

            int reply ;
            FTPClient ftp = new FTPClient();
            ftp.setControlEncoding("UTF-8");
            ftp.connect(ip, Integer.parseInt(port));
            ftp.login(account, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(savePath);
            ftp.changeWorkingDirectory(savePath);
            Log.e("ftp", "start");
            String [] s = filePath.split("/");

            ftp.storeFile(s[s.length - 1], in);
            in.close();
            Log.e("ftp", "success");
        } catch (Exception e) {
          Log.e("ftp", e.getMessage());
            e.printStackTrace();
        }
    }
}
