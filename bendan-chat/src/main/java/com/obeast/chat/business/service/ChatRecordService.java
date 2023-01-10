package com.obeast.chat.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.obeast.business.entity.ChatRecordEntity;
import com.obeast.core.domain.PageObjects;
import com.obeast.core.domain.PageParams;

/**
 * @author wxl
 * Date 2022/12/27 12:37
 * @version 1.0
 * Description:
 */
public interface ChatRecordService extends IService<ChatRecordEntity> {

   /**
    * Description: 分页查询
    * @author wxl
    * Date: 2022/12/27 12:36
    * @param pageParams parameters
    * @param userId userId
    * @param toId toId
    * @return com.obeast.core.domain.PageObjects<com.worldintek.chat.entity.ChatRecordEntity>
    */
    PageObjects<ChatRecordEntity> queryPage(PageParams pageParams, Long userId, Long toId);


}
