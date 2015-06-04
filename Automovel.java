import java.util.*;

public class Automovel{
	
    private String tipo, marca, modelo, cor;
	private int ano;
	private float potencia, consumo, nivelTanque;
	private boolean hardware;
	private ArrayList<Combustivel> listComb;


    public Automovel(String tipo, String marca, String modelo, String cor, int ano, float potencia, float consumo, float nivelTanque, boolean hardware, Combustivel comb){
       	this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.potencia = potencia;
        this.consumo = consumo;
        this.nivelTanque = nivelTanque;
        this.hardware = hardware;
        listComb = new ArrayList <Combustivel>();
    }

    public String getTipo() {
        return tipo;
    }   

    public String getMarca(){
		return marca;
    }

    public String getModelo(){
		return modelo;
    }

    public String getCor(){
		return cor;
    }

    public void setCor(String cor){
		this.cor = cor;
    }
    
    public int getAno() {
        return ano;
    }

    public float getPotencia() {
        return potencia;
    }

    public float getConsumo() {
        return consumo;
    }

    public float getNivelTanque() {
        return nivelTanque;
    }

    public boolean isHardware() {
        return hardware;
    }

    public void setHardware(boolean hardware) {
        this.hardware = hardware;
    }

    public String getCombustivel(int n){
    	return listComb[n].tipo;
    }

    public void setCombustivel(String tipo, int n){
		this.listComb[n].tipo = tipo;
    }
    

    public void checarTemp(){
		Random motor = new Random();
		float temp = motor.nextFloat();
		if(temp > 90)
			System.out.println("---Temperatura elevada!---  " + temp +" celsius");
		else{
			if(temp < 85)
				System.out.println("---Temperatura baixa!---  " + temp + "celsius");
			else
				System.out.println("Temperatura normal!  " + temp + "celsius"); 
    	}
    }

    public void checarNivelTanque(){
        float nivel = Math.random();
        if (nivel>=95)
            System.out.println("Tanque cheio: " + nivel);
        else{
            if(nivel>=60 && nivel<95)
                System.out.println("Nível do tanque Normal: " +nivel);
            else
                System.out.println("Nível do tanque Baixo: " +nivel);
        }
    }   


    public void createCombust(String t, float p){
		Combustivel c = new Combustivel(t,p); //tipo e preço
		listComb.add(c);
    }

    public int searchCombust(String tipoComb){ 
		int pos;
		for(pos=0; pos<2; pos++){
			if(listComb.get(pos).getTipo == tipoComb)
                return pos;
        	}
		return -1;
    }

    public void editCombust(String t, float p){ //novo preço
		int pos = searchCombust(t);
		if (pos == -1) 
			System.out.println("\n---Combustivel Inválido---\n");
		else{
			listComb.get(pos).setPrecoComb(p);
			System.out.println("\nPreço alterado com sucesso.\n");
		}
    }

    public void removeCombust(String t){
		int pos = searchCombust(t);
		if (pos == -1) 
			System.out.println("\n---Combustível Inválido---\n");
		else{
			listComb.remove(pos);
			System.out.println("\nCombustível removido com sucesso.\n");
		}
    }
}
