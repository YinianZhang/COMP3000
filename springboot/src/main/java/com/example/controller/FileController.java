package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Files interface
 */
@RestController
@RequestMapping("/files")
public class FileController {

    /**
     * Upload files store path
     */
    private static final String filePath = System.getProperty("user.dir") + "/files/";

    @Value("${server.port:9090}")
    private String port;

    @Value("${ip:localhost}")
    private String ip;

    /**
     * Upload files
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String flag;
        synchronized (FileController.class) {
            flag = System.currentTimeMillis() + "";
            ThreadUtil.sleep(1L);
        }
        String fileName = file.getOriginalFilename();
        try {
            if (!FileUtil.isDirectory(filePath)) {
                FileUtil.mkdir(filePath);
            }
            //File storage format: timestamp + file name
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);  // ***/manager/files/number-name
            System.out.println(fileName + "--UploadSuccessful");

        } catch (Exception e) {
            System.err.println(fileName + "--UploadFail");
        }
        String http = "http://" + ip + ":" + port + "/files/";
        return Result.success(http + flag + "-" + fileName);  //  http://localhost:9090/files/number-name
    }


    /**
     * Get files
     *
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")   //  timestamp + file name
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;
        try {
            if (StrUtil.isNotEmpty(flag)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(flag, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + flag);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("DownloadFileFail");
        }
    }

    /**
     * delete files
     *
     * @param flag
     */
    @DeleteMapping("/{flag}")
    public void delFile(@PathVariable String flag) {
        FileUtil.del(filePath + flag);
        System.out.println("DeleteFile" + flag + "Successful");
    }


}
