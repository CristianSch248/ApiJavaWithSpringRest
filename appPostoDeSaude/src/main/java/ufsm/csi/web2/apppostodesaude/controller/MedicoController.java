package ufsm.csi.web2.apppostodesaude.controller;

import org.springframework.web.bind.annotation.*;
import ufsm.csi.web2.apppostodesaude.DAO.MedicoDAO;
import ufsm.csi.web2.apppostodesaude.model.Medico;

import java.util.ArrayList;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @GetMapping("/listar")
    public ArrayList<Medico> listarMedicos(){
        return new MedicoDAO().getMedicos();
    }

    @GetMapping("/{id}")
    public Medico getMedico(@PathVariable("id") int id){
        return new MedicoDAO().getMedico(id);
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@RequestBody Medico medico){
        return new MedicoDAO().cadastraMedico(medico);
    }

    @PutMapping("/update")
    public void updateMedico(@RequestBody Medico medico){
        new MedicoDAO().editaMedico(medico);
    }

    @PutMapping("/delete")
    public void deleteMedico(@RequestBody Medico medico){
        new MedicoDAO().deletaMedico(medico);
    }
}
