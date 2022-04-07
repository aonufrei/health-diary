package com.aonufrei.healthdiary.models;

public enum PhysicalActivity {
	SEDENTARY(1.2),
	LIGHTLY_ACTIVE(1.375),
	MODERATELY_ACTIVE(1.55),
	ACTIVE(1.725),
	VERY_ACTIVE(1.9);

	private final Double ratio;

	PhysicalActivity(Double ratio) {
		this.ratio = ratio;
	}

	public Double getRatio() {
		return ratio;
	}
}
