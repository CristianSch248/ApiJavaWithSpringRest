package ufsm.csi.web2.apppostodesaude.controller;

import org.springframework.web.bind.annotation.*;
import ufsm.csi.web2.apppostodesaude.DAO.ConsultaDAO;
import ufsm.csi.web2.apppostodesaude.model.Consulta;
import ufsm.csi.web2.apppostodesaude.model.Consult;
import ufsm.csi.web2.apppostodesaude.model.Medico;
import ufsm.csi.web2.apppostodesaude.model.Paciente;

import java.util.ArrayList;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    @GetMapping("/listar")
    public ArrayList<Consulta> listarConsultas(){
        return new ConsultaDAO().getConsultas();
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@RequestBody Consult consult) {
        System.out.println(consult.getId());
        System.out.println(consult.getPaciente());
        System.out.println(consult.getMedico());
        System.out.println(consult.getData());
        System.out.println(consult.getHora());
        System.out.println(consult.getCaso());
        return new ConsultaDAO().cadastrar(consult);

    }

    @PutMapping("/update")
    public void updateCadastro(@RequestBody Consulta consulta){
        new ConsultaDAO().editarConsulta(consulta);
    }

    @PutMapping("/delete")
    public void deleteConsulta(@RequestBody Consulta consulta){
        new ConsultaDAO().deletaConsulta(consulta);
    }

    @GetMapping("/{id}")
    public Consulta getConsulta(@PathVariable("id") int id){
        return new ConsultaDAO().getConsultaAtiva(id);
    }

    @PostMapping("/todo/historico/atendimento/Medico")
    public ArrayList<Consulta> listarTodasConsultasMedico(@RequestBody int id){
        return new ConsultaDAO().getTodoHistoricoAtendimentoMedico(id);
    }

    @PostMapping("/todos-historico/Atendimantos-Paciente")
    public ArrayList<Consulta> listarTodoHistoricoAtendimentoPaciente(@RequestBody int id){
        return new ConsultaDAO().getTodoHistoricoConsultasPaciente(id);
    }
    /////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/historico/Consultas-Medico")
    public ArrayList<Consulta> listarHistoricoConsultasMedico(@RequestBody Medico medico){
        return new ConsultaDAO().getHistoricoAtendimentoMedico(medico);
    }

    @GetMapping("/historico/Atendimantos-Paciente")
    public ArrayList<Consulta> listarHistoricoAtendimentoPaciente(@RequestBody Paciente paciente){
        return new ConsultaDAO().getHistoricoConsultasPaciente(paciente);
    }
}
