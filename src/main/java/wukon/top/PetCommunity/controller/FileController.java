package wukon.top.PetCommunity.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wukon.top.PetCommunity.domain.Files;
import wukon.top.PetCommunity.mapper.FilesMapper;
import wukon.top.PetCommunity.service.FilesService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Auther:1306933
 * @Date:2022/12/30
 * @Description:wukon.top.controller
 * @version:1.0
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    //注入文件上传路径
    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Resource
    private FilesService filesService;

    /**
      *功能描述：文件上传
      *@param: file 前端传过来的文件
      *@return: String
      */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        //文件原始的名称
        String originalFilename = file.getOriginalFilename();
        //通过hutool工具类来获取文件的类型
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize(); //文件大小kb
        //先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断路径是否存在。若不存在则创建一个新的文件目录
        if (!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();

        String fileUUID = uuid + StrUtil.DOT + type;

        //
        File uploadFile = new File(fileUploadPath + fileUUID);
        //当文件存在的时候再获取文件的md5
        //文件下载路径
        String url;
        String md5;
        //这个方法可以把文件存到磁盘上去
        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        //获取文件的md5
        md5 = SecureUtil.md5(uploadFile);//从数据库查询是否存在相同记录
        //从数据库查询是否有相同的记录
        Files dbfiles = getFileByMd5(md5);
        if (dbfiles != null){
            url = dbfiles.getUrl();
            //删除已存在文件
            uploadFile.delete();
        } else {
            url = "http://localhost:9090/file/getfile/"+fileUUID;
        }


        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setMd5(md5);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        filesService.save(saveFile);

        return url;
    }
    
    /**
      *功能描述：文件下载接口：http://localhost:9090/file/getfile/{fileUUID}
      */
    @GetMapping("/getfile/{fileUUID}")
    public void download(@PathVariable String fileUUID,HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        //写出流要设置的属性
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        //写出流
        ServletOutputStream os = response.getOutputStream();
        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
      *功能描述：通过文件的md5查询文件
      *@param: md5
      *@return: files
      */
    private Files getFileByMd5(String md5){
        //从数据库查询有没有这个MD5
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = filesService.list(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

}
