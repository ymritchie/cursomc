package com.ritchie.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN" ),
	CLIENTE(2, "ROLE_CLIENTE" );
	
	private Integer codigo;
	private String descricao;
	
	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum (Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (Perfil tC : Perfil.values()) {
			if (tC.getCodigo() == codigo) {
				return tC;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + codigo);
	}
	
	
}
