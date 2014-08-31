package br.com.chat;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.rmi.RemoteException;

import org.junit.Test;

public class ChatTest {

	public Rede mockRede(boolean sucessoConexao) {
		// Criar mocks
		Rede rede = mock(Rede.class);

		// Predefinir retorno
		when(rede.abrirConexao(anyString())).thenReturn(sucessoConexao);

		return rede;

	}

	@Test
	public void abrirConexao() {
		Rede rede = mockRede(true);
		// Efetua o estímulo
		new Chat("1.1.1.1", rede);

		// Verificar se os mocks foram acionados
		verify(rede).abrirConexao("1.1.1.1");

	}

	@Test(expected = ExcecaoChat.class)
	public void erroAoAbrirConexao() {
		Rede rede = mockRede(false);
		// Efetua o estímulo
		new Chat("1.1.1.1", rede);

	}

	@Test
	public void enviarArquivo() throws RemoteException {
		Rede rede = mockRede(true);
		// Prepara o ambiente
		Chat chat = new Chat("8.8.8.8", rede);
		reset(rede);

		// Efetua o estímulo
		chat.enviarArquivo(new File("temp.txt"));

		// Verificar se os mocks foram acionados
		verify(rede).enviar(any(Pacote.class));

	}

	@Test(expected = ExcecaoChat.class)
	public void arquivoCorrompido() throws RemoteException {
		Rede rede = mockRede(true);
		// Prepara o ambiente
		Chat chat = new Chat("8.8.8.8", rede);
		reset(rede);

		// Predefinir lançamento de exceção
		doThrow(new RemoteException()).when(rede).enviar(any(Pacote.class));

		// Efetuar o estímulo
		chat.enviarArquivo(new File("temp.txt"));
	}

}
