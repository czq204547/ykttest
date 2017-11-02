package t.c;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import t.dataAccess.DataAccess;
import t.po.Content;
import t.po.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */
//控制器
@Controller
@SessionAttributes("user")
public class Sell {
    @Autowired
    private DataAccess dataAccess;
    private Message1 message1;

    @RequestMapping(value = {"/login"},method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "redirect:/login";
    }

    @RequestMapping(value="/api/login",method = RequestMethod.POST)
    @ResponseBody
    public Message1 apilongin(User user, Model model, HttpServletResponse response,HttpSession session)throws Exception
    {
        message1=new Message1();
        User u=dataAccess.login(user);
        if(u !=null) {
            model.addAttribute("user", u);
            message1.setCode(200);
            message1.setMessage("ok");
            message1.setResult(true);
        }
        return message1;

    }
    @RequestMapping(value = "/public",method = RequestMethod.GET)
    public String publish(Model model){
        return "public";
    }

    @RequestMapping(value = "/publicSubmit",method = RequestMethod.POST)
    public String publicSubmit(Content content, Model model,HttpSession session)throws IOException
    {
        if(!content.getFile().isEmpty()){
            String path=session.getServletContext().getRealPath("/images/");
            System.out.println(path);
            String fileName=content.getFile().getOriginalFilename();
            File filePath=new File(path,fileName);
            if(!filePath.getParentFile().exists()) filePath.getParentFile().mkdirs();
            content.getFile().transferTo(new File(path+fileName));
            content.setImage("/images/"+fileName);
        }
        dataAccess.saveContent(content);
        model.addAttribute("product",content);
        return "publicSubmit";
    }

    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public String show(@RequestParam("id") int id,Model model){
        Content content=dataAccess.show(id);
        User u=(User)model.asMap().get("user");
        if(u!=null){
            if(u.getUserType()==0){//买家
                content.setPersonId(u.getId());
                if(dataAccess.selectById(content)>0){
                    content.setBuy(true);
                    content.setPersonId(u.getId());
                    Content c=dataAccess.selectTrxById(content);
                    content.setBuyPrice(c.getBuyPrice());
                    content.setBuyNum(c.getBuyNum());
                }
            }
            if(u.getUserType()==1){//卖家
                Integer sum=dataAccess.selectByIdSaleNum(content);
                if(sum!=null && sum>0){
                    content.setSell(true);
                    content.setSaleNum(sum);
                }
            }
        }
        model.addAttribute("product",content);
        return "show";
    }

    @RequestMapping(value="/edit",method = RequestMethod.GET)
    public String edit(int id,Model model){
        Content content=dataAccess.show(id);
        model.addAttribute("product",content);
        return "edit";
    }

    @RequestMapping(value="/editSubmit",method = RequestMethod.POST)
    public String eidtSubmit(Content content,Model model,HttpSession session)throws Exception
    {
        if(!content.getFile().isEmpty()){
            String path=session.getServletContext().getRealPath("/images/");
            String fileName=content.getFile().getOriginalFilename();
            File filePath=new File(path,fileName);
            if(!filePath.getParentFile().exists()) filePath.getParentFile().mkdirs();
            content.getFile().transferTo(new File(path+fileName));
            content.setImage("/images/"+fileName);
        }
        dataAccess.editContent(content);
        model.addAttribute("product",content);
        return "publicSubmit";
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model){
        User u=(User)model.asMap().get("user");
        List<Content> productList = dataAccess.index();

        if (u!=null) {
            int type=u.getUserType();
            if(type==0){        //如果是买家
            int personId=u.getId();
            for (Content c : productList) {
                c.setPersonId(personId);
                if (dataAccess.selectById(c) > 0) {
                    c.setBuy(true);
                  }
                }
            }else {     //卖家
                for(Content c:productList){
                    Integer sum=dataAccess.selectByIdSaleNum(c);
                    if(sum!=null && sum>0) c.setSell(true);
                }
            }
        }
        model.addAttribute("productList",productList);
        return "index";

    }
    @RequestMapping(value="/settleAccount",method = RequestMethod.GET)
    public String settleAccount(){
        return "settleAccount";
    }

    @RequestMapping(value="/account",method = RequestMethod.GET)
    public String account(Model model){
        User u=(User)model.asMap().get("user");
        List<Content> contents=dataAccess.selectTrx(u.getId());
        for(Content c:contents){
            Content c2=dataAccess.show(c.getId());
            c.setTitle(c2.getTitle());
            c.setImage(c2.getImage());
            c.setTotal(c.getBuyNum()*c.getBuyPrice());
        }
        model.addAttribute("buyList",contents);
        return "account";
    }

    @RequestMapping(value = "/api/buy",method = RequestMethod.POST)
    @ResponseBody
    public Message1 buy(@RequestBody List<Content> buyList,Model model){
        User u=(User)model.asMap().get("user");
        message1=new Message1();
        for(Content c:buyList){
            Content content=dataAccess.show(c.getId());
            content.setBuyTime(System.currentTimeMillis());
            content.setNumber(c.getNumber());
            content.setPersonId(u.getId());
            dataAccess.saveTrx(content);
        }
        message1.setCode(200);
        message1.setMessage("ok");
        message1.setResult(true);
        return message1;
    }
    @RequestMapping(value="/api/delete",method = RequestMethod.POST)
    @ResponseBody
    public Message1 apiDelete(int id){
        dataAccess.deleteContentById(id);
        message1.setCode(200);
        message1.setMessage("ok");
        message1.setResult(true);
        return message1;

    }

}
