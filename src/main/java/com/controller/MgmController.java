package com.controller;

import com.service.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author 长不大的韭菜
 * @date 2020/5/30 10:39 下午
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MgmController {
    final private PictureService pictureService;

    @GetMapping("/file")
    public DeferredResult<ResponseEntity<FileSystemResource>> generatorFile() {
        DeferredResult<ResponseEntity<FileSystemResource>> deferredResult = new DeferredResult<>(20 * 1000L);
        try {
            File file = pictureService.generate();
            if (file != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Content-Disposition", "attachment; filename=" + file.getName());
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                headers.add("Last-Modified", new Date().toString());
                headers.add("ETag", String.valueOf(System.currentTimeMillis()));
                ResponseEntity<FileSystemResource> body = ResponseEntity.ok().headers(headers).contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
                deferredResult.setResult(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        deferredResult.onTimeout(() -> {
            log.error("图片生成超时");
            deferredResult.setErrorResult("图片生成超时");
        });

        deferredResult.onError((throwable) -> {
            log.error("图片生成出错: " + throwable.getMessage());
            deferredResult.setErrorResult("图片生成出错");
        });

        return deferredResult;
    }

    @GetMapping("/file2")
    public ResponseEntity<FileSystemResource> generatorFile2() {
        try {
            File file = pictureService.generate();
            if (file != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Content-Disposition", "attachment; filename=" + file.getName());
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                headers.add("Last-Modified", new Date().toString());
                headers.add("ETag", String.valueOf(System.currentTimeMillis()));
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
