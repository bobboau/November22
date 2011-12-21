package com.bobboau.November22;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DiskView extends View{
	
	ArrayList<Disk> disks;
	
	//the disk we are moving
	int grabbed = -1;
	
	//the time we started adding, will be the last disk in the list
	long add_start_time = -1;

	public DiskView(Context context) {
		super(context);
		disks = new ArrayList<Disk>();
	}
	
	public DiskView(Context context, AttributeSet attrs) {
		super(context, attrs);
		disks = new ArrayList<Disk>();
	}

	//requirement #1 graphics
	@Override
	protected void onDraw(Canvas canvas) {
		
		Paint p = new Paint();
		p.setStyle(Style.FILL);
				
	    for(Disk disk : disks){
	    	if(disk.rad <= 0.0f)
	    		continue;
    		p.setColor(disk.color);
    		canvas.drawCircle(disk.pos.x, disk.pos.y, disk.rad, p);
        }
	}
	
	//finds a disk under the passed point
	// -1 means none
	private int findDisk(PointF pnt)
	{
		//do it 'backwards' so we grab the one on 'top'
	    for(int i = disks.size()-1; i > -1 ; i--){
	    	Disk disk = disks.get(i);
			PointF disp = new PointF(disk.pos.x - pnt.x, disk.pos.y - pnt.y);	        	    	
 	    	if(disp.length() < disk.rad)
	    	{
 	    		return i;
	    	}
	    }
	    
	    return -1;
	}
	
	//requirement #2 touch-sensitivity
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		PointF event_point = new PointF(event.getX(), event.getY());
		
        switch (event.getAction()) {
        	case MotionEvent.ACTION_DOWN:
        		        		
        		//see if we are on top of an existing disk
        		//if so grab it
        		grabbed = findDisk(event_point);
        	    
        	    //we didn't grab anything so add something
          		if(grabbed < 0)
          		{
	        		disks.add(
	        				new Disk(
	        					new PointF(event.getX(), event.getY()),
								10.0f, // start it off at SOMETHING
								Color.rgb((int)(255*Math.random()), (int)(255*Math.random()), (int)(255*Math.random()))
							)
	        		);
	        		add_start_time = System.currentTimeMillis();
	        		grabbed = disks.size()-1;
          		}
        	
        	case MotionEvent.ACTION_MOVE:
           		if(grabbed > -1)
        		{
        			//we are grabbing something, move it
        	    	Disk disk = disks.get(grabbed);
        	    	disk.pos.x = event_point.x;
        	    	disk.pos.y = event_point.y;
        	    	if(add_start_time > -1)
        	    	{
        	    		disk.rad = (System.currentTimeMillis() - add_start_time) / 10.0f;
        	    	}
        			disks.set(grabbed, disk);
        			
        			//nothing more to do
        			invalidate();
        			return true;
        		}
        	break;
        	
        	case MotionEvent.ACTION_UP:
        		add_start_time = -1;
        		grabbed = -1;
        	break;
        }

        return true;
	}
}
