package com.hsy.platform.plugin;

import java.io.Serializable;
import java.util.List;


/**
 * 适配layui
 * @author Gravity
 *
 */
public class LayPage implements Serializable{

	private int limit ; //每页显示记录数
	private int totalPage;		//总页数
	private int count;	//总记录数
	private int page;	//当前页
	private int currentResult;	//当前记录起始索引
	private String pageStr;		//最终页面显示的底部翻页导航，详细见：getPageStr();(废弃)
	private String msg;
	private PageData pd = new PageData();
	private boolean hasNext;
	private int code = 0;
	List data ;//页面数据

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


	public LayPage(){
		this.limit =20;
		this.page = 1;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List getData(){
		return this.data;
	}

	public void setData(List data){
		this.data = data;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalPage() {
		if(count%limit==0)
			totalPage = count/limit;
		else
			totalPage = count/limit+1;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		if(page<=0)page =1;
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}



	public int getCurrentResult() {
		currentResult = (getPage()-1)*getLimit();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}


	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		this.pd = pd;
	}

	public boolean isHasNext() {
		return currentResult+limit<count;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("LayPage{" +
				"limit=" + limit +
				", totalPage=" + totalPage +
				", count=" + count +
				", page=" + page +
				", currentResult=" + currentResult +
				", pageStr='" + pageStr + '\'' +
				", msg='" + msg + '\'' +
				", hasNext=" + hasNext +
				", code=" + code +
				'}');
		sb.append(pd.toString());
		return sb.toString();
	}
}
