package com.ozf.laiyw.manage.dao.pagehelper;

import java.util.List;

public class PageInfo<T> {

	private Integer pageNum;
	private Integer pageSize;
	private int startRow;
	private int endRow;
	private long total;
	private int pages;
	private List<T> list;

	private String sortName;
	private String sortOrder;

	public PageInfo() {
		super();
	}

	public PageInfo(Integer pageNum, Integer pageSize, String sortName, String sortOrder) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sortName = sortName;
		this.sortOrder = sortOrder;
	}

	public PageInfo(List<T> list) {
		setList(list);
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setList(List<T> list) {
		if (list instanceof Page) {
			Page<T> page = (Page<T>) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.startRow = page.getStartRow();
			this.endRow = page.getEndRow();
			this.total = page.getTotal();
			this.pages = page.getPages();
			this.list = page;
			this.sortName = page.getSortName();
			this.sortOrder = page.getSortOrder();
		} else {
			this.list = list;
		}
	}
}
