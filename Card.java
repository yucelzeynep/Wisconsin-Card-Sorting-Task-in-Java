import java.awt.Image;

import javax.swing.ImageIcon;


public class Card {

	private String name;
	private String color;
	private String shape;
	private String number;
	private ImageIcon image;
	
	public Card(String fileName,String path){
		String str = fileName.substring(0, 3);
		this.name = str;

		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(300, 400, java.awt.Image.SCALE_SMOOTH);
		image = new ImageIcon(newImg);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public ImageIcon getImage() {
		return image;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	
}
