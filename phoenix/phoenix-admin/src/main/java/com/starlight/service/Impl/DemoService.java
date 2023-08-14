package com.starlight.service.Impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Service;

/**
 * doc
 *
 * @author shilian
 * @since 2023-08-14  18:46
 */
@Service
public class DemoService {

    private String fileName = "data.txt";

    private String packagePath = "/Users/shilian/Documents/java/phoenix/fileDemoPackage";

    public int method(int num1, int num2) {
        int resultNum = num1 + num2;
        try(FileOutputStream fileInputStream = new FileOutputStream(packagePath + File.separator +fileName);
        OutputStreamWriter outputStream = new OutputStreamWriter(fileInputStream)){
            outputStream.write(String.valueOf(resultNum));
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("file input success!");
        return num1 + num2;
    }
}
