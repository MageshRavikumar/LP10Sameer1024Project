package com.training.bean;

public class UniformBean {
	private String model;


	public UniformBean() {
	}

	public UniformBean(String model) {
		super();
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "Model [model=" + model + "]";
	}

}
