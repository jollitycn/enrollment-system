package com.educiot.recruit.server.controller.common;

import cn.hutool.core.lang.UUID;
import com.educiot.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @program: server
 * @description: 文件上传
 * @author: XuChao
 * @create: 2020-04-16 17:06
 **/
@RestController
@RequestMapping("/upload")
@Api(value = "文件上传controller", tags = {"文件上传接口"})
@Log4j2
public class UploadController extends BaseController {

    @Value("${image.address}")
    private String imageAddress;

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/add")
    public Result add(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("上传文件");
        String fileName = UUID.fastUUID() + file.getOriginalFilename();
        String fileNameDest = imageAddress + File.separator + fileName;
        File destFile = new File(fileNameDest);
        destFile.getParentFile().mkdirs();
//       try {
        file.transferTo(destFile);
//       }catch (IOException e){
//            log.error(e.getMessage());
//       }
        //返回文件名字
        return Result.success(fileName);
    }
}
