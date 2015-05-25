import java.util.*;

public class Combustivel{
	private String tipo;
	private float preco;
}

public Combustivel(String t, float p){
	this.tipo = t;
	this.preco = p;
}

public String setTipo(String tipo){
	this.tipo = tipo;
}

public float getPreco(){
	return preco;
}

public float setPreco(float preco){
	this.preco = preco;
}

