import java.util.LinkedList;

public class Solucion {
	private LinkedList<Integer> solucion;
	private int bandaAncha;
	public static int minBandwidth;
	
	public Solucion() {
		
	}
	
	public Solucion(LinkedList<Integer> solucion, int bandaAncha) {
		this.solucion = solucion;
		this.bandaAncha = bandaAncha;
	}
	
	public Solucion(LinkedList<Integer> solucion, int bandaAncha, int minBandwidth) {
		this.solucion = solucion;
		this.minBandwidth = minBandwidth;
		this.bandaAncha = bandaAncha;
	}
	
	public int getBandwidth() {
		return this.bandaAncha;
	}
	public LinkedList<Integer> getSolucion() {
		return this.solucion;
	}
	
	public void setSolucion(LinkedList<Integer> solucion) {
		this.solucion = solucion;
	}
}
