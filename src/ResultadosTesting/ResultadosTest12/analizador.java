import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

public class analizador {

	public static void main(String[] args) throws Exception{
		String filename;
		int promedio=0;
		int mejorResultado=2320300;
		double promedioTiempo = 0;
		double[] valores = new double[50];
		double[] valoresTiempo = new double[50];
		for (int i = 1; i <= 50; i++) {
			if(i<10) {
				filename = "Test12Result0"+i+".txt";
				
			} else {
				filename = "Test12Result"+i+".txt";
			}
			File file = new File(filename); 
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st; 
			st = br.readLine();
			StringTokenizer stoken = new StringTokenizer(st);
			String xd = "";
			while(stoken.hasMoreTokens()) {
				xd = stoken.nextToken();
			}
			int hola = Integer.parseInt(xd);
			double ay = Double.parseDouble(xd);
			valores[i-1] = ay;
			if(hola<mejorResultado) {
				mejorResultado=hola;
			}
			promedio+=mejorResultado;
		    
			String tmp = "";
			while ((st = br.readLine()) != null) {
			
			    tmp = st;
			}
			
			stoken = new StringTokenizer(tmp);
			xd = "";
			while(stoken.hasMoreTokens()) {
				xd = stoken.nextToken();
			}
			double adios = Double.parseDouble(xd);
			valoresTiempo[i-1] = adios;
			
		    // Read file content here
		}
		
		System.out.println(mejorResultado);
		System.out.println(calculateprom(valores));
		System.out.println(calculateprom(valoresTiempo));
		System.out.println(calculateSD(valores));

	}
	public static double calculateSD(double numArray[])
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }
	public static double calculateprom(double numArray[])
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;
        return mean;
    }
}

