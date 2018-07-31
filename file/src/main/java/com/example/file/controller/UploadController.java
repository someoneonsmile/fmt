package com.example.file.controller;

import com.example.file.resolver.Upload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author wangyp
 */
@Controller
public class UploadController {

    @RequestMapping( "/toUpload" )
    public String toUpload() {
        return "img-upload";
    }

    @RequestMapping( "/doUpload" )
    @ResponseBody
    public List<String> doUpload( @Upload( "file" ) List<String> pathList ) {
        System.out.println(pathList);
        // [
        //     "D:\2018\7\31\63c41ce5fdb26844b759b736e1b34f8e.jpg",
        //     "D:\2018\7\31\70e3cf6ddb841cf99af5c832a2400e86.jpg",
        //     "D:\2018\7\31\3970baedd9aeb7e51ba12711ebd08e3d.jpg"
        // ]
        return pathList;
    }

    // @RequestMapping( "/doUpload" )
    // @ResponseBody
    // public List<String> doUpload( @Upload( "file" ) String[] pathList ) {
    //     System.out.println(pathList);
    //     // [
    //     //     "D:\2018\7\31\63c41ce5fdb26844b759b736e1b34f8e.jpg",
    //     //     "D:\2018\7\31\70e3cf6ddb841cf99af5c832a2400e86.jpg",
    //     //     "D:\2018\7\31\3970baedd9aeb7e51ba12711ebd08e3d.jpg"
    //     // ]
    //     return Arrays.asList(pathList);
    // }


    // 传多个文件时, 用 String 类型时, 返回结果为: 多个结果以,分隔
    // @RequestMapping( "/doUpload" )
    // @ResponseBody
    // public String doUpload( @Upload( "file" ) String pathList ) {
    //     System.out.println(pathList);
    //     // D:\2018\7\31\63c41ce5fdb26844b759b736e1b34f8e.jpg,D:\2018\7\31\70e3cf6ddb841cf99af5c832a2400e86.jpg,D:\2018\7\31\3970baedd9aeb7e51ba12711ebd08e3d.jpg
    //     return pathList;
    // }
}
