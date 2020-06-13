package br.com.projects.modelo;

import java.util.ArrayList;
import java.util.List;
 
public class Campo {
     
	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();
 	
	
   Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
   
    public void registrarObservador(CampoObservador observador) {
    	 observadores.add(observador);
    }
    
    private void notificarObservadores(CampoEvento evento) {
    	  observadores.stream()
    	  .forEach(o -> o.aventoOcorreu(this, evento));
    }
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(linha - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
           
		if(deltaGeral == 1 && !diagonal) {
			 vizinhos.add(vizinho);
			 return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	   // alternando Marcação
		public void alternarMarcacao() {
			if(!aberto) {
				marcado = !marcado;
				
				if(marcado) {
					notificarObservadores(CampoEvento.MARCAR);
				}else {
					notificarObservadores(CampoEvento.DESMARCAR);
				}
			}
		}
		
		// verificando se o campo esta marcado
	 public boolean abrir() {
			if(!aberto && !marcado) {				
				if(minado) {
 					notificarObservadores(CampoEvento.EXPLODIR);
					return true;
				}
				setAberto(true);
 				
				if(vizihancaSegura()) {
					vizinhos.forEach(v -> v.abrir());
				}
				
				return true;
			}else {				
				return false;
			}
		}
		
		//detectando se os campos ao redor são seguros
		public boolean vizihancaSegura() {
			return vizinhos.stream().noneMatch(v -> v.minado);
		}
		
		public boolean isMinado() {
			return minado;
		}
		
		void minar() {
  			minado = true;
		}
		
		public boolean isMarcado() {
			return marcado;
		}
		
		void setAberto(boolean aberto) {
			this.aberto = aberto;
			 
			if(aberto) {
				notificarObservadores(CampoEvento.ABRIR);
			}
		}
		
		public boolean isAberto() {
			return aberto;
		}
		
		public boolean isFechado() {
			return !isAberto();
		}

		
		 // Tabuleiro
		public int getLinha() {
			return linha;
		}
		
		public int getColuna() {
			return coluna;
		}
		
		boolean objetivoAlcancado() {
		  boolean desvendado = !minado && aberto;
		  boolean protegido = minado && marcado;
		  return desvendado || protegido;
		}
		
		public int minasNaVizinhaca() {
			 return (int)vizinhos.stream().filter(v -> v.minado).count();
		}
		
		void reiniciar() {
			aberto = false;
			minado = false;
			marcado = false;
			notificarObservadores(CampoEvento.REINICIAR);
		}	
}























