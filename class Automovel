import java.util.*;

public class Automovel{
	private String tipo, marca, modelo, cor;
	private int ano;
	private float potencia, consumo, nivelTanque;
	private boolean hardware;
	private combustivel comb;
}

public Automovel(String tipo, String marca, String modelo, String cor, int ano, float potencia, float consumo, float nivelTanque, boolean hardware, combustivel comb){
	this.tipo = tipo;
    this.marca = marca;
    this.modelo = modelo;
    this.cor = cor;
    this.ano = ano;
    this.comb = comb;
    this.potencia = potencia;
    this.consumo = consumo;
    this.nivelTanque = nivelTanque;
    this.hardware = hardware;
}

public String getTipo() {
    return tipo;
}
    
public void setTipo(String tipo) {
    this.tipo = tipo;
}

public int getAno() {
    return ano;
}

public float getPotencia() {
    return potencia;
}

public void setPotencia(float potencia) {
    this.potencia = potencia;
}

public float getConsumo() {
    return consumo;
}

public float getNivelTanque() {
    return nivelTanque;
}

public void setNivelTanque(float nivelTanque) {
    this.nivelTanque = nivelTanque;
}

public boolean isHardware() {
    return hardware;
}

public void setHardware(boolean hardware) {
    this.hardware = hardware;
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

/*public void excluirAutom(String tipo){
	Scanner sc = new Scanner(System.in);	
	if(!hardware){
		System.out.println("Não é possível excluir um automóvel com hardware. Deseja excluir o hardware? S(Sim)/N(Não)");
		Char c = (char)System.in.read();
		if(c == S) ...
		if(c == N) ...
	}
}*/

/*public void checarNivelTanque(){
	Random tanque - new Random();
	float nivel = tanque.nextFloat();
	if(nivel == 100)
		System.out.println("Tanque cheio!");
	else{
		if((nivel>=60) &&(nivel<=95))
	}
}*/









