package com.ritchie.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídia");
	
	private Integer codigo;
	private String descricao;
	
	private TipoCliente(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum (Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (TipoCliente tC : TipoCliente.values()) {
			if (tC.getCodigo() == codigo) {
				return tC;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + codigo);
	}
	
}
