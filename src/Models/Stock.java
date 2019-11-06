package Models;

public class Stock {


	private String name;
	private double price;
	
	public Stock(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	public String getStockName() {
		return name;
	}
	
	public double price() {
		return price;
	}
	
	@Override
	public String toString() {
		return("Stock name: " + name + ", Stock price: " + price);
	}
}
