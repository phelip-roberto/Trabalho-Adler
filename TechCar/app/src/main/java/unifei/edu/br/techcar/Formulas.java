package unifei.edu.br.techcar;

// Fórmulas ultilizadas
public class Formulas {

    public static float nivelTanque(){
        return (float) Math.random() * 100;
    }

    public static float consumoMedio(float distancia,float consumo){
        return distancia/consumo;
    }

    public static float gastos(float distancia,float consumo,float preco){
        return (distancia/consumo)*preco;
    }

}