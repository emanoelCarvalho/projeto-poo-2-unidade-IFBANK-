package projeto.banco.model.conta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import projeto.banco.dao.conta.ContaDAO;
import projeto.banco.database.ConexaoMySql;
import projeto.banco.model.transacao.RegistroTransacao;

public class ContaPoupanca implements IConta {

	private Integer numero;
	private String tipo;
	private BigDecimal saldo;
	private String cpfTitular;
	private Boolean status;

	public ContaPoupanca(String cpfTitular, String tipo) {
		this.numero = new Random().nextInt(999999999);
		this.tipo = tipo;
		this.saldo = BigDecimal.ZERO;
		saldo.setScale(4, RoundingMode.HALF_UP);
		this.cpfTitular = cpfTitular;
		this.status = true;
	}

	@Override
	public String toString() {
		return "ContaCorrente [numero=" + numero + ", tipo=" + tipo + ", saldo=" + saldo + ", cpfTitular=" + cpfTitular
				+ ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaPoupanca other = (ContaPoupanca) obj;
		return Objects.equals(numero, other.numero);
	}

	@Override
	public Integer getNumero() {
		// TODO Auto-generated method stub
		return this.numero;
	}

	@Override
	public void setNumero(int numero) {
		// TODO Auto-generated method stub
		this.numero = numero;
	}

	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return this.tipo;
	}

	@Override
	public void setTipo(String tipo) {
		// TODO Auto-generated method stub
		this.tipo = tipo;
	}

	@Override
	public BigDecimal getSaldo() {
		// TODO Auto-generated method stub
		return this.saldo;
	}

	@Override
	public void setSaldo(BigDecimal saldo) {
		// TODO Auto-generated method stub
		this.saldo = saldo;
	}

	@Override
	public String getCpfTitular() {
		// TODO Auto-generated method stub
		return this.cpfTitular;
	}

	@Override
	public void setCpfTitular(String cpf) {
		// TODO Auto-generated method stub
		this.cpfTitular = cpf;
	}

	@Override
	public Boolean getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

	@Override
	public void setStatus(Boolean status) {
		// TODO Auto-generated method stub
		this.status = status;
	}

	@Override
	public void setDetalhesConta(Integer numero, String tipo, BigDecimal saldo, String cpfTitular, Boolean status) {
		// TODO Auto-generated method stub
		setNumero(numero);
		setTipo(tipo);
		setSaldo(saldo);
		setCpfTitular(cpfTitular);
		setStatus(status);
	}

	@Override
	public Boolean depositar(BigDecimal quantia) {
		if (!status) {
			return false;
		}

		ContaDAO cDao = new ContaDAO(new ConexaoMySql());
		if (!cDao.depositarConta(this.numero, quantia)) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean sacar(BigDecimal quantia) {
		if (!status) {
			return false;
		}

		ContaDAO cDao = new ContaDAO(new ConexaoMySql());
		if (!cDao.sacar(this.numero, quantia)) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean transferir(BigDecimal quantia, int contaDestino) {
		ContaDAO cDao = new ContaDAO(new ConexaoMySql());
		cDao.transferir(quantia, contaDestino, this.getNumero());
		return null;
	}

	@Override
	public List<RegistroTransacao> emitirExtrato(int numeroConta, int mes, int ano) {
		ContaDAO cDAO = new ContaDAO(new ConexaoMySql());
		List<RegistroTransacao> extrato = cDAO.emitirExtrato(numeroConta, mes, ano);
		return extrato;
	}
}
