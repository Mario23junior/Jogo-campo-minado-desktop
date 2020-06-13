package br.com.projects.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.projects.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro  extends JPanel{
     
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(
				tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
		
		SwingUtilities.invokeLater(() -> {
			
			tabuleiro.registrarObservador(e -> {
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "PARABENS VOÇÉ GANHOU...");
				}else {
					JOptionPane.showMessageDialog(this, "QUE PENA VOCE PERDEU TENTE NOVAMENTE...");	
				}
				tabuleiro.reiniciar();
			});
			
		});
		
	}
}
