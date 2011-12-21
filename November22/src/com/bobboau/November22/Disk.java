package com.bobboau.November22;

import android.graphics.PointF;

public class Disk {
	public PointF pos;
	public float rad;
	public int color;
	
	public Disk(PointF P, float R, int C)
	{
		pos=P;
		rad=R;
		color=C;
	}
	
	public Disk()
	{
		//do nothing constructor
	}

}
