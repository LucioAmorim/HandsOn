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

public class HandsOnHelper {

	@Mock
	private LocacaoDAO dao;

	@Mock
	private SPCService spc;

	@Mock
	private EmailService email;

	@InjectMocks
	LocacaoService service;

	@Before
	public void setu() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void alugarUmFilme() throws Exception {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(4.0).agora());
		Locacao locacao = service.alugarFilme(usuario, filmes);
		assertTrue(locacao.getValor() == 4.0);
	}

	@Test
	public void alugarTresFilme() throws Exception {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(4.0).agora(),
				FilmeBuilder.umFilme().comValor(4.0).agora(), FilmeBuilder.umFilme().comValor(4.0).agora());
		Locacao locacao = service.alugarFilme(usuario, filmes);
		assertTrue(locacao.getValor() == 11.0);
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void AlugarFilmeSemEstoque() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().agora());
		Locacao locacao = service.alugarFilme(usuario, filmes);
	}

	@Test(expected = LocadoraException.class)
	public void AlugarFilmeSemUsuario() throws FilmeSemEstoqueException, LocadoraException {
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(4.0).agora());
		Locacao locacao = service.alugarFilme(null, filmes);
	}
	
	@Test(expected = LocadoraException.class)
	public void AlugarFilmeFilme() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		Locacao locacao = service.alugarFilme(usuario, null);
	}

	@Test
	public void AlugarFilmeUsuarioNegativado() throws Exception {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(4.0).agora());
		Mockito.when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);

		try {
			service.alugarFilme(usuario, filmes);
			Assert.fail();
		} catch (Exception e) {
			assertTrue(e.getMessage() == "Usuário Negativado");
		}

	}

	@Test
	public void EmailParaAtrasados() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Locacao> locacoes = Arrays.asList(LocacaoBuilder.umLocacao().atrasada().comUsuario(usuario).agora(),
				LocacaoBuilder.umLocacao().atrasada().comUsuario(usuario).agora());
		Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		service.notificarAtrasos();
		verify(email, times(2)).notificarAtraso(Mockito.any(Usuario.class));

	}
}
