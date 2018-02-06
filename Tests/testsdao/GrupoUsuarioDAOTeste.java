package testsdao;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;
import model.dao.GrupoUsuarioDAO;
import model.vo.GrupoUsuarioVO;

public class GrupoUsuarioDAOTeste extends TestCase {

	@Test
	public GrupoUsuarioVO testedeveriaRetornarGrupoUsuarioVO() {
		GrupoUsuarioVO grupoVO = new GrupoUsuarioVO();
		grupoVO.setCodigoGrupo(3);
		grupoVO.setDescricaoGrupo("Administrador");
		grupoVO.setPermissaoAlterEmbarque("T");
		grupoVO.setPermissaoAlterMovimento("T");
		grupoVO.setPermissaoAlterNavio("T");
		grupoVO.setPermissaoAlterUser("T");
		grupoVO.setPermissaoAlterUser("T");
		grupoVO.setPermissaoConsulEmbarque("T");
		grupoVO.setPermissaoConsulMovimento("T");
		grupoVO.setPermissaoConsulNavio("T");
		grupoVO.setPermissaoConsulUser("T");
		grupoVO.setPermissaoDeletEmbarque("T");
		grupoVO.setPermissaoDeletMovimento("T");
		grupoVO.setPermissaoDeletNavio("T");
		grupoVO.setPermissaoDeletUser("T");
		grupoVO.setPermissaoInsertEmbarque("T");
		grupoVO.setPermissaoInsertMovimento("T");
		grupoVO.setPermissaoInsertNavio("T");
		grupoVO.setPermissaoInsertUser("T");
		
		return grupoVO;
	}
	
	@Test
	public void testDeveriaInserir() {
		GrupoUsuarioDAO grupoDAO = new GrupoUsuarioDAO();
		GrupoUsuarioVO grupoVO = testedeveriaRetornarGrupoUsuarioVO();
		
		//assertTrue(grupoDAO.Inserir(grupoVO));
	}
	
	@Test
	public void testDeveriaListarGrupoDeUsuarios() {
		GrupoUsuarioDAO grupoDAO = new GrupoUsuarioDAO();
		
		for(GrupoUsuarioVO i: grupoDAO.listar()) {
			System.out.println("C�digo Grupo: "+ i.getCodigoGrupo() +
							   "\nDescri��o Grupo: "+ i.getDescricaoGrupo() + 
							   "\nPermiss�o Alterar Embarque: " + i.getPermissaoAlterEmbarque());
		}
	}

}
