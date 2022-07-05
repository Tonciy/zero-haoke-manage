package cn.zeroeden.haoke.dubbo.api.controller;

import cn.zeroeden.haoke.dubbo.api.service.PicUploadFileSystemService;
import cn.zeroeden.haoke.dubbo.api.service.PicUploadService;
import cn.zeroeden.haoke.dubbo.api.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zero
 * @Description 描述此类
 */
@Controller
@RequestMapping("pic/upload")
public class PicUploadController {


    @Autowired
    private PicUploadService picUploadService;

//    @Autowired
//    private PicUploadFileSystemService picUploadService;

    @PostMapping
    @ResponseBody
    public PicUploadResult upload(@RequestParam("file")MultipartFile multipartFile){
        return picUploadService.upload(multipartFile);
    }
}
