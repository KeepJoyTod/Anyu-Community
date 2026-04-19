package com.ruoyi.web.controller.smartcommunity;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.ICommunityRepairrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repair")
public class CommunityRepairrController {
    @Autowired
    private ICommunityRepairrService communityRepairrService;
    @GetMapping("/getList")
        public AjaxResult getList(){
      List<String>  getList =  communityRepairrService.getList();
      return AjaxResult.success(getList);
    }

}
