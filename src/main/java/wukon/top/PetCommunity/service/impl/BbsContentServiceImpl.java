package wukon.top.PetCommunity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

/**
* @author 13069
* @description 针对表【bbs_content】的数据库操作Service实现
* @createDate 2023-03-15 10:16:42
*/
@Service
@RequiredArgsConstructor
public class BbsContentServiceImpl extends ServiceImpl<BbsContentMapper, BbsContent>
    implements BbsContentService{

    //调用多表查询分页
    private final IndexContentMapper indexContentMapper;

    @Override
    public ResponseResult queryIndexContentListPaged(Integer pageNum, Integer pageSize) {
        List<IndexContent> indexContents = indexContentMapper.queryIndexContentListPaged(pageNum, pageSize);
        //封装进分页对象
        Page page = new Page();
        page.setRecords(indexContents);
        page.setTotal(indexContentMapper.countAllDate());
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMsg(),page);
    }

}




