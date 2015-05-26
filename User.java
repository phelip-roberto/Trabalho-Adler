import java.util.*;

public class User
{
	private String name, user, birth, cpf, passw, email, cel;
	/*nome completo, nome do usuário, data de nascimento, cadastro
	de pessoa fisica, email e numero do celular*/
	private int control1, control2, control3; //variavel para controle do set
	private ArrayList<Automovel> listAuto;

	public void Usuario(String na, String us, String ns, String cp, String pw, String em, String tel)
	{
		name = na;
		user = us;
		birth = ns;
		cpf = cp;
		passw = pw;
		email = em;
		cel = tel;
		control1 = control2 = control3 = 0;
		listAuto = new ArrayList<Automovel>();
	}

	public void setName(String no)
	{
		if(this.control1 < 5) //o usuário só poderá alterar seu nome 5 vezes
			this.nome = no;
		else
			System.out.println("Não é possível alterar o nome!");
		this.control1++;
	}

	public String getName()
	{
		return this.name;
	}

	public void setUser(String us)
	{
		if(this.control2 < 5)
			this.user = us;
		else
			System.out.println("Não é possível alterar o nome de usuário!");
		this.control2++;
	}

	public String getUser()
	{
		return this.user;
	}

	public String setPass(String p)
	{
		if(this.control3 < 5)
			this.passw = p;
		else
			System.out.println("Não é possível alterar a senha!");
		this.control2++;
	}	

	public void addAuto(String tipo, String marca, String modelo, String cor, int ano, float potencia, float consumo, float nivelTanque, boolean hardware, Combustivel comb[])
	{
		Automovel auto = new Automovel(tipo, marca, modelo, cor, ano, potencia, consumo, nivelTanque, hardware, comb);
		listAuto.add(auto);
	}

	public int searchAutoModelo(String search)
	{
		//lista o array com base no modelo do automovel
		int i;
		for(i = 0; i < listAuto.size; i++)
		{
			if(listAuto.get(i).modelo == search)
				return i;
		}
		return -1;
	}//retorna a posição do elemento

	public int searchAutoMarca(String search)
	{
		//lista o array com base na marca do automovel
		int i;
		for(i = 0; i < listAuto.size; i++)
		{
			if(listAuto.get(i).marca == search)
				return i;
		}
		return -1;
	}//retorna a posição do elemento

	public int searchAutoTipo(String search)
	{
		//lista o array com base no tipo do automovel
		int i;
		for(i = 0; i < listAuto.size; i++)
		{
			if(listAuto.get(i).tipo == search)
				return i;
		}
		return -1;
	}//retorna a posição do elemento

	public void changeAutoCor(int pos, String newdata)
	{
		listAuto.get(pos).cor = newdata;
	}

	public void changeAutoComb(int pos, Combustivel newdata)
	{
		listAuto.get(pos).comb = newdata;
	}

	public void changeAutoHardware(int pos, boolean newdata)
	{
			listAuto.get(pos).hardware = newdata;
	}
	
	public void removeAuto(int pos)
	{//remove o automovel pelo seu tipo, deve ser informada a sua posicao
		if(listAuto.get(pos).hardware)
		{
			System.out.println("O automovel possui o hardware implantado, a remocao ira impedir a comunicacao do aplicativo com o sistema embarcado. As seguintes funcionalidades serao desabilitadas: medir a temperatura do motor, medir nivel do tanque e seus respectivos relatorios.	Ciente destas informacoes, deseja prosseguir com a remocao do automovel?");
			System.out.println("1 -> SIM 					2 -> NAO");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			if(choice == 1)
			{
				System.out.println("Removido!");
				listAuto.remove(pos);
				return;
			}
			else
				return;
		}
		else
		{
			System.out.println("Removido!");
			listAuto.remove(pos);
		}
	}
}
