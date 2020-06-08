import java.util.LinkedList;

public class Solucion implements Comparable<Solucion> {
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
	
	@Override
    public int compareTo(Solucion o) {
        int comparedSize = o.bandaAncha;
        if (this.bandaAncha > comparedSize) {
            return 1;
        } else if (this.bandaAncha == comparedSize) {
            return 0;
        } else {
            return -1;
        }
    }
}
