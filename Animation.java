import java.util.Comparator;

public class Animation {
	
	//variables in the order of the data in the text file
	private String animationName;
	private int width;
	private int height;
	private int frames;
	private int delay;

	public Animation(String animationName, int width, int height, int frames, int delay) {
		this.animationName = animationName;
		this.width = width;
		this.height = height;
		this.frames = frames;
		this.delay = delay;
	}

	//Accessor methods
	public String getAnimationName() {
		return animationName;
	}
	public void setAnimationName(String animationName) {
		this.animationName = animationName;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getFrames() {
		return frames;
	}
	public void setFrames(int frames) {
		this.frames = frames;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	@Override
	public String toString() {
		return animationName;
	}

}
class Compartor implements Comparator<Animation>{
	public int compare(Animation a, Animation b){
		return a.getAnimationName().compareTo(b.getAnimationName());
	}
}