package com.ptt.controller;

import com.ptt.pojo.Bmb;
import com.ptt.service.BmbService;
import com.ptt.service.TestRun;
import com.whalin.MemCached.MemCachedClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bmb")
public class BmbController {

    Logger logger = Logger.getLogger(BmbController.class);

    //@Autowired
    //@Qualifier("bmbService")
    //BmbService bmbService;
    private BmbService bmbService = TestRun.getBmbService();


//    @Resource
//    MemCachedClient memCachedClient;

    //查询全部
    @RequestMapping("/allBmb")
    public String list(Model model) {
        logger.info("---------查询开始-------------");

        long startTime = System.currentTimeMillis();
        try {
            List<Bmb> list = bmbService.queryAllBmb();
            logger.info("数据库查询执行了。。。。");

            model.addAttribute("list", list);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        logger.info("查询的时间为："+executeTime);

        return "allBmb";
    }

    //添加成员
    @RequestMapping("/toAddBmb")
    public String toAddPeople() {
        return "addBmb";
    }

    @RequestMapping(value = "/addBmb", method = RequestMethod.POST)
    public String addPeople(Bmb bmb) {
        logger.info(bmb);
        int i = bmbService.saveBmb(bmb);
        if (i > 0) {
            System.out.println("添加成功");
        }
        return "redirect:/bmb/allBmb";
    }

    //修改一条记录
    @RequestMapping("/toUpdateBmb")
    public String toUpdateBmb(Model model, int id) {
        Bmb bmb = bmbService.getBmbById(id);
        System.out.println(bmb);
        model.addAttribute("Qbmb", bmb);
        return "updateBmb";
    }

    @RequestMapping("/updateBmb")
    public String updateBmb(Model model, Bmb bmb) {
        System.out.println(bmb);
        int i = bmbService.updateBmb(bmb);
        if (i > 0) {
            System.out.println("更新成功");
        }
        Bmb bmb1 = bmbService.getBmbById(bmb.getId());
        model.addAttribute("bmb", bmb1);
        return "redirect:/bmb/allBmb";
    }

    //删除一本书籍
    @RequestMapping("/del/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        int i = bmbService.deleteBmbById(id);
        if (i > 0) {
            System.out.println("删除成功");
        }
        return "redirect:/bmb/allBmb";
    }

    //查询全部
    @RequestMapping("/allBmb1")
    @ResponseBody
    public Map MapBmb() {

        Map<String, Object> map = new HashMap<String, Object>();

        List<Bmb> list = bmbService.queryAllBmb();

        map.put("code", 200);
        map.put("message", "查询成功！！！！");
        map.put("data", list);
        map.put("warring", "计划经济哈哈哈哈哈哈哈");
        return map;
    }

    //添加成员

    @RequestMapping(value = "/addBmb1", method = RequestMethod.POST)
    @ResponseBody
    public Map addPeople1(Bmb bmb) {
        System.out.println(bmb);
        Map<String, Object> map = new HashMap<String, Object>();
        int i = bmbService.saveBmb(bmb);
        System.out.println("==============" + i);
        if (i > 0) {
            map.put("code", 200);
            map.put("data", bmb);
            map.put("message", "添加成功");
        }
        return map;
    }
}

