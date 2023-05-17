package wukon.top.PetCommunity.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import wukon.top.PetCommunity.domain.BbsContent;
import wukon.top.PetCommunity.domain.po.IndexContent;
import wukon.top.PetCommunity.enums.StatusCodeEnum;
import wukon.top.PetCommunity.mapper.IndexContentMapper;
import wukon.top.PetCommunity.service.BbsContentService;
import wukon.top.PetCommunity.mapper.BbsContentMapper;
import org.springframework.stereotype.Service;
import wukon.top.PetCommunity.util.ResponseResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author 13069
* @description 针对表【bbs_content】的数据库操作Service实现
* @createDate 2023-03-15 10:16:42
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class BbsContentServiceImpl extends ServiceImpl<BbsContentMapper, BbsContent>
    implements BbsContentService{

    //调用多表查询分页
    private final IndexContentMapper indexContentMapper;

    @Override
    public ResponseResult queryIndexContentListPaged(Integer pageNum, Integer pageSize,String theme,String pet) {
        List<IndexContent> indexContents = indexContentMapper.queryIndexContentListPaged(pageNum, pageSize,theme,pet);

        //封装进分页对象
        Page page = new Page();
        page.setRecords(indexContents);
        page.setTotal(indexContentMapper.countAllDate());
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMsg(),page);
    }

    @Override
    public ResponseResult queryOneIndexContent(Integer contentId) {
        IndexContent indexContent = indexContentMapper.queryOneIndexContent(contentId);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMsg(),indexContent);
    }

}




