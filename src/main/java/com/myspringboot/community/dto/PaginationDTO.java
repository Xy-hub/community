package com.myspringboot.community.dto;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据传输类
 */
@Data
public class PaginationDTO<T> {
    //所有的问题，包含作者信息
    private List<T> data;
    //上一页
    private boolean showPrevious;
    //首页
    private boolean showFirstPage;
    //下一页
    private boolean showNext;
    //尾页
    private boolean showEndPage;
    //当前页
    private Integer page;
    //显示的页码
    private List<Integer> pages = new ArrayList<>();
    //总页数
    private Integer pageCount;


    public void setPagination(Integer total, Integer page, Integer pageSize) {
        this.setPage(page);
        pageCount=total%pageSize==0?total/pageSize:total/pageSize+1;
        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=pageCount){
                pages.add(page+i);
            }
        }
        //显示上一页
        if(page==1){
            showPrevious=false;
        }else{

            showPrevious=true;
        }
        //显示下一页
        if(page==pageCount){
            showNext=false;
        }else{
            showNext=true;
        }
        //显示首页
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //显示尾页
        if(pages.contains(pageCount)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
