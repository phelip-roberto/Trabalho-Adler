
import java.util.*;

public class Automovel{
	private String tipo, marca, modelo, cor;
	private int ano;
	private float potencia, consumo, nivelTanque;
	private boolean hardware;
	private Combustivel comb[] = new Combustivel[3];
}

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
    this.comb = comb;
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

public String getCombustivel(){
	return comb;
}

public void setCombustivel(String comb){
	this.comb = comb;
}

public void checarTemp(){
	Random motor = new Random();
	float temp = motor.nextFloat();
	if(temp > 90)
		System.out.println("---Temperatura elevada!--- %.2fº celsius", temp);
	else{
		if(temp < 85)
			System.out.println("---Temperatura baixa!--- %.2fº celsius", temp);
		else
			System.out.println("Temperatura normal: %.2fº celsius", temp);
	}
}

public void checarNivelTanque(){
	Random tanque - new Random();
	float nivel = tanque.nextFloat();
	if((nivel<=100)&&(nivel>=95))
		System.out.println("Tanque cheio: %f", );
	else{
		if((nivel>=60) &&(nivel<95))
			System.out.println("Nível do tanque Normal: %f", nivel);
		else
			System.out.println("Nível do tanque: Baixo: %f", nivel);

	}
}

public void createCombust(String t, float p){
	Combustivel c = new Combustivel(String t, float p); //tipo e preço
	comb.adiciona(c);
}

public int searchCombust(String tipoComb){ 
	int i;
	for(i=0; i<2; i++){
		if(comb(i).getTipo == tipoComb)
			return i;
	}
	return -1;
}

public void editCombust(String t, float p){ //novo preço
	int x = searchCombust(t);
	if (x == -1) 
		System.out.println("\n---Combustivel Inválido---\n");
	else{
		comb[i].setPreco(p);
		System.out.println("\nPreço alterado com sucesso.\n");
	}
}

public void removeCombust(String t){
	int pos = searchCombust(t);
	if (pos == -1) 
		System.out.println("\n---Combustível Inválido---\n");
	else{
		comb.remove(pos);
		System.out.println("\nCombustível removido com sucesso.\n");
	}
}






