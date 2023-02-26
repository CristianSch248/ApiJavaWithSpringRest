package ufsm.csi.web2.apppostodesaude.controller;

import org.springframework.web.bind.annotation.*;
import ufsm.csi.web2.apppostodesaude.DAO.PacienteDAO;
import ufsm.csi.web2.apppostodesaude.model.Paciente;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @GetMapping("/listar")
    public ArrayList<Paciente> listarPacientes(){
        return new PacienteDAO().getPacientes();
    }

    @GetMapping("/{id}")
    public Paciente getPaciente(@PathVariable("id") int id) {
        return new PacienteDAO().getPacienteUnico(id);
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@RequestBody Paciente paciente){
        return new PacienteDAO().cadastrarPaciente(paciente);
    }

    @PutMapping("/update")
    public void update(@RequestBody Paciente paciente){
        new PacienteDAO().editarPaciente(paciente);
    }

    @PutMapping("/delete")
    public void deleteUsuario(@RequestBody Paciente paciente){
        new PacienteDAO().excluirPaciente(paciente);
    }
}
