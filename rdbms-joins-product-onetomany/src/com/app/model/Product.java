package com.app.model;

import java.util.List;

public class Product {

	private int pid;
	private String pname;
	private Double price;
	private List<Color> colorr;
	private List<Size> sizee;
	

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Color> getColorr() {
		return colorr;
	}

	public void setColorr(List<Color> colorr) {
		this.colorr = colorr;
	}

	public List<Size> getSizee() {
		return sizee;
	}

	public void setSizee(List<Size> sizee) {
		this.sizee = sizee;
	}

	}
