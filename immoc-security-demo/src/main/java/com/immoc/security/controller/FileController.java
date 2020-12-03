package com.immoc.security.controller;


import com.immoc.security.model.dto.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
public class FileController {

    @PostMapping("/file")
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        String folder = "C:\\";
        File localFile = new File(folder, new Date().getTime() + ".txt");
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/file/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        String folder = "C:\\";
        try {
            InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
            OutputStream outputStream = response.getOutputStream();
            response.addHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
