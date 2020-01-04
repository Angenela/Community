package com.example.demo.Controller;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    //@GetMapping = @RequestMapping(method=RequestMethod.GET),@PostMapping = @RequestMapping(method=RequestMethod.POST)
    //"/"就是虚拟路径
    @GetMapping("/")
    //HttpServletRequest和HttpServletResponse接口会自动实例化
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {

        if (page < 1) {
            page = 1;
        }

        int totalCount = (int)questionMapper.countByExample(new QuestionExample());
        int totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = (totalCount / size) + 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);

        //返回值为templates内的html文件，即跳转到该html文件，虚拟路径为"/"
        return "index";
    }
}
