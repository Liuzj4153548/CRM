package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.commons.constants.Constants;
import com.powernode.crm.commons.domain.ReturnObject;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.commons.utils.HSSFUtils;
import com.powernode.crm.commons.utils.UUIDUtils;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.domain.ActivityRemark;
import com.powernode.crm.workbench.service.ActivityRemarkService;
import com.powernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @title:ActivityController Author liu
 * @Date:2023/3/30 21:16
 * @Version 1.0
 */
@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request) {
        //调用service层方法，查询所有的用户
        List<User> userList = userService.queryAllUsers();
        //把数据保存到request作用域中
        request.setAttribute("userList",userList);
        //跳转到市场活动主页面，请求转发【携带数据】
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreatActivity.do")  //响应信息返回到市场活动的主页面
    @ResponseBody                                                   //自动返回json字符串
    public Object saveCreatActivity(Activity activity, HttpSession session){

        User user = (User) session.getAttribute(Constants.SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());//引用用户表的主键，要唯一

        ReturnObject returnObject = new ReturnObject();

        //写数据考虑报不报异常
        //调用service层方法，保存创建的市场活动
        try {
            int ret = activityService.saveCreateActivity(activity);
            if (ret > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }
        }catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }

        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate,
                                                  int pageNo,int pageSize) {
        //封装参数
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo - 1) * pageSize);
        map.put("pageSize",pageSize);
        //调用service方法查询两个数据
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryCountActivityByCondition(map);
        //根据查询结果，生成响应信息
        Map<String,Object> retMap = new HashMap<String, Object>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }

    //调用service层根据id数组批量删除市场活动
    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivityByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法删除数据
            int ret = activityService.deleteActivityByIds(id);
            if (ret > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("删除出问题了，请重试....");
        }
        return returnObject;//Java对象会自动转换成json字符串返回
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    @ResponseBody
    public Object queryActivityById(String id) {
        //调用service方法查询市场活动
        Activity activity = activityService.queryActivityById(id);

        //根据查询结果，返回响应信息
        return activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    @ResponseBody
    public Object saveEditActivity(Activity activity,HttpSession session) {

        User user = (User)session.getAttribute(Constants.SESSION_USER);

        //封装参数
        activity.setEditTime(DateUtils.formatDateTime(new Date()));
        activity.setEditBy(user.getEditBy());

        ReturnObject returnObject = new ReturnObject();

        try {
            //调用service层方法，保存修改的市场活动
            int ret = activityService.saveEditActivity(activity);
            if (ret > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，修改数据失败，请稍后");
            }
        }catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，修改数据失败，请稍后");
        }
        return returnObject;
    }

    /**
     * 下载excel表格测试的Controller
     * 不借助返回值返回响应信息
     */
    @RequestMapping("/workbench/activity/fileDownload.do")
    public void fileDownload(HttpServletResponse response) throws Exception{
        //1、设置响应类型【字节流文件】
        response.setContentType("application/octet-stream;charset=UTF-8");
        //2、获取输出流
        //PrintWriter out = response.getWriter();这是字符流
        //字节流
        OutputStream out = response.getOutputStream();

        //浏览器收到响应信息之后，默认会在显示窗口中打开响应信息，
        //即使打不开，也会调用电脑app打开，只有实在打不开才会激活文件下载功能
        //可以设置响应头信息，是浏览器接收到响应信息之后直接激活文件下载窗口
        //即使能打开，也不会打开
        response.addHeader("Content-Disposition","attachment;filename=myStudentList.xls");


        //读取磁盘上的excel文件【InputStream】，输出到浏览器【OutputStream】
        FileInputStream is = new FileInputStream("D:\\webProject\\CRM\\crm-project\\crm\\src\\test\\java\\com\\powernode\\crm\\serverDir\\studentList.xls");
        byte[] buff = new byte[256];//建立缓冲区读取

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            out.write(buff,0,len);//写出数据
        }
        is.close();
        out.flush();//关闭资源，谁开启的谁关闭
    }

    @RequestMapping("/workbench/activity/exportAllActivities.do")
    public void exportAllActivities(HttpServletResponse response) throws Exception{
        //调用service层方法，查询所有市场活动
        List<Activity> activityList = activityService.queryAllActivities();

        //创建excel文件，将list集合写入到excel中
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        //表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        //遍历list集合，获取市场活动对象，创建HSSFRow对象，生成所有的数据行
        if (activityList != null && activityList.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                //每次遍历一个activity，创建一行
                row = sheet.createRow(i + 1);
                //每一行都有11列，都从activity对象中获取
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }

        //根据workbook对象生成excel文件
        /*FileOutputStream os = new FileOutputStream("D:\\webProject\\CRM\\crm-project\\crm\\src\\test\\java\\com\\powernode\\crm\\serverDir\\activityList.xls");
        workbook.write(os);//效率很低，因为他将内存里的数据写入磁盘了【此处是将内存里的数据写入磁盘】
        //关闭资源
        os.close();
        workbook.close();*/

        //把生成的excel文件下载到用户客户端
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream out = response.getOutputStream();

        //防止自动打开
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");

        /*InputStream is = new FileInputStream("D:\\webProject\\CRM\\crm-project\\crm\\src\\test\\java\\com\\powernode\\crm\\serverDir\\activityList.xls");
        byte[] buff = new byte[256];
        int len = 0;
        //此处循环的效率也非常低
        while ((len = is.read(buff)) != -1) {
            out.write(buff,0,len);//【此处是将磁盘中的数据读取到内存中】
        }
        is.close();*/
        workbook.write(out);//【直接实现内存到内存的输出】

        workbook.close();
        out.flush();
    }

    /**
     * 测试上传文件的Controller
     * 配置springmvc文件上传解析器
     * @param userName
     * @param myFile
     * @return
     */
    @RequestMapping("/workbench/activity/fileUpload.do")
    @ResponseBody
    public Object fileUpData(String userName, MultipartFile myFile) throws Exception{
        //把文本数据打印到控制台
        System.out.println("userName = " + userName);

        //获取文件名
        String originalFilename = myFile.getOriginalFilename();

        //把文件在服务器指定的目录中生成一个同样的文件
        File file = new File("D:\\webProject\\CRM\\crm-project\\crm\\src\\test\\java\\com\\" +
                "powernode\\crm\\serverDir\\" + originalFilename);
        //路径必须手动创建，文件如果不存在，会自动创建
        //该方法可以有两个参数，一个路径，一个文件名"D:\webProject\CRM\crm-project\crm\src\test\java\com\"
        //                                        "originalFilename"

        myFile.transferTo(file);//文件传输

        //返回响应信息
        ReturnObject returnObject = new ReturnObject();
        returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        returnObject.setMessage("上传成功");
        return returnObject;
    }


    @RequestMapping("/workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile,HttpSession session,String userName) {//此处需要在springmvc中配置文件上传解析器
        System.out.println(userName);
        User user = (User)session.getAttribute(Constants.SESSION_USER);
        ReturnObject retObj = new ReturnObject();
        try {
            /*//把接收到的excel文件写入磁盘目录中
            //获取文件名
            String originalFilename = activityFile.getOriginalFilename();
            //把文件在服务器指定的目录中生成一个同样的文件
            File file = new File("D:\\webProject\\CRM\\crm-project\\crm\\src\\" +
                    "test\\java\\com\\powernode\\crm\\serverDir\\",originalFilename);
            activityFile.transferTo(file);//文件传输【效率极低：将文件写入磁盘】

            //解析excel文件，获取文件中的数据，并封装成activityList
            InputStream is = new FileInputStream("D:\\webProject\\CRM\\crm-project\\crm\\src\\" +
                    "test\\java\\com\\powernode\\crm\\serverDir\\" + originalFilename);*/

            InputStream is = activityFile.getInputStream();//不再读写磁盘，直接获取文件

            HSSFWorkbook wb = new HSSFWorkbook(is);//【此处效率也低：将数据放到对象中】
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<Activity>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                activity = new Activity();//每次遍历一行，创建一个实体类对象
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    //获取列中的数据【调用封装好的工具类】
                    String cellValue = HSSFUtils.getCellValueForStr(cell);
                    if (j == 0) {
                        activity.setName(cellValue);
                    }else if (j == 1) {
                        activity.setStartDate(cellValue);
                    }else if (j == 2) {
                        activity.setEndDate(cellValue);
                    }else if (j == 3) {
                        activity.setCost(cellValue);
                    }else if (j == 4) {
                        activity.setDescription(cellValue);
                    }
                }
                //每一行中的数据封装好之后，把activity对象保存到list集合中
                activityList.add(activity);
            }
            //调用service层方法，保存市场活动
            int ret = activityService.saveCreatActivityByList(activityList);

            retObj.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            retObj.setMessage("成功导入" + ret + "条数据");
            retObj.setRetData(ret);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件导入失败，请稍后重试...");
            retObj.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            retObj.setMessage("文件导入失败，请稍后重试");
        }
        return retObj;
    }

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest request) {
        //调用service层方法查询数据
        Activity activity = activityService.queryActivityForDetailById(id);

        //需要注入市场活动详情的service
        List<ActivityRemark> activityRemarks = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        //把数据保存到作用域request中
        request.setAttribute("activity",activity);
        request.setAttribute("activityRemarks",activityRemarks);
        //跳转到明细页面===>请求转发
        return "workbench/activity/detail";
    }
}
