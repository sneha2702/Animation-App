import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel implements Runnable{
	
	volatile Thread thread;
	public boolean isPause=false;
	public int x,y;
	public int delayTime;
	public int frameIndex=0;
	//Runnable rc=new Runnable();
	Image[] image;
	MediaTracker media;
	
	void loadAnimation(Animation a) {
		
		int elements=a.getFrames();
		image=new Image[elements];
		y=(this.getHeight()-a.getHeight())/2;
		x=(this.getWidth()-a.getWidth())/2;
		delayTime=a.getDelay();
		media=new MediaTracker(this);
		File folder = new File ( ".//Animations//" + a.getAnimationName());
		File [] listOfFiles = folder.listFiles();
		int index = 0;
		for(int i=0;i<image.length;i++) {
			try{
				if (listOfFiles[i].getName().endsWith(".gif")){
					image[index] = ImageIO.read(listOfFiles[i]);
					media.addImage(image[index], 0);
					index++;
				}
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
		repaint();
	}
	public void startAnimation() {
		frameIndex = 0;
		isPause = false;
		thread = new Thread(this);
		thread.start();
	}
	synchronized void pauseAnimation() {
		isPause=true;
	}
	
	synchronized void resumeAnimation() {
		isPause=false;
		notify();
	}
	synchronized void stopAnimation() {
		thread=null;
		notify();
		frameIndex=0;
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image!=null){
			g.drawImage(image[frameIndex],x,y,null);
			frameIndex++;
			if (frameIndex == image.length){
				frameIndex=0;
			}
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Get a reference to the current thread
		try {
			media.waitForID(0);
		}
		catch (InterruptedException e) {
			return;
		}
		Thread thisThread = Thread.currentThread();
		// Continue to loop while the thread has not been stopped.
		while (thread == thisThread) {
			try {
				// Sleep for the required delay between frames.
				Thread.sleep(delayTime);
				// If the thread is paused and has not been stopped, wait.
				synchronized (this) {
					while (isPause && thread == thisThread)
						wait();
				}
			} catch (InterruptedException e) {
				// Thread was interrupted.
			}
			// Redraw the animation panel.
			repaint();
			// Increment the current frame index (not shown).
		}
	}
	

}
