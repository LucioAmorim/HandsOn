package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.LocacaoBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class HandsOnTest {

	// @Mock

	// @InjectMocks

	// @Before
	public void setup() {

	}

	@Test
	public void deveAlugarFilme() {

	}

	@Test
	public void alugarTresFilme() {

	}

	@Test // (expected = )
	public void AlugarFilmeSemUsuario() {

	}

	@Test // (expected = )
	public void AlugarFilmeSemFilme() {

	}

	@Test // (expected = )
	public void AlugarFilmeSemEstoque() {

	}

	@Test
	public void AlugarFilmeUsuarioNegativado() {
		// Mockito.when(???(Mockito.any(???))).thenReturn(???);
		// try
	}

	@Test
	public void EmailParaAtrasados() {
		// Mockito.when(???).thenReturn(???);
		// verify(???, times(???)).???(Mockito.any(???));

	}
}
