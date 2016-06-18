package com.desuotest.pojo;

import java.util.List;

public class MaterialList{
	private int total_count;
	private int item_count;
	private List<BaseMaterial> materials;
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getItem_count() {
		return item_count;
	}
	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}
	public List<BaseMaterial> getMaterials() {
		return materials;
	}
	public void setMaterials(List<BaseMaterial> materials) {
		this.materials = materials;
	}

	
}
