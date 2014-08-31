package br.com.chat;

import java.rmi.RemoteException;

public interface Rede {

	void enviar(Pacote paccote) throws RemoteException;

	boolean abrirConexao(String ip);

}
